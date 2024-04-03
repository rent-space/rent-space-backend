package com.rentspace.controller;

import com.rentspace.DTO.persist.reservation.PersistPlaceReservationDTO;
import com.rentspace.DTO.response.reservation.ResponsePlaceReservationDTO;
import com.rentspace.service.PlaceReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/solicitacao/espaco")
@AllArgsConstructor
public class PlaceReservationController {

    private PlaceReservationService placeReservationService;

    @PostMapping
    public ResponseEntity<ResponsePlaceReservationDTO> create(@RequestBody PersistPlaceReservationDTO persistDTO) {
        return new ResponseEntity<>(placeReservationService.create(persistDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePlaceReservationDTO> view(@PathVariable Long id) {
        return new ResponseEntity<>(placeReservationService.view(id), HttpStatus.OK);
    }

}
