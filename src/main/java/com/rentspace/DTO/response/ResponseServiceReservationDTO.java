package com.rentspace.DTO.response;

import com.rentspace.model.products.ServiceNature;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseServiceReservationDTO {

    private Long id;

    private LocalDateTime startsAt;

    private LocalDateTime endsAt;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Integer numOfInstallments;

    private ResponseServiceDTO service;

    private ResponseUserDTO eventOwner;

    private ServiceNature serviceNature;

    private BigDecimal finalPrice;

    private Status status;

}
