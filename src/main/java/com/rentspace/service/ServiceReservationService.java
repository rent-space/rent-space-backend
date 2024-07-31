package com.rentspace.service;

import com.rentspace.DTO.persist.reservation.PersistServiceReservationDTO;
import com.rentspace.DTO.response.product.ResponseProductDTO;
import com.rentspace.DTO.response.reservation.ResponseServiceReservationDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.reservation.ServiceReservation;
import com.rentspace.model.reservation.Status;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.repository.ServiceReservationRepository;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.rentspace.exception.ExceptionMessages.*;
import static com.rentspace.util.ProductUtil.*;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceReservationService extends ModelMapperFuncs {

    private ServiceReservationRepository serviceReservationRepository;
    private EventOwnerService eventOwnerService;
    private ServiceOwnerService serviceOwnerService;
    private ServiceService serviceService;
    private PlaceService placeService;

    public void save(ServiceReservation model) { serviceReservationRepository.save(model);}

    public ServiceReservation get(Long id) {
        return serviceReservationRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(RESERVATION_NOT_FOUND + id));
    }

    public ResponseServiceReservationDTO create(PersistServiceReservationDTO persistDTO) {
        validatesFields(persistDTO);

        EventOwner eventOwner = eventOwnerService.get(persistDTO.getEventOwnerId());
        Service service = serviceService.get(persistDTO.getProductId());
        checkProductAvailability(persistDTO, service, serviceReservationRepository);
        checkAvailableService(persistDTO, service);
        ServiceReservation reservation = buildModel(
                persistDTO,
                service,
                getFinalPrice(persistDTO.getStartsAt(), persistDTO.getEndsAt(), new ArrayList<>(Collections.singletonList(service)))
        );

        ServiceOwner serviceOwner = serviceOwnerService.getByServiceId(service.getId());
        serviceOwner.getReservations().add(reservation);
        eventOwner.getServices().add(reservation);
        save(reservation);
        serviceOwnerService.save(serviceOwner);
        eventOwnerService.save(eventOwner);
        List<Place> relatedPlaces = serviceService.getRelatedPlaces(service.getId());
        return buildResponse(reservation, eventOwner, serviceOwner, relatedPlaces);
    }

    public void checkAvailableService(PersistServiceReservationDTO persistDTO, Service service) {
        List<Place> places = serviceService.getRelatedPlaces(service.getId());
        boolean placeFound = false;
        for (Place place : places) {
            if (place.getAddress().equals(persistDTO.getAddress()) &&
                    place.getCity().equals(persistDTO.getCity())) {
                placeFound = true;
                break;
            }
        }
        if (!placeFound && !places.isEmpty()) {
            throw new ApiRequestException(SERVICE_IS_EXCLUSIVE);
        }
    }

    public ResponseServiceReservationDTO view(Long id) {
        ServiceReservation reservation = get(id);
        return buildResponse(
                reservation,
                eventOwnerService.getByServiceReservation(id),
                serviceOwnerService.getByServiceId(reservation.getProduct().getId()),
                placeService.getAllByExclusiveService(id)
        );
    }

    public ResponseServiceReservationDTO updateStatus(Long id, Status status) {
        ServiceReservation reservation = get(id);
        updateReservationStatus(reservation, status);

        save(reservation);
        return buildResponse(
                reservation,
                eventOwnerService.getByServiceReservation(id),
                serviceOwnerService.getByServiceId(reservation.getProduct().getId()),
                placeService.getAllByExclusiveService(id)
        );
    }

    public ResponseServiceReservationDTO delete(Long id) {
        ServiceReservation serviceReservation = get(id);

        serviceReservationRepository.delete(serviceReservation);
        return map(serviceReservation, ResponseServiceReservationDTO.class);
    }

    public List<ResponseServiceReservationDTO> viewAll() {
        List<ServiceReservation>  serviceReservations = serviceReservationRepository.findAll();
        return buildServiceReservationList(serviceReservations);
    }


}
