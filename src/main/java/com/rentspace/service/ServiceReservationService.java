package com.rentspace.service;

import com.rentspace.DTO.persist.PersistServiceReservationDTO;
import com.rentspace.DTO.response.ResponseServiceReservationDTO;
import com.rentspace.repository.ServiceReservationRepository;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceReservationService extends ModelMapperFuncs {

    private ServiceReservationRepository serviceReservationRepository;

    public ResponseServiceReservationDTO create(PersistServiceReservationDTO persistDTO) {
        return null;
    }
}
