package com.rentspace.service;

import com.rentspace.DTO.persist.reservation.PersistServiceReservationDTO;
import com.rentspace.DTO.response.reservation.ResponseServiceReservationDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.reservation.ServiceReservation;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.repository.ServiceReservationRepository;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.rentspace.exception.ExceptionMessages.SERVICE_IS_EXCLUSIVE;
import static com.rentspace.util.ProductUtil.*;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceReservationService extends ModelMapperFuncs {

    private ServiceReservationRepository serviceReservationRepository;
    private EventOwnerService eventOwnerService;
    private ServiceOwnerService serviceOwnerService;
    private ServiceService serviceService;

    public void save(ServiceReservation model) { serviceReservationRepository.save(model);}

    public ResponseServiceReservationDTO create(PersistServiceReservationDTO persistDTO) {
        // TODO observar se o serviço não é vinculado a um espaço
        validatesFields(persistDTO);

        EventOwner eventOwner = eventOwnerService.get(persistDTO.getEventOwnerId());
        Service service = serviceService.get(persistDTO.getProductId());
        checkProductAvailability(persistDTO, service, serviceReservationRepository);
        checkAvailableService(persistDTO, service);
        ServiceReservation reservation = buildModel(
                persistDTO,
                service,
                getFinalPrice(persistDTO, new ArrayList<>(Collections.singletonList(service)))
        );

        ServiceOwner serviceOwner = serviceOwnerService.getByServiceId(service.getId());
        serviceOwner.getReservations().add(reservation);
        eventOwner.getServices().add(reservation);
        save(reservation);
        serviceOwnerService.save(serviceOwner);
        eventOwnerService.save(eventOwner);
        List<Place> relatedPlaces = serviceService.getRelatedPlaces(service.getId());
        return buildResponse(reservation, service, eventOwner, serviceOwner, relatedPlaces);
    }

    public void checkAvailableService(PersistServiceReservationDTO persistDTO, Service service) {
        boolean placeFound = false;
        for (Place place : serviceService.getRelatedPlaces(service.getId())) {
            if (place.getAddress().equals(persistDTO.getAddress()) &&
                    place.getCity().equals(persistDTO.getCity())) {
                placeFound = true;
                break;
            }
        }
        if (!placeFound) {
            throw new ApiRequestException(SERVICE_IS_EXCLUSIVE);
        }
    }
}
