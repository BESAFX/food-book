package com.besafx.app.config;

import com.besafx.app.auditing.PersonAwareUserDetails;
import com.besafx.app.dao.PersonDao;
import com.besafx.app.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Component
public class UserAuthenticationProvider implements UserDetailsService {

    private final Logger LOG = LoggerFactory.getLogger(UserAuthenticationProvider.class);

    @Autowired
    private PersonDao personDao;

    public UserAuthenticationProvider() {
        super();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personDao
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with -> email : " + email));

        List<GrantedAuthority> authorities = new ArrayList<>();

        LOG.info("BUILD USER AUTHORITIES.");
        Optional.ofNullable(person.getTeam().getAuthorities())
                .ifPresent(value -> Arrays.asList(value.split(",")).stream()
                        .forEach(s -> authorities.add(new SimpleGrantedAuthority(s))));

        LOG.info("SAVE LOGIN INFO.");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        person.setLastLoginDate(new Date());
        person.setActive(true);
        person.setIpAddress(request.getRemoteAddr());
        personDao.save(person);

        return new PersonAwareUserDetails(person, authorities);
    }
}
