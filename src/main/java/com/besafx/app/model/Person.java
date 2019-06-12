package com.besafx.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "personSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "PERSON_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "personSequenceGenerator")
    private Long id;

    private String email;

    private String password;

    @Column(columnDefinition = "boolean default false")
    private Boolean technicalSupport;

    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;

    @Column(columnDefinition = "boolean default false")
    private Boolean tokenExpired;

    @Column(columnDefinition = "boolean default false")
    private Boolean active;

    @JsonIgnore
    private String hiddenPassword;

    private Date lastLoginDate;

    private String ipAddress;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String options;

    @ManyToOne
    @JoinColumn(name = "team")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "contact")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;

    @JsonCreator
    public static Person Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Person person = mapper.readValue(jsonString, Person.class);
        return person;
    }
}
