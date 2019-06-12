package com.besafx.app.init;

import com.besafx.app.dao.*;
import com.besafx.app.model.*;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {

    private final static Logger LOG = LoggerFactory.getLogger(Initializer.class);

    @Autowired
    private PrivilegeDao privilegeDao;

    @Autowired
    private UserDetailsDao userDetailsDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RolePrivilegeDao rolePrivilegeDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {

        startup();

    }

    private void startup() {
        if (userDao.count() > 0) {
            return;
        }
        LOG.info("INITIALIZING...");
        Privilege privilege1 = new Privilege();
        privilege1.setName("ROLE_VIEW_PROJECTS_PRIVILEGE");
        privilege1.setDescription("View Projects");
        privilegeDao.save(privilege1);

        Privilege privilege2 = new Privilege();
        privilege2.setName("ROLE_VIEW_ALL_ISSUES_PRIVILEGE");
        privilege2.setDescription("View All Issues");
        privilegeDao.save(privilege2);

        Privilege privilege3 = new Privilege();
        privilege3.setName("ROLE_VIEW_RULES_PRIVILEGE");
        privilege3.setDescription("View Rules");
        privilegeDao.save(privilege3);

        Privilege privilege4 = new Privilege();
        privilege4.setName("ROLE_VIEW_ADMIN_PRIVILEGE");
        privilege4.setDescription("View Administration");
        privilegeDao.save(privilege4);

        Privilege privilege5 = new Privilege();
        privilege5.setName("ROLE_VIEW_QUALITY_PRIVILEGE");
        privilege5.setDescription("View Quality Profiles");
        privilegeDao.save(privilege5);

        Privilege privilege6 = new Privilege();
        privilege6.setName("ROLE_EDIT_ROLE");
        privilege6.setDescription("Edit Roles");
        privilegeDao.save(privilege6);

        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName("Bassam");
        userDetails.setLastName("Alamhdy");
        userDetails.setEmail("islamhaker@gmail.com");
        userDetails.setMobile("+20123456789");
        userDetails.setHourlyRate("20");
        userDetailsDao.save(userDetails);

        User user = new User();
        user.setUserDatils(userDetails);
        user.setActive(true);
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        userDao.save(user);

        Role role = new Role();
        role.setName("ROLE_SYSTEM_ADMIN");
        role.setDescription("System Admin");
        roleDao.save(role);

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoleDao.save(userRole);


        rolePrivilegeDao.saveAll(Lists.newArrayList(
                RolePrivilege.builder().role(role).privilege(privilege1).build(),
                RolePrivilege.builder().role(role).privilege(privilege2).build(),
                RolePrivilege.builder().role(role).privilege(privilege3).build(),
                RolePrivilege.builder().role(role).privilege(privilege4).build(),
                RolePrivilege.builder().role(role).privilege(privilege5).build(),
                RolePrivilege.builder().role(role).privilege(privilege6).build()
        ));
    }
}
