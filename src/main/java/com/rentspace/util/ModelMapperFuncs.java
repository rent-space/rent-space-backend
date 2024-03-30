package com.rentspace.util;

import com.rentspace.DTO.listed.ListedPlaceDTO;
import com.rentspace.DTO.listed.ListedServiceDTO;
import com.rentspace.DTO.persist.product.PersistPlaceDTO;
import com.rentspace.DTO.persist.reservation.PersistPlaceReservationDTO;
import com.rentspace.DTO.persist.reservation.PersistReservationDTO;
import com.rentspace.DTO.persist.reservation.PersistServiceReservationDTO;
import com.rentspace.DTO.response.product.ResponsePlaceDTO;
import com.rentspace.DTO.response.product.ResponseProductDTO;
import com.rentspace.DTO.response.reservation.ResponsePlaceReservationDTO;
import com.rentspace.DTO.response.product.ResponseServiceDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.DTO.response.reservation.ResponseReservationDTO;
import com.rentspace.DTO.response.reservation.ResponseServiceReservationDTO;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Product;
import com.rentspace.model.products.Service;
import com.rentspace.model.reservation.PlaceReservation;
import com.rentspace.model.reservation.Reservation;
import com.rentspace.model.reservation.ServiceReservation;
import com.rentspace.model.reservation.Status;
import com.rentspace.model.user.AppUser;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.model.user.ServiceOwner;
import org.modelmapper.ModelMapper;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * ModelMapper is a library used to make objects translation easier. It can be really helpful in terms
 * of translating DTOs to Entity and vice-versa
 *
 * usage example: User entityUser = modelMapper.map(userDTO, User.class);
 *
 * anyway, it is interesting to create a class for this lib in order to code functions that automates
 * the process of transforming entityes into DTOs in a list element, or in a pagination context.
 *
 * @author Vinícius Azevedo
 */
public abstract class ModelMapperFuncs {

    /************************************* MODELMAPPER UTILITIES *************************************/

    @Autowired
    private ModelMapper modelMapper;

    public <E, T> E map(T source, Class<E> typeDestination) {
        E model = null;
        if (source != null && typeDestination != null) {
            model = modelMapper.map(source, typeDestination);
        }
        return model;
    }

    public <E, T> List<E> mapToList(T sourceList, Class<E> listedTypeDestination) {
        Type listType = TypeToken.getParameterized(List.class, listedTypeDestination).getType();
        return modelMapper.map(sourceList, listType);
    }

    /************************************* MODEL BUILDS *************************************/

    public PlaceReservation buildModel(PersistPlaceReservationDTO dto, Place place, BigDecimal placeFinalPrice, BigDecimal servicesFinalPrice) {
        PlaceReservation reservation = map(dto, PlaceReservation.class);
        reservation.setStatus(Status.PENDING);
        reservation.setProduct(place);
        reservation.setPlaceFinalPrice(placeFinalPrice);
        reservation.setServicesFinalPrice(servicesFinalPrice);
        return reservation;
    }

    public ServiceReservation buildModel(PersistServiceReservationDTO dto, Service service, BigDecimal finalPrice) {
        ServiceReservation reservation = map(dto, ServiceReservation.class);
        reservation.setStatus(Status.PENDING);
        reservation.setProduct(service);
        reservation.setAddress(dto.getAddress());
        reservation.setCity(dto.getCity());
        reservation.setFinalPrice(finalPrice);
        return reservation;
    }

    /************************************* RESPONSE BUILDS *************************************/

    public ResponseServiceReservationDTO buildResponse(ServiceReservation reservation, Service service, EventOwner owner, ServiceOwner serviceOwner, List<Place> places) {
        ResponseServiceReservationDTO dto = map(reservation, ResponseServiceReservationDTO.class);
        dto.setProduct(buildResponse(service, serviceOwner, places));
        dto.setEventOwner(map(owner, ResponseUserDTO.class));
        return dto;
    }

    public ResponsePlaceReservationDTO buildResponse(PlaceReservation reservation, Place place, PlaceOwner placeOwner, EventOwner eventOwner, List<Product> services) {
        ResponsePlaceReservationDTO dto = map(reservation, ResponsePlaceReservationDTO.class);
        dto.setProduct(buildResponse(place, placeOwner));
        dto.setHiredRelatedServices(mapToList(services, ListedServiceDTO.class));
        dto.setEventOwner(map(eventOwner, ResponseUserDTO.class));
        return dto;
    }

    public ResponseServiceDTO buildResponse(Service service, ServiceOwner owner, List<Place> places) {
        ResponseServiceDTO dto = map(service, ResponseServiceDTO.class);
        dto.setOwner(map(owner, ResponseUserDTO.class));
        dto.setPlacesRelated(mapToList(places, ListedPlaceDTO.class));
        return dto;
    }

    public ResponsePlaceDTO buildResponse(Place place, PlaceOwner owner) {
        ResponsePlaceDTO dto = map(place, ResponsePlaceDTO.class);
        dto.setOwner(map(owner, ResponseUserDTO.class));
        return dto;
    }

}
