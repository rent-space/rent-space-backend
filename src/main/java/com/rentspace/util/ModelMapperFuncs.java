package com.rentspace.util;

import com.rentspace.DTO.listed.ListedPlaceDTO;
import com.rentspace.DTO.listed.ListedServiceDTO;
import com.rentspace.DTO.persist.PersistPlaceReservationDTO;
import com.rentspace.DTO.response.ResponsePlaceDTO;
import com.rentspace.DTO.response.ResponsePlaceReservationDTO;
import com.rentspace.DTO.response.ResponseServiceDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.reservation.PlaceReservation;
import com.rentspace.model.reservation.Status;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.model.user.ServiceOwner;
import org.modelmapper.ModelMapper;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

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
public class ModelMapperFuncs {

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

    public ResponseServiceDTO buildResponse(Service service, ServiceOwner owner, List<Place> places) {
        ResponseServiceDTO dto = map(service, ResponseServiceDTO.class);
        dto.setServiceOwner(map(owner, ResponseUserDTO.class));
        dto.setPlacesRelated(mapToList(places, ListedPlaceDTO.class));
        return dto;
    }

    public ResponsePlaceDTO buildResponse(Place place, PlaceOwner placeOwner) {
        ResponsePlaceDTO dto = map(place, ResponsePlaceDTO.class);
        dto.setPlaceOwner(map(placeOwner, ResponseUserDTO.class));
        return dto;
    }

    public ResponsePlaceReservationDTO buildResponse(PlaceReservation reservation, Place place, PlaceOwner placeOwner, EventOwner eventOwner, List<Service> services) {
        ResponsePlaceReservationDTO dto = map(reservation, ResponsePlaceReservationDTO.class);
        dto.setPlace(buildResponse(place, placeOwner));
        dto.setHiredRelatedServices(mapToList(services, ListedServiceDTO.class));
        dto.setEventOwner(map(eventOwner, ResponseUserDTO.class));
        return dto;
    }

    public PlaceReservation buildModel(PersistPlaceReservationDTO dto, Place place, BigDecimal placeFinalPrice, BigDecimal servicesFinalPrice) {
        PlaceReservation reservation = map(dto, PlaceReservation.class);
        reservation.setStatus(Status.PENDING);
        reservation.setPlace(place);
        reservation.setPlaceFinalPrice(placeFinalPrice);
        reservation.setServicesFinalPrice(servicesFinalPrice);
        return reservation;
    }

}
