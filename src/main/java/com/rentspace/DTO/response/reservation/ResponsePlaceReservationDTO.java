package com.rentspace.DTO.response.reservation;

import com.rentspace.DTO.listed.ListedServiceDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.DTO.response.product.ResponsePlaceDTO;
import com.rentspace.DTO.response.product.ResponseProductDTO;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class ResponsePlaceReservationDTO extends ResponseReservationDTO {

    private Integer numOfParticipants;

    private List<ListedServiceDTO> hiredRelatedServices;

    private BigDecimal placeFinalPrice;

    private BigDecimal servicesFinalPrice;

    public ResponsePlaceReservationDTO(
        Long id,
        LocalDateTime startsAt,
        LocalDateTime endsAt,
        PaymentMethod paymentMethod,
        Integer numOfInstallments,
        ResponsePlaceDTO place,
        Integer numOfParticipants,
        Status status,
        List<ListedServiceDTO> hiredRelatedServices,
        ResponseUserDTO eventOwner,
        BigDecimal placeFinalPrice,
        BigDecimal servicesFinalPrice
    ) {
        super(id, startsAt, endsAt, paymentMethod, numOfInstallments, place, eventOwner, status);
       this.numOfParticipants = numOfParticipants;
       this.hiredRelatedServices = hiredRelatedServices;
       this.placeFinalPrice = placeFinalPrice;
       this.servicesFinalPrice = servicesFinalPrice;
    }

}
