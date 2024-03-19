package com.rentspace.service;

import com.rentspace.DTO.persist.PersistPlaceDTO;
import com.rentspace.DTO.response.ResponsePlaceDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.place.Place;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.repository.PlaceOwnerRepository;
import com.rentspace.repository.PlaceRepository;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.rentspace.exception.ExceptionMessages.INVALID_PLACE_ID;
import static com.rentspace.exception.ExceptionMessages.INVALID_PLACE_OWNER_ID;

@Service
@AllArgsConstructor
public class PlaceService extends ModelMapperFuncs {

    private PlaceRepository placeRepository;
    private PlaceOwnerService placeOwnerService;

    public void save(Place place) { placeRepository.save(place); }

    public ResponsePlaceDTO create(PersistPlaceDTO persistPlaceDTO) {
        PlaceOwner owner = placeOwnerService.get(persistPlaceDTO.getPlaceOwnerId());

        Place place = map(persistPlaceDTO, Place.class);
        owner.getPlaces().add(place);

        this.save(place);
        placeOwnerService.save(owner);
        return buildResponsePlaceDTO(place, owner);
    }

    public Place get(Long id) {
        return this.placeRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(INVALID_PLACE_ID + id));
    }
}
