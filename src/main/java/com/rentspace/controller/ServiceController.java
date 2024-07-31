package com.rentspace.controller;

import com.rentspace.DTO.listed.ListedServiceDTO;
import com.rentspace.DTO.persist.product.PersistPlaceDTO;
import com.rentspace.DTO.persist.product.PersistServiceDTO;
import com.rentspace.DTO.response.product.ResponsePlaceDTO;
import com.rentspace.DTO.response.product.ResponseServiceDTO;
import com.rentspace.DTO.response.reservation.ResponseServiceReservationDTO;
import com.rentspace.model.products.Service;
import com.rentspace.service.ServiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/servico")
@AllArgsConstructor
public class ServiceController {

    private ServiceService serviceService;

    @PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ResponseServiceDTO> create(@RequestPart PersistServiceDTO persistDTO,
      @RequestPart("file") List<MultipartFile> file) throws IOException {
        return new ResponseEntity<>(serviceService.create(persistDTO, file), HttpStatus.CREATED);
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<String>> getServiceNatures() {
        return new ResponseEntity<>(serviceService.getServiceNatures(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseServiceDTO> view(@PathVariable Long id) {
        return new ResponseEntity<>(serviceService.view(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseServiceDTO> delete(@PathVariable Long id) {
        return new ResponseEntity<>(serviceService.delete(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ListedServiceDTO>> viewAll() {
        return new ResponseEntity<>(serviceService.viewAll(), HttpStatus.OK);
    }

    @GetMapping("/prestador/{id}")
    public ResponseEntity<List<ListedServiceDTO>> viewByOwner(@PathVariable Long id) {
        return new ResponseEntity<>(serviceService.viewByOwner(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseServiceDTO> update(@PathVariable Long id, @RequestBody PersistServiceDTO persistDTO) {
        return new ResponseEntity<>(serviceService.update(id, persistDTO), HttpStatus.OK);
    }

}
