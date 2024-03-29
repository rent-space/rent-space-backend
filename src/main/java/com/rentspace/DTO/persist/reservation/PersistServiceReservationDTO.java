package com.rentspace.DTO.persist.reservation;

import com.rentspace.DTO.persist.reservation.PersistReservationDTO;
import com.rentspace.model.products.ServiceNature;
import com.rentspace.model.reservation.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class PersistServiceReservationDTO extends PersistReservationDTO {

    @NotNull
    private String address;

    @NotNull
    private String city;

    public PersistServiceReservationDTO(
            LocalDateTime startsAt,
            LocalDateTime endsAt,
            PaymentMethod method,
            Integer numOfInstallments,
            Long serviceId,
            Long eventOwnerId,
            String address,
            String city
    ) {
        super(startsAt, endsAt, method, numOfInstallments, eventOwnerId, serviceId);
        this.address = address;
        this.city = city;
    }

}
