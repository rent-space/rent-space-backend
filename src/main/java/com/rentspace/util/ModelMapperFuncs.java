package com.rentspace.util;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

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
@AllArgsConstructor
public abstract class ModelMapperFuncs {

    private ModelMapper modelMapper;

    /*
        TODO: Object translation funcions
            1- itself (made automatically with modelMapper)
            2- in pagination context
            3- object to list
            4- list to object
     */

}
