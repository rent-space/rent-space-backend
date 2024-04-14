package com.rentspace.controller;

import com.rentspace.DTO.listed.ListedServiceDTO;
import com.rentspace.DTO.persist.product.PersistServiceDTO;
import com.rentspace.DTO.response.product.ResponseServiceDTO;
import com.rentspace.DTO.response.reservation.ResponseServiceReservationDTO;
import com.rentspace.model.products.Service;
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
    public ResponseEntity<ResponseServiceDTO> create(@RequestBody PersistServiceDTO persistDTO) {
        return new ResponseEntity<>(serviceService.create(persistDTO), HttpStatus.CREATED);
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<String>> getServiceNatures() {
        return new ResponseEntity<>(serviceService.getServiceNatures(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseServiceDTO> view(@PathVariable Long id) {
        return new ResponseEntity<>(serviceService.view(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ListedServiceDTO>> viewAll() {
        return new ResponseEntity<>(serviceService.viewAll(), HttpStatus.OK);
    }

}
