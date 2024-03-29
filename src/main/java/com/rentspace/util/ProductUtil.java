package com.rentspace.util;

import com.rentspace.DTO.persist.reservation.PersistReservationDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Product;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.Reservation;
import com.rentspace.repository.ReservationRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;

import static com.rentspace.exception.ExceptionMessages.INVALID_PAYMENT_FORMAT;
import static com.rentspace.exception.ExceptionMessages.INVALID_RESERVATION_PERIOD_OF_TIME;

public abstract class ProductUtil {

    public static BigDecimal getFinalPrice(PersistReservationDTO persistDTO, List<Product> services) {
        long duration = Duration.between(persistDTO.getStartsAt(), persistDTO.getEndsAt()).toMinutes();
        BigDecimal finalPrice = new BigDecimal(0);
        for (Product product : services) {
            BigDecimal pricePerMinute = product.getPricePerHour().divide(new BigDecimal(60), RoundingMode.HALF_UP);
            finalPrice = finalPrice.add(pricePerMinute.multiply(new BigDecimal(duration)));
        }
        return finalPrice;
    }

    public static void validatesFields(PersistReservationDTO persistDTO) {
        if (persistDTO.getPaymentMethod() == PaymentMethod.PIX && persistDTO.getNumOfInstallments() != 0) {
            throw new ApiRequestException(INVALID_PAYMENT_FORMAT);
        }
        if (persistDTO.getStartsAt().isAfter(persistDTO.getEndsAt())) {
            throw new ApiRequestException(INVALID_RESERVATION_PERIOD_OF_TIME);
        }
    }

    public static void checkProductAvailability(PersistReservationDTO persistDTO, Product product, ReservationRepository<? extends Reservation> repository) {
        if (repository
                .getReservationInProgress(
                        persistDTO.getStartsAt(),
                        persistDTO.getEndsAt(),
                        product
                ).isPresent()
        ) {
            throw new ApiRequestException(INVALID_RESERVATION_PERIOD_OF_TIME);
        }
    }

}
