package com.besafx.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;

@Data
@Entity
@Table(name = "\"role_privilege\"")
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePrivilege implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "rolePrivilegeRoleSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "ROLE_PRIVILEGE_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "rolePrivilegeRoleSequenceGenerator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "\"role\"")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "\"privilege\"")
    private Privilege privilege;


    @JsonCreator
    public static RolePrivilege Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RolePrivilege rolePrivilege = mapper.readValue(jsonString, RolePrivilege.class);
        return rolePrivilege;
    }
}
