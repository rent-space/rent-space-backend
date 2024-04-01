package com.rentspace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.products.ServiceNature;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.repository.PlaceRepository;
import com.rentspace.repository.ServiceRepository;
import com.rentspace.service.PlaceService;
import com.rentspace.service.ServiceOwnerService;
import com.rentspace.service.ServiceService;
import com.rentspace.util.ModelMapperFuncs;
import com.rentspace.service.PlaceOwnerService;
import com.rentspace.DTO.persist.product.PersistPlaceDTO; 
import com.rentspace.DTO.persist.product.PersistServiceDTO;
import com.rentspace.DTO.response.product.ResponsePlaceDTO; 

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.*;

public class CreateTest {

	@Mock
    private PlaceRepository placeRepository;

	@Mock
    private ModelMapperFuncs modelMapperFuncs;

	@InjectMocks 
	private PlaceService placeService;

	@Mock
    private PlaceOwnerService placeOwnerService;

	private ServiceService serviceService; 

	@Mock
    private ServiceRepository serviceRepository;

	@Mock
    private ServiceOwnerService serviceOwnerService;

	@Mock
    private PlaceService placeServices;

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
    public void createService() {
        serviceService = new ServiceService(serviceRepository, serviceOwnerService, placeServices);

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

        serviceService.create(persistServiceDTO);

        verify(serviceOwnerService, times(1)).get(any()); 
        verify(placeServices, times(1)).get(1L);
        verify(placeServices, times(1)).get(2L);
        verify(serviceRepository, times(1)).save(any());
    }

}