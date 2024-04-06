package com.rentspace.controller;

import com.rentspace.DTO.persist.reservation.PersistServiceReservationDTO;
import com.rentspace.DTO.response.reservation.ResponseServiceReservationDTO;
import com.rentspace.service.ServiceReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/solicitacao/servico")
@AllArgsConstructor
public class ServiceReservationController {

    private ServiceReservationService serviceReservationService;

    @PostMapping
    public ResponseEntity<ResponseServiceReservationDTO> create(@RequestBody PersistServiceReservationDTO persistDTO) {
        return new ResponseEntity<>(serviceReservationService.create(persistDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseServiceReservationDTO> view(@PathVariable Long id) {
        return new ResponseEntity<>(serviceReservationService.view(id), HttpStatus.OK);
    }

}
