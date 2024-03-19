package com.rentspace.DTO.persist;

import com.rentspace.model.products.Service;
import com.rentspace.model.reservation.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersistPlaceReservationDTO {

    @NotNull
    private Date startTime;

    @NotNull
    private Date endTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Integer numOfInstallments;

    @NotNull
    private Long placeId;

    @NotNull
    private Integer numOfParticipants;

    @NotNull
    private List<Long> hiredRelatedServicesIds;

}
