package com.rentspace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.rentspace.model.products.Place; 
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.repository.PlaceRepository;
import com.rentspace.service.PlaceService;
import com.rentspace.util.ModelMapperFuncs;
import com.rentspace.service.PlaceOwnerService;
import com.rentspace.DTO.persist.PersistPlaceDTO; 
import com.rentspace.DTO.response.ResponsePlaceDTO;

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
        persistPlaceDTO.setPlaceOwnerId(1L);

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

        verify(placeOwnerService, times(1)).get(1L);
        verify(placeRepository, times(1)).save(any());
    }
    
}
