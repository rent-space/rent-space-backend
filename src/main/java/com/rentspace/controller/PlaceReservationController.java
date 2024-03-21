package com.rentspace.controller;

import com.rentspace.DTO.persist.PersistPlaceReservationDTO;
import com.rentspace.DTO.response.ResponsePlaceReservationDTO;
import com.rentspace.repository.PlaceReservationRepository;
import com.rentspace.service.PlaceReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/solicitacao/espaco")
@AllArgsConstructor
public class PlaceReservationController {

    private PlaceReservationService placeReservationService;

    @PostMapping
    public ResponseEntity<ResponsePlaceReservationDTO> create(@RequestBody PersistPlaceReservationDTO persistDTO) {
        return new ResponseEntity<>(placeReservationService.create(persistDTO), HttpStatus.CREATED);
    }

}
