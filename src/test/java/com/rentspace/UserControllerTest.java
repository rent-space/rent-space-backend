package com.rentspace;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.model.user.UserType;
import com.rentspace.service.UserService;
import com.rentspace.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerUserEventOwner() { 
        PersistUserDTO persistUserDTO = new PersistUserDTO(UserType.EVENT_OWNER, "Ricardo Fagundes", "profile.jpg",
                "rfagundes@example.com", "83911111111", "example.com");
        ResponseUserDTO responseUserDTO = new ResponseUserDTO(1L, "Ricardo Fagundes", "profile.jpg",
                "rfagundes@example.com", "83911111111", "example.com");

        when(userService.create(persistUserDTO)).thenReturn(responseUserDTO);

        ResponseEntity<ResponseUserDTO> responseEntity = userController.create(persistUserDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responseUserDTO, responseEntity.getBody());
    }
    
    @Test
    public void registerUserPlaceOwner() { 
        PersistUserDTO persistUserDTO = new PersistUserDTO(UserType.PLACE_OWNER, "Raquel Farias", "profile.jpg",
                "raquelfarias@example.com", "83922222222", "example.com");
        ResponseUserDTO responseUserDTO = new ResponseUserDTO(1L, "Raquel Farias", "profile.jpg",
                "raquelfarias@example.com", "83922222222", "example.com");

        when(userService.create(persistUserDTO)).thenReturn(responseUserDTO);

        ResponseEntity<ResponseUserDTO> responseEntity = userController.create(persistUserDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responseUserDTO, responseEntity.getBody());
    }
    
    @Test
    public void registerUserServiceOwner() { 
        PersistUserDTO persistUserDTO = new PersistUserDTO(UserType.SERVICE_OWNER, "Rafael Feitosa", "profile.jpg",
                "rafeitosa@example.com", "83933333333", "example.com");
        ResponseUserDTO responseUserDTO = new ResponseUserDTO(1L, "Rafael Feitosa", "profile.jpg",
                "rafeitosa@example.com", "83933333333", "example.com");

        when(userService.create(persistUserDTO)).thenReturn(responseUserDTO);

        ResponseEntity<ResponseUserDTO> responseEntity = userController.create(persistUserDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responseUserDTO, responseEntity.getBody());
    }
    
}
