package com.rentspace.DTO.response.reservation;

import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.DTO.response.product.ResponseProductDTO;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseReservationDTO {

    private Long id;

    private LocalDateTime startsAt;

    private LocalDateTime endsAt;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Integer numOfInstallments;

    private ResponseProductDTO product;

    private ResponseUserDTO eventOwner;

    private Status status;
}
