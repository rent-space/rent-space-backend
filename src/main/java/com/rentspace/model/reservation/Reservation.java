package com.rentspace.model.reservation;

import com.rentspace.model.GenericModel;
import com.rentspace.model.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Reservation extends GenericModel {

    private LocalDateTime startsAt;

    private LocalDateTime endsAt;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Integer numOfInstallments;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Product product;

}
