package com.rentspace.service;

import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.EventOwner;
import com.rentspace.repository.EventOwnerRepository;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.rentspace.exception.ExceptionMessages.EVENT_OWNER_SEARCH_ERROR;
import static com.rentspace.exception.ExceptionMessages.RESERVATION_USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class EventOwnerService extends ModelMapperFuncs {

    private EventOwnerRepository eventOwnerRepository;

    public void save(EventOwner model) { this.eventOwnerRepository.save(model); }
    public EventOwner get(Long id) {
        return eventOwnerRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(EVENT_OWNER_SEARCH_ERROR + id));
    }

    public EventOwner getByPlaceReservation(Long reservationId) {
        if(eventOwnerRepository.findByServiceReservation(reservationId).isPresent()){
            return eventOwnerRepository.findByServiceReservation(reservationId).get();
        }else{
            return new EventOwner();
        }
    }

    public EventOwner getByServiceReservation(Long reservationId) {
        if(eventOwnerRepository.findByServiceReservation(reservationId).isPresent()){
            return eventOwnerRepository.findByServiceReservation(reservationId).get();
        }else{
            return new EventOwner();
        }

    }
}
