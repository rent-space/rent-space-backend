package com.rentspace.DTO.response.reservation;

import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.DTO.response.product.ResponseProductDTO;
import com.rentspace.DTO.response.product.ResponseServiceDTO;
import com.rentspace.model.products.ServiceNature;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class ResponseServiceReservationDTO extends ResponseReservationDTO {

    private BigDecimal finalPrice;

    public ResponseServiceReservationDTO(
        Long id,
        LocalDateTime startsAt,
        LocalDateTime endsAt,
        PaymentMethod paymentMethod,
        Integer numOfInstallments,
        ResponseServiceDTO productDTO,
        ResponseUserDTO eventOwner,
        Status status,
        BigDecimal finalPrice
    ) {
        super(id, startsAt, endsAt, paymentMethod, numOfInstallments, productDTO, eventOwner, status);
        this.finalPrice = finalPrice;
    }

}
