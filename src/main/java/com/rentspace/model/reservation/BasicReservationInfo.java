package com.rentspace.model.reservation;

import com.rentspace.model.GenericModel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.Date;

@MappedSuperclass
@Data
public abstract class BasicReservationInfo extends GenericModel {

    private Date startTime;

    private Date endTime;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Integer numOfInstallments;

    @Enumerated(EnumType.STRING)
    private Status status;

}
