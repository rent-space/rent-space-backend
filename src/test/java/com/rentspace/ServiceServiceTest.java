package com.rentspace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.rentspace.controller.PlaceController;
import com.rentspace.controller.PlaceReservationController;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.Status;
import com.rentspace.model.user.AppUser;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.model.products.Service;
import com.rentspace.model.products.ServiceNature;
import com.rentspace.repository.ServiceOwnerRepository;
import com.rentspace.repository.ServiceRepository;
import com.rentspace.repository.UserRepository;
import com.rentspace.service.ServiceService;
import com.rentspace.service.PlaceService;
import com.rentspace.service.ServiceOwnerService;
import com.rentspace.service.UserService;
import com.rentspace.service.PlaceOwnerService;
import com.rentspace.service.PlaceReservationService;
import com.rentspace.DTO.persist.PersistPlaceDTO;
import com.rentspace.DTO.persist.PersistPlaceReservationDTO;
import com.rentspace.DTO.response.ResponsePlaceDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.DTO.response.ResponsePlaceReservationDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceServiceTest {

	@Mock
	private ServiceRepository serviceRepository;
	
	@Mock
	private ServiceOwnerRepository serviceOwnerRepository; 
	
	@Mock
    private ServiceService serviceService;
	
	@Mock
	private ServiceOwnerService serviceOwnerService; 
	
	private Service service;
	
	private ServiceOwner serviceOwner;  

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    	serviceService = new ServiceService(serviceRepository, null, null);
    	serviceOwnerService = new ServiceOwnerService(serviceOwnerRepository); 
    	service = new Service(); 
    	serviceOwner = new ServiceOwner(); 
    }
    
    @Test
    public void saveService() {
        when(serviceRepository.save(any(Service.class))).thenReturn(service);

        serviceService.save(service);

        verify(serviceRepository, times(1)).save(service);
    }
    
    @Test
    public void getServiceNatures() {
        List<String> serviceNatures = serviceService.getServiceNatures();

        List<String> expectedNatures = List.of(Arrays.toString(ServiceNature.values()));
        assertEquals(expectedNatures, serviceNatures);
    }
     
    @Test 
    public void getServiceById() {
        when(serviceRepository.findById(anyLong())).thenReturn(Optional.of(service));

        Service retrievedService = serviceService.get(1L);

        assertEquals(service, retrievedService);
    }
    
    @Test
    public void saveServiceOwner() {
        when(serviceOwnerRepository.save(any(ServiceOwner.class))).thenReturn(serviceOwner);

        serviceOwnerService.save(serviceOwner);

        verify(serviceOwnerRepository, times(1)).save(serviceOwner);
    }
    
    @Test 
    public void getServiceOwnerById() {
        when(serviceOwnerRepository.findById(anyLong())).thenReturn(Optional.of(serviceOwner));

        ServiceOwner retrievedService = serviceOwnerService.get(1L);

        assertEquals(serviceOwner, retrievedService);
    }

}
