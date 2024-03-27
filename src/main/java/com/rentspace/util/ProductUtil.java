package com.rentspace.util;

import com.rentspace.DTO.persist.PersistPlaceReservationDTO;
import com.rentspace.model.products.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;

public class ProductUtil {

    public static BigDecimal getFinalPrice(PersistPlaceReservationDTO persistDTO, List<Product> services) {
        long duration = Duration.between(persistDTO.getStartsAt(), persistDTO.getEndsAt()).toMinutes();
        BigDecimal finalPrice = new BigDecimal(0);
        for (Product product : services) {
            BigDecimal pricePerMinute = product.getPricePerHour().divide(new BigDecimal(60), RoundingMode.HALF_UP);
            finalPrice = finalPrice.add(pricePerMinute.multiply(new BigDecimal(duration)));
        }
        return finalPrice;
    }
}
