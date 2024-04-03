package com.rentspace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rentspace.DTO.listed.ListedPlaceDTO;
import com.rentspace.DTO.persist.product.PersistPlaceDTO;
import com.rentspace.DTO.persist.reservation.PersistPlaceReservationDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.DTO.response.product.ResponsePlaceDTO;
import com.rentspace.DTO.response.reservation.ResponsePlaceReservationDTO;
import com.rentspace.controller.PlaceController;
import com.rentspace.controller.PlaceReservationController;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.Status;
import com.rentspace.service.PlaceOwnerService;
import com.rentspace.service.PlaceReservationService;
import com.rentspace.service.PlaceService;

public class PlaceControllerTest {

    @Mock
    private PlaceService placeService;
    
    @Mock
    private PlaceOwnerService placeOwnerService;
    
    @Mock
    private PlaceReservationService placeReservationService;

    @InjectMocks
    private PlaceController placeController;
    
    @InjectMocks
    private PlaceReservationController placeReservationController;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    	placeController = new PlaceController(placeService); 
    }

    @Test
    public void createPlace() {
        PersistPlaceDTO persistPlaceDTO = new PersistPlaceDTO("Place", "Description",
                Collections.singletonList("media"), "Address", "City",
                "neigbohood", "complement", "zipCode",
                BigDecimal.valueOf(100), 50, 1L);
   
        ResponseUserDTO responseUserDTO = new ResponseUserDTO(1L, "Rosane Fortuna", "",
                "rosanefortuna@gmail.com", "83911111111", "");
        
        ResponsePlaceDTO responsePlaceDTO = new ResponsePlaceDTO(2L, "Place", "Description",
                "Address", "City", "neighborhood",
                "complement", "zipCode", BigDecimal.valueOf(100), 50, responseUserDTO);
        
        when(placeService.create(persistPlaceDTO)).thenReturn(responsePlaceDTO);

        ResponseEntity<ResponsePlaceDTO> responseEntity = placeController.create(persistPlaceDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responsePlaceDTO, responseEntity.getBody());
    }
    
    @Test
    public void viewPlace() {
        Long placeId = 1L;
        ResponsePlaceDTO expectedResponse = new ResponsePlaceDTO();

        when(placeService.view(placeId)).thenReturn(expectedResponse);

        ResponseEntity<ResponsePlaceDTO> responseEntity = placeController.view(placeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
    
    @Test
    public void createPlaceReservation() {
        PlaceReservationController placeReservationController = 
        		new PlaceReservationController(placeReservationService);

        PersistPlaceReservationDTO persistDTO = new PersistPlaceReservationDTO(
                LocalDateTime.of(2024, 3, 21, 10, 0), LocalDateTime.of(2024, 3, 21, 12, 0),
                PaymentMethod.CREDIT, 2, 1L, 10, Collections.singletonList(1L), 2L); 

        ResponsePlaceReservationDTO expectedResponse = new ResponsePlaceReservationDTO(
                1L, LocalDateTime.of(2024, 3, 21, 10, 0), LocalDateTime.of(2024, 3, 21, 12, 0),
                PaymentMethod.CREDIT, 2, new ResponsePlaceDTO(), 10, Status.ACCEPTED,
                Collections.emptyList(), new ResponseUserDTO(), BigDecimal.ZERO, BigDecimal.ZERO);
      
        when(placeReservationService.create(persistDTO)).thenReturn(expectedResponse);

        ResponseEntity<ResponsePlaceReservationDTO> responseEntity = placeReservationController.
        		create(persistDTO); 
        
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
    
    @Test
    public void viewAllPlaces() { 
        PlaceService placeService = mock(PlaceService.class);
        List<ListedPlaceDTO> expectedPlaces = Arrays.asList(
            new ListedPlaceDTO(1L, "Place 1"),
            new ListedPlaceDTO(2L, "Place 2")
        );
        when(placeService.viewAll()).thenReturn(expectedPlaces);

        PlaceController placeController = new PlaceController(placeService);

        ResponseEntity<List<ListedPlaceDTO>> responseEntity = placeController.viewAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedPlaces, responseEntity.getBody());
    }

}
