package com.rentspace.DTO.listed;

import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.DTO.response.product.ResponseServiceDTO;
import com.rentspace.model.products.Product;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.Status;
import com.rentspace.model.user.AppUser;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ListedServiceReservationDTO {

    private Long id;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private PaymentMethod paymentMethod;
    private Integer numOfInstallments;
    private ResponseServiceDTO productDTO;
    private ResponseUserDTO serviceOwner;
    private Product product;
    private Status status;
    private BigDecimal finalPrice;
    private String address;
    private String city;
    private AppUser user;
}
