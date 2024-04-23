package com.rentspace.model.products;

import com.rentspace.model.GenericModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Product extends GenericModel {

    private String title;
    private String description;


    @ElementCollection
    @Column(columnDefinition = "text")
    private List<String> media;
    private String address;
    private String city;
    private BigDecimal pricePerHour;

}
