package com.rentspace.controller;

import com.rentspace.DTO.persist.PersistPlaceReservationDTO;
import com.rentspace.DTO.persist.PersistServiceReservationDTO;
import com.rentspace.DTO.response.ResponsePlaceReservationDTO;
import com.rentspace.DTO.response.ResponseServiceReservationDTO;
import com.rentspace.service.PlaceReservationService;
import com.rentspace.service.ServiceReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/solicitacao/servico")
@AllArgsConstructor
public class ServiceReservationController {

    private ServiceReservationService serviceReservationService;

    @PostMapping
    public ResponseEntity<ResponseServiceReservationDTO> create(@RequestBody PersistServiceReservationDTO persistDTO) {
        return new ResponseEntity<>(serviceReservationService.create(persistDTO), HttpStatus.CREATED);
    }

}
