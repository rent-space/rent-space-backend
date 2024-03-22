package com.rentspace;

import com.rentspace.DTO.persist.PersistPlaceReservationDTO;
import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.DTO.response.ResponsePlaceDTO;
import com.rentspace.DTO.response.ResponsePlaceReservationDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.controller.PlaceReservationController;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.ServiceNature;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.Status;
import com.rentspace.model.user.AppUser;
import com.rentspace.model.user.UserType;
import com.rentspace.repository.PlaceRepository;
import com.rentspace.repository.ServiceRepository;
import com.rentspace.repository.PlaceOwnerRepository;
import com.rentspace.repository.ServiceOwnerRepository;
import com.rentspace.repository.UserRepository;
import com.rentspace.service.PlaceReservationService;
import com.rentspace.service.PlaceOwnerService;
import com.rentspace.service.ServiceOwnerService;
import com.rentspace.service.PlaceService;
import com.rentspace.service.ServiceService;
import com.rentspace.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static com.rentspace.exception.ExceptionMessages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExceptionTest {
	
	@Mock
    private UserRepository<AppUser> userRepository;
	
	@Mock
    private PlaceOwnerRepository placeOwnerRepository;
	
	@Mock
    private ServiceOwnerRepository serviceOwnerRepository;
	
	@Mock
    private PlaceRepository placeRepository;
	
	@Mock
    private ServiceRepository serviceRepository;

    private UserService userService;
    
    private PlaceReservationService placeReservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
        placeReservationService = new PlaceReservationService(null, null, null, null, null);
    }
	
	@Test 
    void emailAlreadyExists() {
        PersistUserDTO persistUserDTO = new PersistUserDTO(UserType.EVENT_OWNER, "Renato Ferreira", "", 
        		"rf@gmail.com", "83944444444", "");
 
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new AppUser()));

        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
            userService.create(persistUserDTO);
        });
        assertEquals(EMAIL_ALREADY_EXISTS, exception.getMessage());
    }
    
    @Test
    public void invalidPlaceOwnerId() {
        Long ownerId = 3L;

        when(placeOwnerRepository.findById(ownerId)).thenReturn(Optional.empty());

        PlaceOwnerService placeOwnerService = new PlaceOwnerService(placeOwnerRepository);

        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
            placeOwnerService.get(ownerId);
        });
        assertEquals(INVALID_PLACE_OWNER_ID + ownerId, exception.getMessage());
    }
    
    @Test
    public void invalidPlaceId() {
    	Long ownerId = 3L;

    	when(placeOwnerRepository.findById(ownerId)).thenReturn(Optional.empty());

        PlaceOwnerService placeOwnerService = new PlaceOwnerService(placeOwnerRepository);

        PlaceService placeService = new PlaceService(placeRepository, placeOwnerService);

        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
            placeService.get(ownerId);
        });
        assertEquals(INVALID_PLACE_ID + ownerId, exception.getMessage());
    }
    
    @Test
    public void invalidServiceOwnerId() {
        Long ownerId = 3L; 

        when(serviceOwnerRepository.findById(ownerId)).thenReturn(Optional.empty());

        ServiceOwnerService serviceOwnerService = new ServiceOwnerService(serviceOwnerRepository);

        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
        	serviceOwnerService.get(ownerId);
        });
        assertEquals(INVALID_SERVICE_OWNER_ID + ownerId, exception.getMessage());
    }
    
    @Test
    public void invalidServiceId() {
    	Long ownerId = 3L;
    	
    	when(serviceOwnerRepository.findById(ownerId)).thenReturn(Optional.empty());

        ServiceService serviceService = new ServiceService(serviceRepository, new ServiceOwnerService(null), 
        		new PlaceService(null, null));

        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
        	serviceService.get(ownerId);
        });
        assertEquals(INVALID_SERVICE_ID + ownerId, exception.getMessage());
    }
    
    @Test
    public void invalidPaymentFormat() {
        PlaceReservationController placeReservationController = 
        		new PlaceReservationController(placeReservationService);

        PersistPlaceReservationDTO persistDTO = new PersistPlaceReservationDTO(
                LocalDateTime.of(2024, 3, 21, 10, 0), LocalDateTime.of(2024, 3, 21, 12, 0),
                PaymentMethod.PIX, 2, 1L, 10, Collections.singletonList(1L), 2L); 
        
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
        	placeReservationController.create(persistDTO);
        });
        assertEquals(INVALID_PAYMENT_FORMAT, exception.getMessage());
    }
    
    @Test
    public void invalidServiceNature() {
        assertThrows(IllegalArgumentException.class, () -> {
            ServiceNature invalid = ServiceNature.valueOf("INVALID_VALUE");
        }); 
    }
    
    @Test
    public void invalidUserType() {
        assertThrows(IllegalArgumentException.class, () -> {
        	UserType invalid = UserType.valueOf("INVALID_VALUE");
        }); 
    }
    
    @Test
    public void invalidPaymentMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
        	PaymentMethod invalid = PaymentMethod.valueOf("INVALID_VALUE");
        }); 
    }
    
    @Test
    public void invalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
        	Status invalid = Status.valueOf("INVALID_VALUE");
        }); 
    }
        
}
