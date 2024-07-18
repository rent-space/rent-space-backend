package com.rentspace;

import com.rentspace.model.user.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.rentspace.controller.ServiceController;
import com.rentspace.controller.ServiceReservationController;
import com.rentspace.service.ServiceService;
import com.rentspace.service.ServiceOwnerService;
import com.rentspace.service.ServiceReservationService;
import com.rentspace.model.products.ServiceNature;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.Status;
import com.rentspace.DTO.persist.product.PersistServiceDTO;
import com.rentspace.DTO.response.product.ResponseServiceDTO;
import com.rentspace.DTO.persist.reservation.PersistServiceReservationDTO;
import com.rentspace.DTO.response.reservation.ResponseServiceReservationDTO;
import com.rentspace.DTO.response.ResponseUserDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Mock
    private ServiceReservationService serviceReservationService;

    @InjectMocks
    private ServiceReservationController serviceReservationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createService() throws IOException {
        PersistServiceDTO persistServiceDTO = new PersistServiceDTO("Title", "Description",
                Collections.singletonList("media"), "Address", "City", "Neighborhood",
                BigDecimal.valueOf(100), 4L, ServiceNature.BARMEN, 20, null);

        ResponseUserDTO responseUserDTO = new ResponseUserDTO(0L, "Ricardo Fagundes", "",
                "rfagundes@gmail.com", "83911111111", null, UserType.SERVICE_OWNER);

        ResponseServiceDTO responseServiceDTO = new ResponseServiceDTO(3L, "Title", "Description",
                "Address", "City", BigDecimal.valueOf(100), responseUserDTO, ServiceNature.BARMEN, 20, null,
                new ArrayList<>());

        MultipartFile mockFile = mock(MultipartFile.class);
        when(serviceService.create(persistServiceDTO, mockFile)).thenReturn(responseServiceDTO);

        ResponseEntity<ResponseServiceDTO> responseEntity = serviceController.create(persistServiceDTO, mockFile);

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

    @Test
    void createServiceReservation() {
        PersistServiceReservationDTO persistDTO = new PersistServiceReservationDTO(
                LocalDateTime.now(), LocalDateTime.now().plusHours(1), PaymentMethod.CREDIT,
                1, 1L, 1L, "123 Main St", "City");

        ResponseServiceReservationDTO expectedResponseDTO = new ResponseServiceReservationDTO(
                1L, LocalDateTime.now(), LocalDateTime.now().plusHours(1), PaymentMethod.CREDIT,
                1, new ResponseServiceDTO(), new ResponseUserDTO(), Status.PENDING, BigDecimal.TEN);

        when(serviceReservationService.create(any())).thenReturn(expectedResponseDTO);

        ResponseEntity<ResponseServiceReservationDTO> responseEntity = serviceReservationController
                .create(persistDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponseDTO, responseEntity.getBody());

        verify(serviceReservationService, times(1)).create(any());
    }
}
