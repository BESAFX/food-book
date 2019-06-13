package com.besafx.app.init;

import com.besafx.app.dao.CompanyDao;
import com.besafx.app.dao.ContactDao;
import com.besafx.app.dao.PersonDao;
import com.besafx.app.dao.TeamDao;
import com.besafx.app.dto.CompanyOptions;
import com.besafx.app.dto.PersonOptions;
import com.besafx.app.model.Company;
import com.besafx.app.model.Contact;
import com.besafx.app.model.Person;
import com.besafx.app.model.Team;
import com.besafx.app.utils.JSONConverter;
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
    private CompanyDao companyDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {

        startup();

    }

    private void startup() {

        if (Lists.newArrayList(personDao.findAll()).isEmpty()) {
            Person person = new Person();
            Contact contact = new Contact();
            contact.setNickname("Eng. ");
            contact.setName("Bassam Almahdy");
            contact.setPhoto("");
            contact.setQualification("Web Developer");
            person.setContact(contactDao.save(contact));
            person.setEmail("admin@gmail.com");
            person.setPassword(passwordEncoder.encode("admin"));
            person.setHiddenPassword("admin");
            person.setEnabled(true);
            person.setTokenExpired(false);
            person.setActive(false);
            person.setTechnicalSupport(true);
            Team team = new Team();
            team.setCode(1);
            team.setName("Technical Support");
            team.setAuthorities(String.join(",", "ROLE_COMPANY_UPDATE",
                    "ROLE_CUSTOMER_CREATE",
                    "ROLE_CUSTOMER_UPDATE",
                    "ROLE_CUSTOMER_DELETE",

                    "ROLE_SUPPLIER_CREATE",
                    "ROLE_SUPPLIER_UPDATE",
                    "ROLE_SUPPLIER_DELETE",

                    "ROLE_PRODUCT_CREATE",
                    "ROLE_PRODUCT_UPDATE",
                    "ROLE_PRODUCT_DELETE",

                    "ROLE_PERSON_CREATE",
                    "ROLE_PERSON_UPDATE",
                    "ROLE_PERSON_DELETE",

                    "ROLE_TEAM_CREATE",
                    "ROLE_TEAM_UPDATE",
                    "ROLE_TEAM_DELETE"
            ));
            person.setTeam(teamDao.save(team));
            person.setOptions(JSONConverter
                    .toString(PersonOptions.builder()
                            .lang("AR")
                            .style("mdl-style")
                            .dateType("G")
                            .iconSet("icon-set-2")
                            .iconSetType("png")
                            .build())
            );
            person.setCompany(companyDao.findFirstBy().orElseGet(() -> {
                Company company = new Company();
                company.setOptions(
                        JSONConverter.toString(
                                CompanyOptions.builder()
                                        .vatFactor(0.0)
                                        .logo("")
                                        .background("")
                                        .reportTitle("")
                                        .reportSubTitle("")
                                        .reportFooter("")));
                return companyDao.save(company);
            }));
            personDao.save(person);
        }

    }
}
