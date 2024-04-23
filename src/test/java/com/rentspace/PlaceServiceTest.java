package com.rentspace;

import static com.rentspace.util.ProductUtil.getFinalPrice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.rentspace.DTO.listed.ListedPlaceDTO;
import com.rentspace.DTO.listed.ListedServiceDTO;
import com.rentspace.DTO.persist.product.PersistPlaceDTO;
import com.rentspace.DTO.persist.reservation.PersistPlaceReservationDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.DTO.response.product.ResponsePlaceDTO;
import com.rentspace.DTO.response.reservation.ResponsePlaceReservationDTO;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Product;
import com.rentspace.model.products.Service;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.PlaceReservation;
import com.rentspace.model.reservation.Status;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.repository.EventOwnerRepository;
import com.rentspace.repository.PlaceOwnerRepository;
import com.rentspace.repository.PlaceRepository;
import com.rentspace.repository.PlaceReservationRepository;
import com.rentspace.service.EventOwnerService;
import com.rentspace.service.PlaceOwnerService;
import com.rentspace.service.PlaceReservationService;
import com.rentspace.service.PlaceService;
import com.rentspace.service.ServiceService;
import com.rentspace.util.ModelMapperFuncs;

public class PlaceServiceTest {

	@Mock
    private PlaceRepository placeRepository;
	
	@Mock
	private PlaceOwnerRepository placeOwnerRepository;

	@Mock
	private EventOwnerRepository eventOwnerRepository;

	@Mock
	private PlaceReservationRepository placeReservationRepository;

	@Mock
    private PlaceService placeService;
	
	@Mock
	private PlaceOwnerService placeOwnerService; 

	@Mock
	private EventOwnerService eventOwnerService;  

	@Mock
	private PlaceReservationService placeReservationService;

	@InjectMocks 
	private PlaceService placeServiceIM;

	private Place place;

	private PlaceOwner placeOwner;

	private EventOwner eventOwner;

	private PlaceReservation placeReservation; 

