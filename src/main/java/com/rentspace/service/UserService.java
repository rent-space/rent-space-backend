package com.rentspace.service;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.model.place.Place;
import com.rentspace.model.service.Services;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.model.user.User;
import com.rentspace.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private void saveNewUser(User user){
        userRepository.save(user);
    }

    public ResponseUserDTO registerNewServiceOwner(PersistUserDTO persistUserDTO, List<Services> services) {

        ServiceOwner serviceOwner = modelMapper.map(persistUserDTO, ServiceOwner.class);
        serviceOwner.setServices(services);
        saveNewUser(serviceOwner);
        return modelMapper.map(serviceOwner, ResponseUserDTO.class);
    }

    public ResponseUserDTO registerNewEventOwner(PersistUserDTO persistUserDTO) {

        EventOwner eventOwner = modelMapper.map(persistUserDTO, EventOwner.class);
        saveNewUser(eventOwner);
        return modelMapper.map(eventOwner, ResponseUserDTO.class);
    }

    public ResponseUserDTO registerNewPlaceOwner(PersistUserDTO persistUserDTO, List<Place> places){

        PlaceOwner placeOwner = modelMapper.map(persistUserDTO, PlaceOwner.class);
        placeOwner.setPlaces(places);
        saveNewUser(placeOwner);
        return modelMapper.map(placeOwner, ResponseUserDTO.class);

    }
}
