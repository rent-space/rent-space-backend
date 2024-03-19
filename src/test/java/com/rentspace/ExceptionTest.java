package com.rentspace;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.AppUser;
import com.rentspace.model.user.UserType;
import com.rentspace.repository.PlaceRepository;
import com.rentspace.repository.PlaceOwnerRepository;
import com.rentspace.repository.ServiceOwnerRepository;
import com.rentspace.repository.UserRepository;
import com.rentspace.service.PlaceOwnerService;
import com.rentspace.service.ServiceOwnerService;
import com.rentspace.service.PlaceService;
import com.rentspace.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }
	
	@Test 
    void exceptionEmailAlreadyExists() {
        PersistUserDTO persistUserDTO = new PersistUserDTO(UserType.EVENT_OWNER, "Renato Ferreira", "", "rf@gmail.com", "", "");
 
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new AppUser()));

        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
            userService.create(persistUserDTO);
        });
        assertEquals(EMAIL_ALREADY_EXISTS, exception.getMessage());
    }
    
    @Test
    public void exceptionInvalidPlaceOwnerId() {
        Long ownerId = 3L;

        when(placeOwnerRepository.findById(ownerId)).thenReturn(Optional.empty());

        PlaceOwnerService placeOwnerService = new PlaceOwnerService(placeOwnerRepository);

        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
            placeOwnerService.get(ownerId);
        });
        assertEquals(INVALID_PLACE_OWNER_ID + ownerId, exception.getMessage());
    }
    
    @Test
    public void exceptionInvalidPlaceId() {
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
    public void exceptionInvalidServiceOwnerId() {
        Long ownerId = 3L; 

        when(serviceOwnerRepository.findById(ownerId)).thenReturn(Optional.empty());

        ServiceOwnerService serviceOwnerService = new ServiceOwnerService(serviceOwnerRepository);

        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
        	serviceOwnerService.get(ownerId);
        });
        assertEquals(INVALID_SERVICE_OWNER_ID + ownerId, exception.getMessage());
    }
    
}
