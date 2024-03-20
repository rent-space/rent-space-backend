package com.rentspace.controller;

import com.rentspace.DTO.persist.PersistPlaceDTO;
import com.rentspace.DTO.persist.PersistServiceDTO;
import com.rentspace.DTO.response.ResponsePlaceDTO;
import com.rentspace.DTO.response.ResponseServiceDTO;
import com.rentspace.service.PlaceService;
import com.rentspace.service.ServiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/servico")
@AllArgsConstructor
public class ServiceController {

    private ServiceService serviceService;

    @PostMapping
    public ResponseEntity<ResponseServiceDTO> create(@RequestBody PersistServiceDTO persistPlaceDTO) {
        return new ResponseEntity<>(serviceService.create(persistPlaceDTO), HttpStatus.CREATED);
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<String>> getServiceNatures() {
        return new ResponseEntity<>(serviceService.getServiceNatures(), HttpStatus.OK);
    }

}
