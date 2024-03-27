package com.rentspace.DTO.persist;

import com.rentspace.model.products.ServiceNature;
import com.rentspace.model.reservation.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersistServiceReservationDTO {

    @NotNull
    private LocalDateTime startsAt;

    @NotNull
    private LocalDateTime endsAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @NotNull
    private Integer numOfInstallments;

    @NotNull
    private Long serviceId;

    @NotNull
    private Long eventOwnerId;

    @NotNull
    private ServiceNature serviceNature;

}
