/*package com.rentspace;

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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.rentspace.DTO.listed.ListedServiceDTO;
import com.rentspace.DTO.persist.reservation.PersistServiceReservationDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.DTO.response.product.ResponseProductDTO;
import com.rentspace.DTO.response.product.ResponseServiceDTO;
import com.rentspace.DTO.response.reservation.ResponseServiceReservationDTO;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.products.ServiceNature;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.ServiceReservation;
import com.rentspace.model.reservation.Status;
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
        when(placeService.getAllByExclusiveService(serviceId)).thenReturn(mockPlaces);

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
        when(placeService.getAllByExclusiveService(anyLong())).thenReturn(mockPlaces);

        ServiceReservationService serviceReservationService = new ServiceReservationService(
            serviceReservationRepository, eventOwnerService, serviceOwnerService, serviceService, placeService);

        ResponseServiceReservationDTO result = serviceReservationService.view(reservationId);

        assertNotNull(result);
    
    }
    
    @Test
    public void updateStatusServiceReservation() {
        ServiceReservationRepository serviceReservationRepository = mock(ServiceReservationRepository.class);
        ServiceOwnerService serviceOwnerService = mock(ServiceOwnerService.class);
        PlaceService placeService = mock(PlaceService.class);
        EventOwnerService eventOwnerService = mock(EventOwnerService.class);

        ServiceReservationService serviceReservationService = new ServiceReservationService(serviceReservationRepository, 
        		eventOwnerService, serviceOwnerService, serviceService, placeService);

        Long reservationId = 1L;
        Status status = Status.ACCEPTED; 

        ServiceReservation mockReservation = new ServiceReservation();
        mockReservation.setProduct(new Service());
        mockReservation.setStatus(Status.PENDING); 

        when(serviceReservationRepository.findById(reservationId)).thenReturn(Optional.of(mockReservation));

        EventOwner mockEventOwner = new EventOwner();
        when(eventOwnerService.getByServiceReservation(reservationId)).thenReturn(mockEventOwner);

        List<Place> mockPlaces = new ArrayList<>(); 
        when(placeService.getAllByExclusiveService(reservationId)).thenReturn((mockPlaces));

        when(serviceReservationRepository.save(any(ServiceReservation.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        ResponseServiceReservationDTO result = serviceReservationService.updateStatus(reservationId, status);

        assertEquals(mockReservation.getId(), result.getId());
        assertEquals(mockReservation.getStatus(), status);
    }
    
    @Test
    public void deleteService() {
        ServiceRepository serviceRepository = mock(ServiceRepository.class);
        ServiceOwnerService serviceOwnerService = mock(ServiceOwnerService.class);
        PlaceService placeService = mock(PlaceService.class);
        ServiceService serviceService = new ServiceService(serviceRepository, serviceOwnerService, placeService);

        Long serviceId = 1L;
        Service service = new Service();
        service.setId(serviceId);

        ServiceOwner owner = new ServiceOwner();
        List<Place> places = new ArrayList<>();
        List<String> media = new ArrayList<>();

        when(serviceRepository.findById(serviceId)).thenReturn(java.util.Optional.ofNullable(service));
        when(placeService.getAllByExclusiveService(serviceId)).thenReturn(places);
        when(serviceOwnerService.getByServiceId(serviceId)).thenReturn(owner);

        ResponseServiceDTO response = serviceService.delete(serviceId);

        verify(serviceRepository, times(1)).delete(service);
        assertEquals(response.getId(), serviceId);
        assertEquals(response.getPlacesRelated(), places);
    }
    
    @Test
    public void viewAllServices() { 
        ServiceRepository serviceRepository = Mockito.mock(ServiceRepository.class);
        ModelMapper modelMapper = Mockito.mock(ModelMapper.class);

        List<Service> services = new ArrayList<>();
        Service service1 = new Service();
        service1.setId(1L);
        service1.setTitle("Service 1");
        service1.setMedia(new ArrayList<>());
        services.add(service1);

        Service service2 = new Service();
        service2.setId(2L);
        service2.setTitle("Service 2");
        List<String> mediaList = new ArrayList<>();
        mediaList.add("media1.jpg");
        mediaList.add("media2.jpg");
        service2.setMedia(mediaList);
        services.add(service2);

        when(serviceRepository.findAll()).thenReturn(services);
        when(modelMapper.map(service1, ListedServiceDTO.class)).thenReturn(new ListedServiceDTO(1L, "Service 1", "", ServiceNature.BAR, new BigDecimal(50)));
        when(modelMapper.map(service2, ListedServiceDTO.class)).thenReturn(new ListedServiceDTO(2L, "Service 2", "media1.jpg", ServiceNature.BARMEN, new BigDecimal("50.3")));

        ServiceService serviceService = new ServiceService(serviceRepository, serviceOwnerService, null);

        List<ListedServiceDTO> result = serviceService.viewAll();

        verify(serviceRepository, Mockito.times(1)).findAll();

        assertEquals(2, result.size());
        assertEquals("Service 1", result.get(0).getTitle());
        assertEquals("", result.get(0).getFirstMedia());
        assertEquals("Service 2", result.get(1).getTitle());
        assertEquals("media1.jpg", result.get(1).getFirstMedia());
    }
    
    @Test
    public void deleteServiceReservation() {
        Long reservationId = 1L;
 
        ServiceReservation serviceReservation = new ServiceReservation();
        serviceReservation.setId(reservationId);
        serviceReservation.setStartsAt(LocalDateTime.now());
        serviceReservation.setEndsAt(LocalDateTime.now().plusHours(1));
        serviceReservation.setPaymentMethod(PaymentMethod.CREDIT);
        serviceReservation.setNumOfInstallments(1);
        serviceReservation.setAddress("Test address");
        serviceReservation.setCity("Test city");
        serviceReservation.setFinalPrice(BigDecimal.TEN);

        ResponseUserDTO eventOwner = new ResponseUserDTO();
        eventOwner.setId(1L);
        eventOwner.setName("John Doe");
        eventOwner.setEmail("john.doe@example.com");

        ResponseProductDTO productDTO = new ResponseProductDTO();
        productDTO.setId(1L);
        productDTO.setTitle("Test Service");
        productDTO.setDescription("Test description");
        productDTO.setAddress("Test address");
        productDTO.setCity("Test city");
        productDTO.setPricePerHour(BigDecimal.TEN);
        productDTO.setOwner(eventOwner);

        ResponseServiceDTO serviceDTO = new ResponseServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setTitle("Test Service");
        serviceDTO.setDescription("Test description");
        serviceDTO.setAddress("Test address");
        serviceDTO.setCity("Test city");
        serviceDTO.setPricePerHour(BigDecimal.TEN);
        serviceDTO.setOwner(eventOwner);

        ResponseServiceReservationDTO expectedResponse = new ResponseServiceReservationDTO();
        expectedResponse.setId(reservationId);
        expectedResponse.setStartsAt(serviceReservation.getStartsAt());
        expectedResponse.setEndsAt(serviceReservation.getEndsAt());
        expectedResponse.setPaymentMethod(serviceReservation.getPaymentMethod());
        expectedResponse.setNumOfInstallments(serviceReservation.getNumOfInstallments());
        expectedResponse.setProduct(serviceDTO);
        expectedResponse.setEventOwner(eventOwner);
        expectedResponse.setStatus(serviceReservation.getStatus());
        expectedResponse.setFinalPrice(serviceReservation.getFinalPrice());

        ServiceReservationRepository serviceReservationRepository = Mockito.mock(ServiceReservationRepository.class);
        when(serviceReservationRepository.findById(reservationId)).thenReturn(java.util.Optional.ofNullable(serviceReservation));

        ModelMapper modelMapper = Mockito.mock(ModelMapper.class);
        when(modelMapper.map(serviceReservation, ResponseServiceReservationDTO.class)).thenReturn(expectedResponse);

        ServiceReservationService serviceReservationService = new ServiceReservationService(serviceReservationRepository, 
        		null, serviceOwnerService, serviceService, null);

        ResponseServiceReservationDTO actualResponse = serviceReservationService.delete(reservationId);

        verify(serviceReservationRepository, Mockito.times(1)).findById(reservationId);
        verify(serviceReservationRepository, Mockito.times(1)).delete(serviceReservation);

        assertEquals(expectedResponse, actualResponse);
    }
    
}
*/