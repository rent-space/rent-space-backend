package com.rentspace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rentspace.DTO.persist.reservation.PersistServiceReservationDTO;
import com.rentspace.DTO.response.product.ResponseServiceDTO;
import com.rentspace.DTO.response.reservation.ResponseServiceReservationDTO;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.products.ServiceNature;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.ServiceReservation;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.repository.ServiceOwnerRepository;
import com.rentspace.repository.ServiceRepository;
import com.rentspace.repository.ServiceReservationRepository;
import com.rentspace.service.EventOwnerService;
import com.rentspace.service.PlaceService;
import com.rentspace.service.ServiceOwnerService;
import com.rentspace.service.ServiceReservationService;
import com.rentspace.service.ServiceService;

public class ServiceServiceTest {

	@Mock
	private ServiceRepository serviceRepository;
	
	@Mock
	private ServiceOwnerRepository serviceOwnerRepository; 
	
	@Mock
	private ServiceReservationRepository serviceReservationRepository;
	
	@Mock
    private ServiceService serviceService;
	
	@Mock
	private ServiceOwnerService serviceOwnerService; 
	
	private ServiceReservation serviceReservation;
	
	private Service service;
	
	private ServiceOwner serviceOwner;  

    @InjectMocks
	private ServiceReservationService serviceReservationService; 

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    	serviceService = new ServiceService(serviceRepository, null, null);
    	serviceOwnerService = new ServiceOwnerService(serviceOwnerRepository); 
    	service = new Service(); 
    	serviceOwner = new ServiceOwner(); 
    	serviceReservation = new ServiceReservation();
    	serviceReservationService = new ServiceReservationService(serviceReservationRepository, null, 
    			serviceOwnerService, serviceService, null); 
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
      
    @Test 
    public void getServiceOwnerByServiceId() { 
        when(serviceOwnerRepository.findByServiceId(anyLong())).thenReturn(Optional.of(serviceOwner));

        ServiceOwner retrievedService = serviceOwnerService.getByServiceId(1L); 

        assertEquals(serviceOwner, retrievedService);
    }
    
    @Test 
    public void saveServiceReservation() { 
        when(serviceReservationRepository.save(any(ServiceReservation.class))).thenReturn(serviceReservation);

        serviceReservationService.save(serviceReservation); 

        verify(serviceReservationRepository, times(1)).save(serviceReservation);
    }
    
    @Test
    void checkAvailableServicePlaceFound() { 
        PersistServiceReservationDTO persistDTO = new PersistServiceReservationDTO(
                LocalDateTime.now(), LocalDateTime.now().plusHours(1), PaymentMethod.CREDIT,
                1, 1L, 1L, "123 Main St", "City"); 

        Service service = new Service();        
        List<Place> relatedPlaces = new ArrayList<>();
        Place place = new Place();  
        place.setAddress("123 Main St");
        place.setCity("City");
        relatedPlaces.add(place); 

        when(serviceService.getRelatedPlaces(anyLong())).thenReturn(relatedPlaces);
        when(serviceService.getRelatedPlaces(null)).thenReturn(relatedPlaces);

        serviceReservationService.checkAvailableService(persistDTO, service);
    }
    
    @Test
    public void viewService() {
        Long serviceId = 1L;
        Service mockService = new Service();
        mockService.setTitle("Test Service");
        
        ServiceRepository serviceRepository = mock(ServiceRepository.class);
        when(serviceRepository.findById(serviceId)).thenReturn(Optional.of(mockService));

        ServiceOwnerService serviceOwnerService = mock(ServiceOwnerService.class);
        ServiceOwner mockOwner = new ServiceOwner();
        when(serviceOwnerService.getByServiceId(serviceId)).thenReturn(mockOwner);

        PlaceService placeService = mock(PlaceService.class);
        List<Place> mockPlaces = Arrays.asList(new Place(), new Place());
        when(placeService.getByExclusiveService(serviceId)).thenReturn(mockPlaces);

        ServiceService serviceService = new ServiceService(serviceRepository, serviceOwnerService, placeService);

        ResponseServiceDTO result = serviceService.view(serviceId);

        assertEquals(mockService.getId(), result.getId());
        assertEquals(mockService.getTitle(), result.getTitle()); 
        assertEquals(mockPlaces.size(), result.getPlacesRelated().size());
    }
    
    @Test
    public void getServiceReservationById() {
        Long reservationId = 1L;
        ServiceReservation mockReservation = new ServiceReservation();

        ServiceReservationRepository serviceReservationRepository = mock(ServiceReservationRepository.class);
        when(serviceReservationRepository.findById(reservationId)).thenReturn(Optional.of(mockReservation));

        ServiceReservationService serviceReservationService = new ServiceReservationService(serviceReservationRepository, 
        		null, serviceOwnerService, serviceService, null);

        ServiceReservation result = serviceReservationService.get(reservationId);

        assertNotNull(result);
    }

    @Test
    public void testViewServiceReservation() {
        Long reservationId = 1L; 
        ServiceReservation mockReservation = new ServiceReservation();

        mockReservation.setProduct(new Service()); 
        mockReservation.setFinalPrice(BigDecimal.TEN); 

        ServiceReservationRepository serviceReservationRepository = mock(ServiceReservationRepository.class);
        when(serviceReservationRepository.findById(reservationId)).thenReturn(Optional.of(mockReservation));

        EventOwnerService eventOwnerService = mock(EventOwnerService.class);
        EventOwner mockEventOwner = new EventOwner();

        when(eventOwnerService.getByServiceReservation(reservationId)).thenReturn(mockEventOwner);

        ServiceOwnerService serviceOwnerService = mock(ServiceOwnerService.class);
        ServiceOwner mockServiceOwner = new ServiceOwner();
        when(serviceOwnerService.getByServiceId(mockReservation.getProduct().getId())).thenReturn(mockServiceOwner);

        PlaceService placeService = mock(PlaceService.class);
        List<Place> mockPlaces = new ArrayList<>();
        when(placeService.getByExclusiveService(anyLong())).thenReturn(mockPlaces);

        ServiceReservationService serviceReservationService = new ServiceReservationService(
            serviceReservationRepository, eventOwnerService, serviceOwnerService, serviceService, placeService);

        ResponseServiceReservationDTO result = serviceReservationService.view(reservationId);

        assertNotNull(result);
    
    }
}
