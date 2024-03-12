package com.rentspace.controller;

import com.rentspace.DTO.UserDTO;
import com.rentspace.model.place.Place;
import com.rentspace.model.service.Services;
import com.rentspace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register-service-owner")
    public void registerNewServiceOwner(@RequestBody UserDTO userDTO, @RequestBody List<Services> services) {
        userService.registerNewServiceOwner(userDTO, services);
    }

    @PostMapping("/register-event-owner")
    public void registerNewEventOwner(@RequestBody UserDTO userDTO) {
        userService.registerNewEventOwner(userDTO);
    }

    @PostMapping("/register-place-owner")
    public void registerNewPlaceOwner(@RequestBody UserDTO userDTO, @RequestBody List<Place> places) {
        userService.registerNewPlaceOwner(userDTO, places);
    }
}
