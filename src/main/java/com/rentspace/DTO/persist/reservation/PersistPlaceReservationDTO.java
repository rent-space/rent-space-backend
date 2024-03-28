package com.rentspace.DTO.persist.reservation;

import com.rentspace.model.reservation.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class PersistPlaceReservationDTO extends PersistReservationDTO {

    @NotNull
    private Integer numOfParticipants;

    @NotNull
    private List<Long> hiredRelatedServicesIds;

    public PersistPlaceReservationDTO(
            LocalDateTime startsAt,
            LocalDateTime endsAt,
            PaymentMethod method,
            Integer numOfInstallments,
            Long placeId,
            Integer numOfParticipants,
            List<Long> hiredRelatedServicesIds,
            Long eventOwnerId
    ) {
        super(startsAt, endsAt, method, numOfInstallments, eventOwnerId, placeId);
        this.numOfParticipants = numOfParticipants;
        this.hiredRelatedServicesIds = hiredRelatedServicesIds;
    }

}
