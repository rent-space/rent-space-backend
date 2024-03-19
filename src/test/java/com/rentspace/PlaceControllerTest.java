package com.rentspace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.rentspace.controller.PlaceController;
import com.rentspace.service.PlaceService;
import com.rentspace.service.PlaceOwnerService;
import com.rentspace.DTO.persist.PersistPlaceDTO;
import com.rentspace.DTO.response.ResponsePlaceDTO;
import com.rentspace.DTO.response.ResponseUserDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Collections;

public class PlaceControllerTest {

    @Mock
    private PlaceService placeService;
    
    @Mock
    private PlaceOwnerService placeOwnerService;

    @InjectMocks
    private PlaceController placeController;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createPlace() {
        PersistPlaceDTO persistPlaceDTO = new PersistPlaceDTO("Place", "Description",
                Collections.singletonList("media"), "Address", "City",
                BigDecimal.valueOf(100), 50, 1L);
   
        ResponseUserDTO responseUserDTO = new ResponseUserDTO(1L, "Rosane Fortuna", "",
                "rosanefortuna@gmail.com", "83911111111", "");
        
        ResponsePlaceDTO responsePlaceDTO = new ResponsePlaceDTO(2L, "Place", "Description",
                "Address", "City", BigDecimal.valueOf(100), 50, responseUserDTO);
        
        when(placeService.create(persistPlaceDTO)).thenReturn(responsePlaceDTO);

        PlaceController placeController = new PlaceController(placeService);

        ResponseEntity<ResponsePlaceDTO> responseEntity = placeController.create(persistPlaceDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responsePlaceDTO, responseEntity.getBody());
    }
    
}