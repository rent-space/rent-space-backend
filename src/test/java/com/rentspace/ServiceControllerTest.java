package com.rentspace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.rentspace.controller.ServiceController;
import com.rentspace.service.ServiceService;
import com.rentspace.service.ServiceOwnerService;
import com.rentspace.model.products.ServiceNature;
import com.rentspace.DTO.persist.product.PersistServiceDTO;
import com.rentspace.DTO.response.product.ResponseServiceDTO;
import com.rentspace.DTO.response.ResponseUserDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceControllerTest {

    @Mock
    private ServiceService serviceService;
    
    @Mock
    private ServiceOwnerService serviceOwnerService;

    @InjectMocks
    private ServiceController serviceController;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createService() { 
        PersistServiceDTO persistServiceDTO = new PersistServiceDTO("Title", "Description",
                Collections.singletonList("media"), "Address", "City",
                BigDecimal.valueOf(100), 4L, ServiceNature.BARMEN, 20, null);
        
        ResponseUserDTO responseUserDTO = new ResponseUserDTO(0L, "Ricardo Fagundes", "",
                "rfagundes@gmail.com", "83911111111", null); 
        
        ResponseServiceDTO responseServiceDTO = new ResponseServiceDTO(3L, "Title", "Description", 
        		"Address", "City", BigDecimal.valueOf(100), responseUserDTO, ServiceNature.BARMEN, 20, null);
                
        when(serviceService.create(persistServiceDTO)).thenReturn(responseServiceDTO);

        ServiceController serviceController = new ServiceController(serviceService);

        ResponseEntity<ResponseServiceDTO> responseEntity = serviceController.create(persistServiceDTO);
 
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responseServiceDTO, responseEntity.getBody());
    }
    
    @Test
    public void getServiceNatures() {
    	List<String> expectedServiceNatures = Arrays.stream(ServiceNature.values())
                .map(Enum::name)
                .collect(Collectors.toList()); 
        
        when(serviceService.getServiceNatures()).thenReturn(expectedServiceNatures);

        ResponseEntity<List<String>> responseEntity = serviceController.getServiceNatures();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedServiceNatures, responseEntity.getBody());
    }
    
}
