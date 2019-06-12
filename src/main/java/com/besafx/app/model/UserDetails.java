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
@Table(name = "\"user_details\"")
public class UserDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "userDetailsSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "USER_DETAILS_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "userDetailsSequenceGenerator")
    private Long id;

    private String firstName;

    private String lastName;

    private String mobile;

    private String email;

    private String hourlyRate;

    @JsonCreator
    public static UserDetails Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserDetails userDetails = mapper.readValue(jsonString, UserDetails.class);
        return userDetails;
    }
}
