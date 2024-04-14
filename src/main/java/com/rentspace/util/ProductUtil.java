package com.rentspace.util;

import com.rentspace.DTO.persist.reservation.PersistReservationDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Product;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.Reservation;
import com.rentspace.model.reservation.Status;
import com.rentspace.repository.ReservationRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.rentspace.exception.ExceptionMessages.*;

public abstract class ProductUtil {

    public static BigDecimal getFinalPrice(LocalDateTime startsAt, LocalDateTime endsAt, List<Product> products) {
        long duration = Duration.between(startsAt, endsAt).toMinutes();
        BigDecimal finalPrice = new BigDecimal(0);
        for (Product product : products) {
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

    public static void updateReservationStatus(Reservation reservation, Status status) {
        if (status == Status.PENDING || reservation.getStatus() != Status.PENDING){
            throw new ApiRequestException(INVALID_STATUS_UPDATE + reservation.getId());
        }

        reservation.setStatus(status);
    }

}
