package com.rentspace.service;


import com.rentspace.DTO.persist.PersistServiceDTO;
import com.rentspace.DTO.response.ResponseServiceDTO;
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

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceService extends ModelMapperFuncs {

    private ServiceRepository serviceRepository;
    private ServiceOwnerService serviceOwnerService;
    private PlaceService placeService;

    public void save(Service model) { this.serviceRepository.save(model); }

    public ResponseServiceDTO create(PersistServiceDTO persistDTO) {
        ServiceOwner owner = serviceOwnerService.get(persistDTO.getServiceOwnerId());

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

        return buildResponseServiceDTO(service, owner, places);
    }

    public List<String> getServiceNatures() {
        return List.of(Arrays.toString(ServiceNature.values()));
    }
}
