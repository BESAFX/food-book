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
@Table(name = "\"project\"")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "projectSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "PROJECT_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "projectSequenceGenerator")
    private Long id;

    private String name;

    private String projKey;

    private String path;

    private String username;

    private String password;

    @JsonCreator
    public static Project Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Project project = mapper.readValue(jsonString, Project.class);
        return project;
    }
}
