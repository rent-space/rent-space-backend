package com.rentspace.service;

import com.rentspace.DTO.persist.reservation.PersistPlaceReservationDTO;
import com.rentspace.DTO.response.reservation.ResponsePlaceReservationDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Product;
import com.rentspace.model.products.Service;
import com.rentspace.model.reservation.PlaceReservation;
import com.rentspace.model.reservation.Status;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.repository.PlaceReservationRepository;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.rentspace.exception.ExceptionMessages.*;
import static com.rentspace.util.ProductUtil.*;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class PlaceReservationService extends ModelMapperFuncs {

    private PlaceReservationRepository placeReservationRepository;
    private PlaceService placeService;
    private ServiceService serviceService;
    private PlaceOwnerService placeOwnerService;
    private EventOwnerService eventOwnerService;
    private ServiceReservationService serviceReservationService;

    public void save(PlaceReservation model) { this.placeReservationRepository.save(model); }

    public PlaceReservation get(Long id) {
        return this.placeReservationRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(RESERVATION_NOT_FOUND + id));
    }

    public ResponsePlaceReservationDTO create(PersistPlaceReservationDTO persistDTO) {
        validatesFields(persistDTO);

        EventOwner eventOwner = eventOwnerService.get(persistDTO.getEventOwnerId());
        Place place = placeService.get(persistDTO.getProductId());
        checkProductAvailability(persistDTO, place, placeReservationRepository);

        List<Service> servicesRelated = getRelatedServices(persistDTO, place);

        PlaceReservation reservation = buildModel(
                persistDTO,
                place,
                getFinalPrice(persistDTO.getStartsAt(), persistDTO.getEndsAt(), new ArrayList<>(Collections.singletonList(place))),
                getFinalPrice(persistDTO.getStartsAt(), persistDTO.getEndsAt(), mapToList(servicesRelated, Product.class)),
                servicesRelated
        );

        PlaceOwner placeOwner = placeOwnerService.getByPlaceId(place.getId());
        placeOwner.getReservations().add(reservation);
        eventOwner.getPlaces().add(reservation);
        save(reservation);
        placeOwnerService.save(placeOwner);
        eventOwnerService.save(eventOwner);
        servicesRelated.forEach(
                service -> serviceReservationService.save(buildModel(reservation, service))
        );

        return buildResponse(reservation, placeOwner, eventOwner);
    }

    private List<Service> getRelatedServices(PersistPlaceReservationDTO persistDTO, Place place) {
        List<Service> services = new ArrayList<>();
        int servicesPeopleInvolved = 0;
        for (Long id : persistDTO.getHiredRelatedServicesIds()) {
            Service service = serviceService.get(id);
            if (!place.getServices().contains(service)) {
                throw new ApiRequestException(SERVICE_NOT_RELATED_TO_SPACE + id);
            }
            services.add(service);
            servicesPeopleInvolved += service.getPeopleInvolved();
        }

        if (persistDTO.getNumOfParticipants() + servicesPeopleInvolved > place.getMaximumCapacity()) {
            throw new ApiRequestException(PEOPLE_INVOLVED_EXCEED_MAXIMUM_CAPACITY);
        }
        return services;
    }

    public ResponsePlaceReservationDTO view(Long id) {
        PlaceReservation reservation = get(id);
        return buildResponse(
                reservation,
                placeOwnerService.getByPlaceId(reservation.getProduct().getId()),
                eventOwnerService.getByPlaceReservation(id)
        );
    }

    public ResponsePlaceReservationDTO updateStatus(Long id, Status status) {
        PlaceReservation reservation = get(id);
        updateReservationStatus(reservation, status);

        save(reservation);
        return buildResponse(
                reservation,
                placeOwnerService.getByPlaceId(reservation.getProduct().getId()),
                eventOwnerService.getByPlaceReservation(id)
        );
    }

    public ResponsePlaceReservationDTO delete(Long id) {
        PlaceReservation placeReservation = get(id);
        placeReservationRepository.delete(placeReservation);
        return map(placeReservation, ResponsePlaceReservationDTO.class);
    }

    public List<ResponsePlaceReservationDTO> viewAll() {
        List<ResponsePlaceReservationDTO> dtos = new ArrayList<>();
        for (PlaceReservation reservation : placeReservationRepository.findAll()) {
            dtos.add(
                    buildResponse(
                            reservation,
                            placeOwnerService.getByPlaceId(reservation.getProduct().getId()),
                            eventOwnerService.getByPlaceReservation(reservation.getId())
                    )
            );
        }
        return dtos;
    }
}