	@Mock
    private ModelMapperFuncs modelMapperFuncs;
	
    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    	place = new Place();
    	placeOwner = new PlaceOwner();
    	eventOwner = new EventOwner();
    	placeService = new PlaceService(placeRepository, null);
    	placeOwnerService = new PlaceOwnerService(placeOwnerRepository);
    	eventOwnerService = new EventOwnerService(eventOwnerRepository); 
    	placeReservation = new PlaceReservation();
    	placeReservationService = new PlaceReservationService(placeReservationRepository, 
    			placeService, null, placeOwnerService, null, null);  
    }
    
    @Test
    public void savePlace() {
        when(placeRepository.save(any(Place.class))).thenReturn(place);

        placeService.save(place);

        verify(placeRepository, times(1)).save(place);
    }
    
    @Test
    public void saveEventOwner() {
        when(eventOwnerRepository.save(any(EventOwner.class))).thenReturn(eventOwner);

        eventOwnerService.save(eventOwner);

        verify(eventOwnerRepository, times(1)).save(eventOwner);
    }

    @Test 
    public void getEventOwnerById() {
        when(eventOwnerRepository.findById(anyLong())).thenReturn(Optional.of(eventOwner));

        EventOwner retrievedService = eventOwnerService.get(1L);

        assertEquals(eventOwner, retrievedService);
    }

    @Test
    public void viewPlace() {
        MockitoAnnotations.openMocks(this);  

        Long placeId = 1L;
        Place mockPlace = new Place(); 
        PlaceOwner mockOwner = new PlaceOwner(); 

        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(mockPlace));
        when(placeOwnerService.getByPlaceId(anyLong())).thenReturn(mockOwner);

        ResponsePlaceDTO response = placeServiceIM.view(placeId); 

        assertEquals(mockPlace.getId(), response.getId());
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
    public void testViewAll() {
        List<Place> mockPlaces = Arrays.asList(
            new Place(), new Place());
        
        when(placeRepository.findAll()).thenReturn(mockPlaces);

        PlaceService placeService = new PlaceService(placeRepository, placeOwnerService);

        List<ListedPlaceDTO> result = placeService.viewAll();

        assertEquals(mockPlaces.size(), result.size());
        assertEquals(mockPlaces.get(0).getId(), result.get(0).getId());
        assertEquals(mockPlaces.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(mockPlaces.get(1).getId(), result.get(1).getId());
        assertEquals(mockPlaces.get(1).getTitle(), result.get(1).getTitle());
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

        BigDecimal placeFinalPrice = getFinalPrice(persistDTO.getStartsAt(), persistDTO.getEndsAt(), new ArrayList<>(Collections.singletonList(place)));

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

        BigDecimal servicesFinalPrice = getFinalPrice(persistDTO.getStartsAt(), persistDTO.getEndsAt(), services);

        long duration = Duration.between(startsAt, endsAt).toMinutes();
        BigDecimal expectedPrice = BigDecimal.ZERO;
        for (Product service : services) {
            BigDecimal pricePerMinute = service.getPricePerHour().divide(BigDecimal.valueOf(60), 2, 
            		RoundingMode.HALF_UP); 
            expectedPrice = expectedPrice.add(pricePerMinute.multiply(BigDecimal.valueOf(duration)));
        }

        assertEquals(expectedPrice, servicesFinalPrice);
    }
    
    @Test
    public void getPlacesByExclusiveService() {
        List<Place> expectedPlaces = Arrays.asList(new Place(), new Place());

        PlaceRepository placeRepository = mock(PlaceRepository.class);
        when(placeRepository.getAllByExclusiveService(1L)).thenReturn(expectedPlaces);

        PlaceService placeService = new PlaceService(placeRepository, placeOwnerService);

        List<Place> result = placeService.getAllByExclusiveService(1L);

        assertEquals(expectedPlaces.size(), result.size());
    }
    
    @Test
    public void getByPlaceReservation() {
        Long reservationId = 1L;
        EventOwner mockOwner = new EventOwner();

        EventOwnerRepository eventOwnerRepository = mock(EventOwnerRepository.class);
        when(eventOwnerRepository.findByPlaceReservation(reservationId)).thenReturn(Optional.of(mockOwner));

        EventOwnerService eventOwnerService = new EventOwnerService(eventOwnerRepository);

        EventOwner result = eventOwnerService.getByPlaceReservation(reservationId);

        assertNotNull(result);
    }

    @Test
    public void getPlaceReservationById() { 
        Long reservationId = 1L;
        EventOwner mockOwner = new EventOwner();

        EventOwnerRepository eventOwnerRepository = mock(EventOwnerRepository.class);
        when(eventOwnerRepository.findByServiceReservation(reservationId)).thenReturn(Optional.of(mockOwner));

        EventOwnerService eventOwnerService = new EventOwnerService(eventOwnerRepository);

        EventOwner result = eventOwnerService.getByServiceReservation(reservationId);

        assertNotNull(result);
    }
    
    @Test
    public void getReservationById() {
        Long reservationId = 1L; 
        PlaceReservation mockReservation = new PlaceReservation();
        
        PlaceReservationRepository placeReservationRepository = mock(PlaceReservationRepository.class);
        when(placeReservationRepository.findById(reservationId)).thenReturn(Optional.of(mockReservation));

        PlaceReservationService placeReservation = new PlaceReservationService(placeReservationRepository, 
        		placeService, null, placeOwnerService, eventOwnerService, null); 

        PlaceReservation result = placeReservation.get(reservationId);

        assertNotNull(result);
    }
    
    @Test
    public void viewReservation() {
        Long reservationId = 1L;
        PlaceReservation mockReservation = new PlaceReservation();

        mockReservation.setProduct(new Place());
        mockReservation.setHiredRelatedServices(new ArrayList<>());

        PlaceReservationRepository placeReservationRepository = mock(PlaceReservationRepository.class);
        when(placeReservationRepository.findById(reservationId)).thenReturn(Optional.of(mockReservation));

        PlaceOwnerService placeOwnerService = mock(PlaceOwnerService.class);
        PlaceOwner mockPlaceOwner = new PlaceOwner();
        when(placeOwnerService.getByPlaceId(mockReservation.getProduct().getId())).thenReturn(mockPlaceOwner);

        EventOwnerService eventOwnerService = mock(EventOwnerService.class);
        EventOwner mockEventOwner = new EventOwner();
        when(eventOwnerService.getByPlaceReservation(reservationId)).thenReturn(mockEventOwner);

        PlaceReservationService placeReservationService = new PlaceReservationService(
            placeReservationRepository, null, null, placeOwnerService, eventOwnerService, null);

        ResponsePlaceReservationDTO result = placeReservationService.view(reservationId);

        assertNotNull(result);
    }
    
    @Test
    public void updateStatusPlaceReservation() {
        PlaceReservationRepository placeReservationRepository = mock(PlaceReservationRepository.class);
        PlaceService placeService = mock(PlaceService.class);
        ServiceService serviceService = mock(ServiceService.class);
        PlaceOwnerService placeOwnerService = mock(PlaceOwnerService.class);
        EventOwnerService eventOwnerService = mock(EventOwnerService.class);

        PlaceReservationService placeReservationService = new PlaceReservationService(placeReservationRepository, 
        		placeService, serviceService, placeOwnerService, eventOwnerService, null);

        Long reservationId = 1L;
        Status status = Status.ACCEPTED;

        PlaceReservation mockReservation = new PlaceReservation();
        mockReservation.setProduct(new Place());
        mockReservation.setHiredRelatedServices(new ArrayList<>());
        mockReservation.setNumOfParticipants(10);
        mockReservation.setStatus(Status.PENDING); 

        when(placeReservationRepository.findById(reservationId)).thenReturn(Optional.of(mockReservation));

        EventOwner mockEventOwner = new EventOwner();
        when(eventOwnerService.getByPlaceReservation(reservationId)).thenReturn(mockEventOwner);

        when(placeReservationRepository.save(any(PlaceReservation.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        ResponsePlaceReservationDTO result = placeReservationService.updateStatus(reservationId, status);

        assertEquals(mockReservation.getId(), result.getId());
        assertEquals(mockReservation.getStatus(), status);
    }
    
//    @Test
//    public void deletePlace() {
//        Long placeId = 1L;
//
//        Place place = new Place();
//        place.setId(placeId);
//        place.setTitle("Test Place");
//        place.setDescription("Test description");
//        place.setAddress("Test address");
//        place.setCity("Test city");
//        place.setPricePerHour(BigDecimal.TEN);
//        place.setMaximumCapacity(100);
//        place.setNeighborhood("Test neighborhood");
//        place.setComplement("Test complement");
//        place.setZipCode("12345");
//
//        ResponseUserDTO owner = new ResponseUserDTO();
//        owner.setId(1L);
//        owner.setName("John Doe");
//        owner.setEmail("john.doe@example.com");
//
//        ResponsePlaceDTO expectedResponse = new ResponsePlaceDTO();
//        expectedResponse.setId(placeId);
//        expectedResponse.setTitle(place.getTitle());
//        expectedResponse.setDescription(place.getDescription());
//        expectedResponse.setAddress(place.getAddress());
//        expectedResponse.setCity(place.getCity());
//        expectedResponse.setPricePerHour(place.getPricePerHour());
//        expectedResponse.setOwner(owner);
//        expectedResponse.setMaximumCapacity(place.getMaximumCapacity());
//        expectedResponse.setNeighborhood(place.getNeighborhood());
//        expectedResponse.setComplement(place.getComplement());
//        expectedResponse.setZipCode(place.getZipCode());
//
//        PlaceRepository placeRepository = Mockito.mock(PlaceRepository.class);
//        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
//
//        ModelMapper modelMapper = Mockito.mock(ModelMapper.class);
//        when(modelMapper.map(place, ResponsePlaceDTO.class)).thenReturn(expectedResponse);
//
//        PlaceService placeService = new PlaceService(placeRepository, placeOwnerService);
//
//        ResponsePlaceDTO actualResponse = placeService.delete(placeId);
//
//        verify(placeRepository, Mockito.times(1)).findById(placeId);
//        verify(placeRepository, Mockito.times(1)).delete(place);
//
//        assertEquals(expectedResponse, actualResponse);
//    } FIXME
    
    @Test
    public void updatePlace() {
        Long placeId = 1L; 
        PersistPlaceDTO persistDTO = new PersistPlaceDTO();
        persistDTO.setTitle("Updated Place");
        persistDTO.setDescription("Updated description");
        persistDTO.setMedia(new ArrayList<>());
        persistDTO.setAddress("Updated address");
        persistDTO.setCity("Updated city");
        persistDTO.setNeighborhood("Updated neighborhood");
        persistDTO.setPricePerHour(BigDecimal.valueOf(20));
        persistDTO.setMaximumCapacity(200);
        persistDTO.setComplement("Updated complement");
        persistDTO.setZipCode("54321");
        persistDTO.setOwnerId(1L);

        Place place = new Place();
        place.setId(placeId);
        place.setTitle("Test Place");
        place.setDescription("Test description");
        place.setMedia(new ArrayList<>());
        place.setAddress("Test address");
        place.setCity("Test city");
        place.setNeighborhood("Test neighborhood");
        place.setPricePerHour(BigDecimal.TEN);
        place.setMaximumCapacity(100);
        place.setComplement("Test complement");
        place.setZipCode("12345");

        PlaceOwner owner = new PlaceOwner();
        owner.setId(1L);
        owner.setName("John Doe");
        owner.setEmail("john.doe@example.com");

        ResponseUserDTO ownerDTO = new ResponseUserDTO();
        ownerDTO.setId(owner.getId());
        ownerDTO.setName(owner.getName());
        ownerDTO.setEmail(owner.getEmail());

        ResponsePlaceDTO expectedResponse = new ResponsePlaceDTO();
        expectedResponse.setId(placeId);
        expectedResponse.setTitle(persistDTO.getTitle());
        expectedResponse.setDescription(persistDTO.getDescription());
        expectedResponse.setAddress(persistDTO.getAddress());
        expectedResponse.setCity(persistDTO.getCity());
        expectedResponse.setNeighborhood(persistDTO.getNeighborhood());
        expectedResponse.setPricePerHour(persistDTO.getPricePerHour());
        expectedResponse.setOwner(ownerDTO);
        expectedResponse.setMaximumCapacity(persistDTO.getMaximumCapacity());
        expectedResponse.setComplement(persistDTO.getComplement());
        expectedResponse.setZipCode(persistDTO.getZipCode());

        PlaceOwnerService placeOwnerService = Mockito.mock(PlaceOwnerService.class);
        when(placeOwnerService.getByPlaceId(placeId)).thenReturn(owner);

        PlaceRepository placeRepository = Mockito.mock(PlaceRepository.class);
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));

        ModelMapper modelMapper = Mockito.mock(ModelMapper.class);
        when(modelMapper.map(persistDTO, Place.class)).thenReturn(place);
        when(modelMapper.map(place, ResponsePlaceDTO.class)).thenReturn(expectedResponse);

        PlaceService placeService = new PlaceService(placeRepository, placeOwnerService);

        ResponsePlaceDTO actualResponse = placeService.update(placeId, persistDTO);

        verify(placeRepository, Mockito.times(1)).findById(placeId);
        verify(placeOwnerService, Mockito.times(1)).getByPlaceId(placeId);

        assertEquals(expectedResponse, actualResponse);
    }
    
    @Test
    public void deletePlaceReservation() {
        Long reservationId = 1L; 

        PlaceReservation placeReservation = new PlaceReservation();
        placeReservation.setId(reservationId);
        placeReservation.setStartsAt(LocalDateTime.now());
        placeReservation.setEndsAt(LocalDateTime.now().plusHours(1));
        placeReservation.setPaymentMethod(PaymentMethod.CREDIT);
        placeReservation.setNumOfInstallments(1);
        placeReservation.setStatus(Status.ACCEPTED);
        placeReservation.setNumOfParticipants(10);
        placeReservation.setHiredRelatedServices(new ArrayList<>());
        placeReservation.setPlaceFinalPrice(BigDecimal.TEN);
        placeReservation.setServicesFinalPrice(BigDecimal.ZERO);

        ResponsePlaceDTO placeDTO = new ResponsePlaceDTO();
        placeDTO.setId(1L);
        placeDTO.setTitle("Test Place");
        placeDTO.setDescription("Test description");
        placeDTO.setAddress("Test address");
        placeDTO.setCity("Test city");
        placeDTO.setPricePerHour(BigDecimal.TEN);
        placeDTO.setOwner(new ResponseUserDTO());

        List<ListedServiceDTO> hiredRelatedServices = new ArrayList<>();

        ResponseUserDTO eventOwner = new ResponseUserDTO();
        eventOwner.setId(1L);
        eventOwner.setName("John Doe");
        eventOwner.setEmail("john.doe@example.com");

        ResponsePlaceReservationDTO expectedResponse = new ResponsePlaceReservationDTO(
            reservationId,
            placeReservation.getStartsAt(),
            placeReservation.getEndsAt(),
            placeReservation.getPaymentMethod(),
            placeReservation.getNumOfInstallments(),
            placeDTO,
            placeReservation.getNumOfParticipants(),
            placeReservation.getStatus(),
            hiredRelatedServices,
            eventOwner,
            placeReservation.getPlaceFinalPrice(),
            placeReservation.getServicesFinalPrice()
        );

        PlaceReservationRepository placeReservationRepository = Mockito.mock(PlaceReservationRepository.class);
        when(placeReservationRepository.findById(reservationId)).thenReturn(Optional.of(placeReservation));

        ModelMapper modelMapper = Mockito.mock(ModelMapper.class);
        when(modelMapper.map(placeReservation, ResponsePlaceReservationDTO.class)).thenReturn(expectedResponse);

        PlaceReservationService placeReservationService = new PlaceReservationService(placeReservationRepository, 
        		placeService, null, placeOwnerService, eventOwnerService, null);

        ResponsePlaceReservationDTO actualResponse = placeReservationService.delete(reservationId);

        verify(placeReservationRepository, Mockito.times(1)).findById(reservationId);
        verify(placeReservationRepository, Mockito.times(1)).delete(placeReservation);
 
        assertEquals(expectedResponse, actualResponse);
    }
    
}
