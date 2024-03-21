package com.rentspace.controller;

import com.rentspace.DTO.persist.PersistPlaceDTO;
import com.rentspace.DTO.response.ResponsePlaceDTO;
import com.rentspace.service.PlaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/espaco")
@AllArgsConstructor
public class PlaceController {

    private PlaceService placeService;

    @PostMapping
    public ResponseEntity<ResponsePlaceDTO> create(@RequestBody PersistPlaceDTO persistPlaceDTO) {
        return new ResponseEntity<>(placeService.create(persistPlaceDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePlaceDTO> view(@PathVariable Long id){
        return new ResponseEntity<>(placeService.view(id), HttpStatus.OK);
    }


}
