package com.rentspace.service;

import com.rentspace.DTO.UserDTO;
import com.rentspace.model.place.Place;
import com.rentspace.model.place.PlaceReservation;
import com.rentspace.model.service.ServiceReservation;
import com.rentspace.model.service.Services;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.model.user.User;
import com.rentspace.repository.UserRepository;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User saveNewUser(User user){
        return userRepository.save(user);
    }

    public void registerNewServiceOwner(UserDTO userDTO, List<Services> services) {
        ServiceOwner serviceOwner =
                new ServiceOwner
                        (userDTO.getName(), userDTO.getProfilePhoto(), userDTO.getEmail(), userDTO.getTelephone(), userDTO.getWebSite() );
        serviceOwner.setServices(services);
        saveNewUser(serviceOwner);
    }

    public void registerNewEventceOwner(UserDTO userDTO) {
        EventOwner eventOwner =
                new EventOwner
                        (userDTO.getName(), userDTO.getProfilePhoto(), userDTO.getEmail(), userDTO.getTelephone(), userDTO.getWebSite());
        saveNewUser(eventOwner);
    }

    public void registernewPlaceowner(UserDTO userDTO, List<Place> places){
        PlaceOwner placeOwner = new PlaceOwner
                (userDTO.getName(), userDTO.getProfilePhoto(), userDTO.getEmail(), userDTO.getTelephone(), userDTO.getWebSite());
        placeOwner.setPlaces(places);
        saveNewUser(placeOwner);

    }
}
