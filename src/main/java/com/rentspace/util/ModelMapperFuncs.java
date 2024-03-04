package com.rentspace.util;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

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
