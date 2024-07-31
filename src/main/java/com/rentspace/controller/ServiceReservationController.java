package com.rentspace.controller;

import com.rentspace.DTO.listed.ListedServiceReservationDTO;
import com.rentspace.DTO.persist.reservation.PersistServiceReservationDTO;
import com.rentspace.DTO.response.reservation.ResponseServiceReservationDTO;
import com.rentspace.model.reservation.Status;
import com.rentspace.service.ServiceReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public ResponseEntity<List<ResponseServiceReservationDTO>> viewAll() {
        return new ResponseEntity<>(serviceReservationService.viewAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseServiceReservationDTO> updateStatus(@PathVariable Long id, @RequestBody Status status) {
        return new ResponseEntity<>(serviceReservationService.updateStatus(id, status), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseServiceReservationDTO> delete(@PathVariable Long id) {
        return new ResponseEntity<>(serviceReservationService.delete(id), HttpStatus.OK);
    }

}
