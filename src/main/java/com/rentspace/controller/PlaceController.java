package com.rentspace.controller;

import com.rentspace.DTO.listed.ListedPlaceDTO;
import com.rentspace.DTO.persist.product.PersistPlaceDTO;
import com.rentspace.DTO.response.product.ResponsePlaceDTO;
import com.rentspace.service.PlaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/espaco")
@AllArgsConstructor
public class PlaceController {

    private PlaceService placeService;

    @PostMapping
    public ResponseEntity<ResponsePlaceDTO> create(@RequestBody PersistPlaceDTO persistDTO) {
        return new ResponseEntity<>(placeService.create(persistDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePlaceDTO> view(@PathVariable Long id){
        return new ResponseEntity<>(placeService.view(id), HttpStatus.OK);
    }

    @GetMapping("/allPlaces")
    public ResponseEntity<List<ListedPlaceDTO>> viewAll(){
        return new ResponseEntity<>(placeService.viewAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePlaceDTO> update(@PathVariable Long id, @RequestBody PersistPlaceDTO persistDTO) {
        return new ResponseEntity<>(placeService.update(id, persistDTO), HttpStatus.OK);
    }


}
