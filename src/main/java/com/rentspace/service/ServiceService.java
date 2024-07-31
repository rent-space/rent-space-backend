package com.rentspace.service;


import com.rentspace.DTO.listed.ListedServiceDTO;
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
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.rentspace.exception.ExceptionMessages.INVALID_SERVICE_ID;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceService extends ModelMapperFuncs {

    private ServiceRepository serviceRepository;
    private ServiceOwnerService serviceOwnerService;
    private PlaceService placeService;
    private ImageService imageService;

    public void save(Service model) { this.serviceRepository.save(model); }

    public Service get(Long id) {
        return this.serviceRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(INVALID_SERVICE_ID  + id));
    }

    public ResponseServiceDTO create(PersistServiceDTO persistDTO, List<MultipartFile> file) throws IOException {
        validatePersistDTO(persistDTO);

        ServiceOwner owner = serviceOwnerService.get(persistDTO.getOwnerId());
        List<Place> places = getPlaces(persistDTO.getPlacesIdsRelated());

        if (persistDTO.getMedia() == null) {
            persistDTO.setMedia(new ArrayList<>());
        }

        if (file != null && !file.isEmpty()) {
            List<String> fileNames = imageService.uploadMultipleFiles(file);
            System.out.println(fileNames);
            persistDTO.setMedia(fileNames);
        }

        Service service = map(persistDTO, Service.class);
        saveService(service, owner, places);

        return buildResponse(service, owner, places);
    }

    private void validatePersistDTO(PersistServiceDTO persistDTO) {
        Objects.requireNonNull(persistDTO, "PersistServiceDTO must not be null");
        Objects.requireNonNull(persistDTO.getOwnerId(), "OwnerId must not be null");
        Objects.requireNonNull(persistDTO.getPlacesIdsRelated(), "PlacesIdsRelated must not be null");
    }

    private List<Place> getPlaces(List<Long> placesIdsRelated) {
        List<Place> places = new ArrayList<>();
        placesIdsRelated.forEach(id -> places.add(placeService.get(id)));
        return places;
    }

//    private Service map(PersistServiceDTO persistDTO, Class<Service> serviceClass) {
//        // Implement mapping logic here
//        // This is a stub for demonstration purposes
//        return new Service();
//    }

    private void saveService(Service service, ServiceOwner owner, List<Place> places) {
        save(service);
        owner.getServices().add(service);
        serviceOwnerService.save(owner);

        for (Place place : places) {
            place.getServices().add(service);
            placeService.save(place);
        }
    }

    public List<String> getServiceNatures() {
        return List.of(Arrays.toString(ServiceNature.values()));
    }

    public List<Place> getRelatedPlaces(Long serviceId) {
        return this.placeService.getAllByExclusiveService(serviceId);
    }

    public ResponseServiceDTO view(Long id) {
        Service service = get(id);
        return buildResponse(
                service,
                serviceOwnerService.getByServiceId(id),
                placeService.getAllByExclusiveService(id)
        );
    }

    public ResponseServiceDTO delete(Long id) {
        Service service = get(id);
        serviceRepository.delete(service);
        return buildResponse(
                service,
                serviceOwnerService.getByServiceId(id),
                placeService.getAllByExclusiveService(id)
        );
    }

    public List<ListedServiceDTO> viewAll() {
        return this.buildResponse(this.serviceRepository.findAll());
    }

    public List<ListedServiceDTO> viewByOwner(Long ownerId) {
        return this.buildResponse(serviceOwnerService.get(ownerId).getServices());
    }

    public ResponseServiceDTO update(Long id, PersistServiceDTO persistDTO) {
        get(id);
        Service service = map(persistDTO, Service.class);
        service.setId(id);
        save(service);
        return buildResponse(
                service,
                serviceOwnerService.getByServiceId(id),
                placeService.getAllByExclusiveService(id)
        );
    }
}
