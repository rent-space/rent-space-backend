package com.rentspace.controller;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/usuario")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<ResponseUserDTO> registerUser(@RequestBody PersistUserDTO persistUserDTO) {
        return new ResponseEntity<>(userService.registerUser(persistUserDTO), HttpStatus.CREATED);
    }
}
