package com.rentspace.service;


import com.rentspace.DTO.persist.product.PersistServiceDTO;
import com.rentspace.DTO.response.product.ResponseServiceDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.products.ServiceNature;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.repository.ServiceRepository;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.rentspace.exception.ExceptionMessages.INVALID_SERVICE_ID;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceService extends ModelMapperFuncs {

    private ServiceRepository serviceRepository;
    private ServiceOwnerService serviceOwnerService;
    private PlaceService placeService;

    public void save(Service model) { this.serviceRepository.save(model); }

    public Service get(Long id) {
        return this.serviceRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(INVALID_SERVICE_ID + id));
    }

    public ResponseServiceDTO create(PersistServiceDTO persistDTO) {
        ServiceOwner owner = serviceOwnerService.get(persistDTO.getOwnerId());

        List<Place> places = new ArrayList<>();
        persistDTO.getPlacesIdsRelated().forEach(
                id -> places.add(this.placeService.get(id))
        );

        Service service = map(persistDTO, Service.class);

        this.save(service);
        owner.getServices().add(service);
        for (Place place : places) {
            place.getServices().add(service);
            this.placeService.save(place);
        }
        serviceOwnerService.save(owner);

        return buildResponse(service, owner, places);
    }

    public List<String> getServiceNatures() {
        return List.of(Arrays.toString(ServiceNature.values()));
    }

    public List<Place> getRelatedPlaces(Long serviceId) {
        return this.serviceRepository.findRelatedPlaces(serviceId);
    }
}
