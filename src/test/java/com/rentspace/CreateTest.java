package com.rentspace;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.rentspace.DTO.persist.product.PersistPlaceDTO;
import com.rentspace.DTO.persist.product.PersistServiceDTO;
import com.rentspace.DTO.persist.reservation.PersistPlaceReservationDTO;
import com.rentspace.DTO.persist.reservation.PersistServiceReservationDTO;
import com.rentspace.DTO.response.product.ResponsePlaceDTO;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.products.ServiceNature;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.PlaceReservation;
import com.rentspace.model.reservation.ServiceReservation;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.repository.PlaceRepository;
import com.rentspace.repository.PlaceReservationRepository;
import com.rentspace.repository.ServiceRepository;
import com.rentspace.repository.ServiceReservationRepository;
import com.rentspace.service.EventOwnerService;
import com.rentspace.service.PlaceOwnerService;
import com.rentspace.service.PlaceReservationService;
import com.rentspace.service.PlaceService;
import com.rentspace.service.ServiceOwnerService;
import com.rentspace.service.ServiceReservationService;
import com.rentspace.service.ServiceService;
import com.rentspace.util.ModelMapperFuncs;

public class CreateTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private ModelMapperFuncs modelMapperFuncs;

    @InjectMocks
    private PlaceService placeService;

    @Mock
    private PlaceOwnerService placeOwnerService;

    @Mock
    private ServiceService serviceService;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ServiceOwnerService serviceOwnerService;

    @Mock
    private PlaceService placeServices;

    @Mock
    private EventOwnerService eventOwnerService;

    @Mock
    private ServiceReservationRepository serviceReservationRepository;

    @Mock
    private PlaceReservationRepository placeReservationRepository;

    @InjectMocks
    private ServiceReservationService serviceReservationService;

    @Mock
    private ServiceReservationService serviceReservation;

    @InjectMocks
    private PlaceReservationService placeReservationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createPlace() {
        PersistPlaceDTO persistPlaceDTO = new PersistPlaceDTO();
        persistPlaceDTO.setTitle("Test Place");
        persistPlaceDTO.setDescription("Test Description");
        persistPlaceDTO.setMedia(new ArrayList<>());
        persistPlaceDTO.setAddress("Test Address");
        persistPlaceDTO.setCity("Test City");
        persistPlaceDTO.setPricePerHour(BigDecimal.TEN);
        persistPlaceDTO.setMaximumCapacity(100);

        ResponsePlaceDTO responsePlaceDTO = new ResponsePlaceDTO();
        responsePlaceDTO.setId(1L);
        responsePlaceDTO.setTitle("Test Place");
        responsePlaceDTO.setDescription("Test Description");
        responsePlaceDTO.setAddress("Test Address");
        responsePlaceDTO.setCity("Test City");
        responsePlaceDTO.setPricePerHour(BigDecimal.TEN);
        responsePlaceDTO.setMaximumCapacity(100);

        PlaceOwner owner = new PlaceOwner();
        owner.setPlaces(new ArrayList<>());

        when(placeOwnerService.get(any())).thenReturn(owner);
        when(placeRepository.save(any())).thenReturn(new Place());
        when(modelMapperFuncs.map(any(), any())).thenReturn(responsePlaceDTO);

        placeService.create(persistPlaceDTO);

        verify(placeOwnerService, times(1)).get(any());
        verify(placeRepository, times(1)).save(any());
    }

    @Test
    public void createService() throws IOException {
        List<MultipartFile> mockFile = List.of(mock(MultipartFile.class));

        PersistServiceDTO persistServiceDTO = new PersistServiceDTO();
        persistServiceDTO.setTitle("Test Service");
        persistServiceDTO.setDescription("Test Description");
        persistServiceDTO.setPricePerHour(BigDecimal.TEN);
        persistServiceDTO.setServiceNature(ServiceNature.BAR);
        persistServiceDTO.setPeopleInvolved(5);
        List<Long> placesIdsRelated = new ArrayList<>();
        placesIdsRelated.add(1L);
        placesIdsRelated.add(2L);
        persistServiceDTO.setPlacesIdsRelated(placesIdsRelated);

        ServiceOwner owner = new ServiceOwner();
        owner.setServices(new ArrayList<>());

        Place place1 = new Place();
        Place place2 = new Place();
        place1.setServices(new ArrayList<>());
        place2.setServices(new ArrayList<>());

        when(serviceOwnerService.get(any())).thenReturn(owner);
        when(placeServices.get(1L)).thenReturn(place1);
        when(placeServices.get(2L)).thenReturn(place2);
        when(serviceRepository.save(any())).thenReturn(new Service());

        serviceService.create(persistServiceDTO, mockFile);

        verify(serviceOwnerService, times(1)).get(any());
        verify(placeServices, times(1)).get(1L);
        verify(placeServices, times(1)).get(2L);
        verify(serviceRepository, times(1)).save(any());
    }

    @Test
    void createServiceReservation() {
        PersistServiceReservationDTO persistDTO = new PersistServiceReservationDTO(
                LocalDateTime.now(), LocalDateTime.now().plusHours(1), PaymentMethod.CREDIT,
                1, 1L, 1L, "123 Main St", "City");

        EventOwner eventOwner = new EventOwner();
        eventOwner.setServices(new ArrayList<>());

        Service service = new Service();
        service.setPricePerHour(BigDecimal.valueOf(50));

        ServiceOwner serviceOwner = new ServiceOwner();
        serviceOwner.setReservations(new ArrayList<>());

        ServiceReservation reservation = new ServiceReservation();

        List<Place> relatedPlaces = new ArrayList<>();
        Place place = new Place();
        place.setAddress("123 Main St");
        place.setCity("City");
        relatedPlaces.add(place);

        when(eventOwnerService.get(anyLong())).thenReturn(eventOwner);
        when(serviceService.get(anyLong())).thenReturn(service);
        when(serviceService.getRelatedPlaces(anyLong())).thenReturn(relatedPlaces);
        when(serviceOwnerService.getByServiceId(anyLong())).thenReturn(serviceOwner);
        when(serviceReservationRepository.save(any())).thenReturn(reservation);

        serviceReservationService.create(persistDTO);

        verify(eventOwnerService, times(1)).get(anyLong());
        verify(serviceService, times(1)).get(anyLong());
        verify(serviceReservationRepository, times(1)).save(any());
    }

    @Test
    void createPlaceReservation() {
        PersistPlaceReservationDTO persistDTO = new PersistPlaceReservationDTO(
                LocalDateTime.now(), LocalDateTime.now().plusHours(1), PaymentMethod.CREDIT,
                1, 1L, 10, Collections.singletonList(1L), 1L);

        EventOwner eventOwner = new EventOwner();
        eventOwner.setPlaces(new ArrayList<>());

        Place place = new Place();
        place.setPricePerHour(BigDecimal.valueOf(50));
        place.setMaximumCapacity(50);
        place.setServices(new ArrayList<>());

        Service service = new Service();
        service.setPeopleInvolved(2);
        service.setPricePerHour(BigDecimal.valueOf(50));
        place.getServices().add(service);

        PlaceOwner placeOwner = new PlaceOwner();
        placeOwner.setReservations(new ArrayList<>());

        PlaceReservation reservation = new PlaceReservation();

        when(placeServices.get(anyLong())).thenReturn(place);
        when(serviceService.get(anyLong())).thenReturn(service);
        when(eventOwnerService.get(anyLong())).thenReturn(eventOwner);
        when(placeOwnerService.getByPlaceId(anyLong())).thenReturn(placeOwner);
        when(placeReservationRepository.save(any())).thenReturn(reservation);

        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            ServiceReservation serviceReservation = (ServiceReservation) args[0];
            return null;
        }).when(serviceReservation).save(any(ServiceReservation.class));

        placeReservationService.create(persistDTO);

        verify(eventOwnerService, times(1)).get(anyLong());
        verify(placeServices, times(1)).get(anyLong());
        verify(placeReservationRepository, times(1)).save(any());
    }
}
