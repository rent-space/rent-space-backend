package com.rentspace.service;

import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.repository.PlaceOwnerRepository;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import static com.rentspace.exception.ExceptionMessages.INVALID_PLACE_OWNER_ID;
import static com.rentspace.exception.ExceptionMessages.PLACE_OWNER_SEARCH_ERROR;

@Service
@AllArgsConstructor
public class PlaceOwnerService extends ModelMapperFuncs {

    private PlaceOwnerRepository placeOwnerRepository;

    public void save(PlaceOwner model) { placeOwnerRepository.save(model); }

    public PlaceOwner get(Long id) {
        return placeOwnerRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(INVALID_PLACE_OWNER_ID + id));
    }

    public PlaceOwner getByPlaceId(Long id) {
        return placeOwnerRepository.findByPlaceId(id)
                .orElseThrow(() -> new ApiRequestException(PLACE_OWNER_SEARCH_ERROR + id));
    }

}
