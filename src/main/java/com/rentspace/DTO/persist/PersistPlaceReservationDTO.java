package com.rentspace.DTO.persist;

import com.rentspace.model.reservation.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersistPlaceReservationDTO {

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
    private Long placeId;

    @NotNull
    private Integer numOfParticipants;

    @NotNull
    private List<Long> hiredRelatedServicesIds;

    @NotNull
    private Long eventOwnerId;

}
