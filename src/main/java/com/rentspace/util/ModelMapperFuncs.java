package com.rentspace.util;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ModelMapper is a library used to make objects translation easier. It can be really helpful in terms
 * of translating DTOs to Entity and vice-versa
 *
 * usage example: User entityUser = modelMapper.map(userDTO, User.class);
 *
 * anyway, it is interesting to create a class for this lib in order to code functions that automates
 * the process of transforming entityes into DTOs in a list element, or in a pagination context.
 *
 * @author Vinícius Azevedo
 */
public class ModelMapperFuncs {

    @Autowired
    private ModelMapper modelMapper;

    public <E, T> E map(T source, Class<E> typeDestination) {
        E model = null;
        if (source != null && typeDestination != null) {
            model = modelMapper.map(source, typeDestination);
        }
        return model;
    }

}
