package com.rentspace.controller;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/usuario")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<ResponseUserDTO> create(@RequestBody PersistUserDTO persistDTO) {
        return new ResponseEntity<>(userService.create(persistDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<ResponseUserDTO> get(@PathVariable String userEmail){
        return new ResponseEntity<>(userService.get(userEmail), HttpStatus.OK);
    }
}
