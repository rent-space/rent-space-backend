package com.rentspace.service;

import com.rentspace.DTO.persist.PersistPlaceReservationDTO;
import com.rentspace.DTO.response.ResponsePlaceReservationDTO;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaceReservationService extends ModelMapperFuncs {
    public ResponsePlaceReservationDTO create(PersistPlaceReservationDTO persistDTO) {
        return null;
    }
}
