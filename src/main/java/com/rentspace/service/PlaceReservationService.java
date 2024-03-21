package com.rentspace.service;

import com.rentspace.DTO.persist.PersistPlaceReservationDTO;
import com.rentspace.DTO.response.ResponsePlaceReservationDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.PlaceReservation;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.repository.PlaceReservationRepository;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import static com.rentspace.exception.ExceptionMessages.*;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class PlaceReservationService extends ModelMapperFuncs {

    private PlaceReservationRepository placeReservationRepository;
    private PlaceService placeService;
    private ServiceService serviceService;
    private PlaceOwnerService placeOwnerService;
    private EventOwnerService eventOwnerService;

    public void save(PlaceReservation model) { this.placeReservationRepository.save(model); }

    public ResponsePlaceReservationDTO create(PersistPlaceReservationDTO persistDTO) {
        if (persistDTO.getPaymentMethod() == PaymentMethod.PIX && persistDTO.getNumOfInstallments() != 0) {
            throw new ApiRequestException(INVALID_PAYMENT_FORMAT);
        }
        if (persistDTO.getStartsAt().isAfter(persistDTO.getEndsAt())) {
            throw new ApiRequestException(INVALID_RESERVATION_PERIOD_OF_TIME);
        }

        EventOwner eventOwner = eventOwnerService.get(persistDTO.getEventOwnerId());
        Place place = placeService.get(persistDTO.getPlaceId());

        if (placeReservationRepository
                .getReservationInProgress(
                        persistDTO.getStartsAt(),
                        persistDTO.getEndsAt(),
                        place
                ).isPresent()
        ) { throw new ApiRequestException(INVALID_RESERVATION_PERIOD_OF_TIME); }

        List<Service> servicesRelated = new ArrayList<>();
        int servicesPeopleInvolved = 0;
        for (Long id : persistDTO.getHiredRelatedServicesIds()) {
            Service service = serviceService.get(id);
            if (!place.getServices().contains(service)) {
                throw new ApiRequestException(SERVICE_NOT_RELATED_TO_SPACE + id);
            }
            servicesRelated.add(service);
            servicesPeopleInvolved += service.getPeopleInvolved();
        }

        if (persistDTO.getNumOfParticipants() + servicesPeopleInvolved > place.getMaximumCapacity()) {
            throw new ApiRequestException(PEOPLE_INVOLVED_EXCEED_MAXIMUM_CAPACITY);
        }

        PlaceReservation reservation = buildModel(
                persistDTO,
                place,
                getPlaceFinalPrice(persistDTO, place),
                getServicesFinalPrice(persistDTO, servicesRelated));

        PlaceOwner placeOwner = placeOwnerService.getByPlaceId(place.getId());
        placeOwner.getReservations().add(reservation);
        eventOwner.getPlaces().add(reservation);
        save(reservation);
        placeOwnerService.save(placeOwner);
        eventOwnerService.save(eventOwner);

        return buildResponse(reservation, place, placeOwner, eventOwner, servicesRelated);
    }

    private BigDecimal getPlaceFinalPrice(PersistPlaceReservationDTO persistDTO, Place place) {
        long duration = Duration.between(persistDTO.getStartsAt(), persistDTO.getEndsAt()).toMinutes();
        BigDecimal pricePerMinute = place.getPricePerHour().divide(new BigDecimal(60), RoundingMode.HALF_UP);
        return pricePerMinute.multiply(new BigDecimal(duration));
    }

    private BigDecimal getServicesFinalPrice(PersistPlaceReservationDTO persistDTO, List<Service> services) {
        long duration = Duration.between(persistDTO.getStartsAt(), persistDTO.getEndsAt()).toMinutes();
        BigDecimal finalPrice = new BigDecimal(0);
        for (Service service : services) {
            BigDecimal pricePerMinute = service.getPricePerHour().divide(new BigDecimal(60), RoundingMode.HALF_UP);
            finalPrice = finalPrice.add(pricePerMinute.multiply(new BigDecimal(duration)));
        }
        return finalPrice;
    }
}
