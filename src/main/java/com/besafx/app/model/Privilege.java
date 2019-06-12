package com.besafx.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.IOException;
import java.io.Serializable;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "\"privilege\"")
public class Privilege implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "privilegeSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "PRIVILEGE_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "privilegeSequenceGenerator")
    private Long id;

    private String name;

    private String description;

    @JsonCreator
    public static Privilege Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Privilege privilege = mapper.readValue(jsonString, Privilege.class);
        return privilege;
    }
}
