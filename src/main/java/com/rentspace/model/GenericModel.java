package com.rentspace.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;

/**
 * This class will be inherited by all other entities in order to create a pattern to the Id implementation
 *
 * @author Vinícius Azevedo
 */
@MappedSuperclass
@Data
public abstract class GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

}
