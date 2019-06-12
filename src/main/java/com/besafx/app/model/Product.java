package com.besafx.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "productSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "PRODUCT_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "productSequenceGenerator")
    private Long id;

    private Integer code;

    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registerDate;

    @ManyToOne
    @JoinColumn(name = "parent")
    private Product parent;

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    @OneToMany(mappedBy = "parent")
    private List<Product> childes = new ArrayList<>();

    @JsonCreator
    public static Product Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(jsonString, Product.class);
        return product;
    }

}
