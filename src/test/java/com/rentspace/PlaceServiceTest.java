package com.rentspace;

import com.rentspace.model.products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.rentspace.model.products.Place; 
import com.rentspace.model.products.Service; 
import com.rentspace.model.reservation.PlaceReservation;
import com.rentspace.model.user.PlaceOwner; 
import com.rentspace.repository.PlaceOwnerRepository;
import com.rentspace.repository.PlaceRepository;
import com.rentspace.repository.PlaceReservationRepository;
import com.rentspace.service.PlaceService;
import com.rentspace.service.PlaceOwnerService;
import com.rentspace.service.PlaceReservationService;
import com.rentspace.DTO.persist.PersistPlaceReservationDTO;

import static com.rentspace.util.ProductUtil.getFinalPrice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.time.Duration;

public class PlaceServiceTest {

	@Mock
    private PlaceRepository placeRepository;
	
	@Mock
	private PlaceOwnerRepository placeOwnerRepository;
	
	@Mock
	private PlaceReservationRepository placeReservationRepository;
	
	@Mock
    private PlaceService placeService;
	
	@Mock
	private PlaceOwnerService placeOwnerService; 
	
	@Mock
	private PlaceReservationService placeReservationService;

	private Place place;
	
	private PlaceOwner placeOwner;
	
	private PlaceReservation placeReservation; 
	
    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    	place = new Place();
    	placeOwner = new PlaceOwner();
    	placeService = new PlaceService(placeRepository, null);
    	placeOwnerService = new PlaceOwnerService(placeOwnerRepository);
    	placeReservation = new PlaceReservation();
    	placeReservationService = new PlaceReservationService(placeReservationRepository, 
    			placeService, null, placeOwnerService, null);  
    }
    
    @Test
    public void savePlace() {
        when(placeRepository.save(any(Place.class))).thenReturn(place);

        placeService.save(place);

        verify(placeRepository, times(1)).save(place);
    }
     
    @Test 
    public void getPlaceById() {
        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(place));

        Place retrievedPlace = placeService.get(1L);

        assertEquals(place, retrievedPlace); 
    }
    
    @Test
    public void savePlaceOwner() {
        when(placeOwnerRepository.save(any(PlaceOwner.class))).thenReturn(placeOwner);

        placeOwnerService.save(placeOwner);

        verify(placeOwnerRepository, times(1)).save(placeOwner);
    }
    
    @Test 
    public void getPlaceOwnerById() {
        when(placeOwnerRepository.findById(anyLong())).thenReturn(Optional.of(placeOwner));

        PlaceOwner retrievedService = placeOwnerService.get(1L);

        assertEquals(placeOwner, retrievedService);
    }
    
    @Test 
    public void getPlaceOwnerByPlaceId() { 
        when(placeOwnerRepository.findByPlaceId(anyLong())).thenReturn(Optional.of(placeOwner));

        PlaceOwner retrievedService = placeOwnerService.getByPlaceId(1L);

        assertEquals(placeOwner, retrievedService); 
    } 
    
    @Test 
    public void savePlaceReservation() { 
        when(placeReservationRepository.save(any(PlaceReservation.class))).thenReturn(placeReservation);

        placeReservationService.save(placeReservation); 

        verify(placeReservationRepository, times(1)).save(placeReservation);
    }
    
    @Test
    public void getPlaceFinalPrice() { 
        Place place = new Place();
        place.setPricePerHour(new BigDecimal("10.00")); 

        LocalDateTime startsAt = LocalDateTime.of(2024, 3, 23, 10, 0);
        LocalDateTime endsAt = LocalDateTime.of(2024, 3, 23, 12, 0);
        PersistPlaceReservationDTO persistDTO = new PersistPlaceReservationDTO();
        persistDTO.setStartsAt(startsAt);
        persistDTO.setEndsAt(endsAt);

        BigDecimal placeFinalPrice = getFinalPrice(persistDTO, new ArrayList<>(Collections.singletonList(place)));

        long duration = Duration.between(startsAt, endsAt).toMinutes();
        BigDecimal pricePerHour = place.getPricePerHour();
        BigDecimal expectedPrice = pricePerHour.multiply(BigDecimal.valueOf(duration)).
        		divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

        assertEquals(expectedPrice.doubleValue(), placeFinalPrice.doubleValue(), 0.5);
    }
    
    @Test
    public void getServicesFinalPrice() { 
        Service service1 = new Service();
        service1.setPricePerHour(new BigDecimal("20.00")); 
        Service service2 = new Service();
        service2.setPricePerHour(new BigDecimal("30.00")); 
        List<Product> services = Arrays.asList(service1, service2);

        LocalDateTime startsAt = LocalDateTime.of(2024, 3, 23, 10, 0);
        LocalDateTime endsAt = LocalDateTime.of(2024, 3, 23, 12, 0);
        PersistPlaceReservationDTO persistDTO = new PersistPlaceReservationDTO();
        persistDTO.setStartsAt(startsAt);
        persistDTO.setEndsAt(endsAt);

        BigDecimal servicesFinalPrice = getFinalPrice(persistDTO, services);

        long duration = Duration.between(startsAt, endsAt).toMinutes();
        BigDecimal expectedPrice = BigDecimal.ZERO;
        for (Product service : services) {
            BigDecimal pricePerMinute = service.getPricePerHour().divide(BigDecimal.valueOf(60), 2, 
            		RoundingMode.HALF_UP); 
            expectedPrice = expectedPrice.add(pricePerMinute.multiply(BigDecimal.valueOf(duration)));
        }

        assertEquals(expectedPrice, servicesFinalPrice);
    }
}
