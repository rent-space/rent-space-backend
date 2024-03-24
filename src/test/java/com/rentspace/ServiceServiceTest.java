package com.rentspace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.model.products.Service;
import com.rentspace.model.products.ServiceNature;
import com.rentspace.repository.ServiceOwnerRepository;
import com.rentspace.repository.ServiceRepository;
import com.rentspace.service.ServiceService;
import com.rentspace.service.ServiceOwnerService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
