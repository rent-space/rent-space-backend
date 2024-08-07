package com.rentspace.controller;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<ResponseUserDTO> getByEmail(@PathVariable String userEmail){
        return new ResponseEntity<>(userService.getByEmail(userEmail), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> update(@PathVariable Long id, @RequestBody PersistUserDTO persistUserDTO ) {
        return new ResponseEntity<>(userService.update(id, persistUserDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> delete(@PathVariable Long id) {
        return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> setUserAccountId(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String accountId = (String) request.get("accountId");
        return new ResponseEntity<>(userService.setUserAccountId(id, accountId), HttpStatus.OK);
    }
}
