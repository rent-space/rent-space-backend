package com.rentspace.service;


import com.rentspace.DTO.persist.PersistPlaceDTO;
import com.rentspace.DTO.response.ResponsePlaceDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Place;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.repository.PlaceRepository;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import static com.rentspace.exception.ExceptionMessages.INVALID_PLACE_ID;

@Service
@AllArgsConstructor
public class PlaceService extends ModelMapperFuncs {

    private PlaceRepository placeRepository;
    private PlaceOwnerService placeOwnerService;

    public void save(Place model) { placeRepository.save(model); }

    public ResponsePlaceDTO create(PersistPlaceDTO persistDTO) {
        PlaceOwner owner = placeOwnerService.get(persistDTO.getPlaceOwnerId());

        Place place = map(persistDTO, Place.class);
        owner.getPlaces().add(place);

        this.save(place);
        placeOwnerService.save(owner);
        return buildResponse(place, owner);
    }

    public Place get(Long id) {
        return this.placeRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(INVALID_PLACE_ID + id));
    }

    public ResponsePlaceDTO view(Long id) {

        Place place = this.placeRepository.findById(id).orElseThrow(() -> new ApiRequestException(INVALID_PLACE_ID + id));

        PlaceOwner owner = this.placeOwnerService.getByPlaceId(id);

        return buildResponse(place, owner);
    }
}
