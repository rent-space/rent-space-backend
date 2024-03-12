package com.rentspace.controller;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.model.place.Place;
import com.rentspace.model.service.Services;
import com.rentspace.model.user.User;
import com.rentspace.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/usuario")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register-service-owner")
    public ResponseEntity<ResponseUserDTO> registerNewServiceOwner(@RequestBody PersistUserDTO persistUserDTO, @RequestBody List<Services> services) {
        return new ResponseEntity<>(userService.registerNewServiceOwner(persistUserDTO, services), HttpStatus.CREATED);
    }

    @PostMapping("/register-event-owner")
    public ResponseEntity<ResponseUserDTO> registerNewEventOwner(@RequestBody PersistUserDTO persistUserDTO) {
        return new ResponseEntity<>(userService.registerNewEventOwner(persistUserDTO), HttpStatus.CREATED);
    }

    @PostMapping("/register-place-owner")
    public ResponseEntity<ResponseUserDTO> registerNewPlaceOwner(@RequestBody PersistUserDTO persistUserDTO, @RequestBody List<Place> places) {
        return new ResponseEntity<>(userService.registerNewPlaceOwner(persistUserDTO, places), HttpStatus.CREATED);
    }
}
