package com.besafx.app.config;

import com.besafx.app.model.User;
import com.besafx.app.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserAuthenticationProvider implements UserDetailsService {

    private final Logger LOG = LoggerFactory.getLogger(UserAuthenticationProvider.class);

    @Autowired
    private UserDao userDao;

    public UserAuthenticationProvider() {
        super();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles()
                .stream()
                .flatMap(userRole -> userRole.getRole().getPrivileges().stream())
                .map(rolePrivilege -> rolePrivilege.getPrivilege().getName())
                .collect(Collectors.toList())
                .forEach(s -> authorities.add(new SimpleGrantedAuthority(s)));

        return new UserAwareUserDetails(user, authorities);
    }
}
