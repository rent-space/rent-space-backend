package com.rentspace.DTO.response;

import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponsePlaceReservationDTO {

    private Long id;

    private Date startTime;

    private Date endTime;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Integer numOfInstallments;

    private ResponsePlaceDTO place;

    private Integer numOfParticipants;

    private Status status;

    private List<ResponseServiceDTO> hiredRelatedServicesIds;

}
