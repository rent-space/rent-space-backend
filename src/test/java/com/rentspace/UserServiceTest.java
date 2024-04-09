package com.rentspace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.model.user.AppUser;
import com.rentspace.model.user.UserType;
import com.rentspace.repository.UserRepository;
import com.rentspace.service.UserService;
import com.rentspace.util.ModelMapperFuncs;

public class UserServiceTest {

	@Mock
    private UserRepository<AppUser> userRepository;

	@Mock
    private UserService userService;

	@Mock
    private ModelMapperFuncs modelMapperFuncs;

	@Mock
    private UserService serviceUser; 

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    	userService = new UserService(userRepository);
    }

    @Test
    public void saveUser() { 
        AppUser userToSave = new AppUser("Ronaldo Fenomeno", "RonaldoFe@example.com", "", "", "");
        when(userRepository.save(any(AppUser.class))).thenReturn(userToSave);

        userService.save(userToSave);

        verify(userRepository, times(1)).save(userToSave);
    }

    @Test
    public void createUser() {
        PersistUserDTO persistUserDTO = new PersistUserDTO();
        persistUserDTO.setEmail("RonaldoFe@example.com");
        persistUserDTO.setName("Ronaldo Fenomeno");
        persistUserDTO.setTelephone("1234567890");
        persistUserDTO.setUserType(UserType.EVENT_OWNER);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty()); 

        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setId(1L);
        responseUserDTO.setEmail("RonaldoFe@example.com");
        responseUserDTO.setName("Ronaldo Fenomeno");
        responseUserDTO.setTelephone("1234567890");

        when(modelMapperFuncs.map(any(), any())).thenReturn(responseUserDTO);

        userService.create(persistUserDTO);

        verify(userRepository, times(1)).findByEmail("RonaldoFe@example.com");
        verify(userRepository, times(1)).save(any());
    }
    
    @Test
    public void findUserByEmail() {
        String email = "test@example.com";
        AppUser appUser = new AppUser();
        appUser.setEmail(email);
 
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(appUser));

        UserService serviceUser = new UserService(userRepository);

        AppUser result = serviceUser.get(email);

        assertEquals(appUser, result); 
    }
    
    @Test
    public void getUserByEmail() {
        String email = "test@example.com";
        AppUser mockUser = new AppUser();
        mockUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        UserService serviceUser = new UserService(userRepository);

        ResponseUserDTO result = serviceUser.getByEmail(email);

        assertEquals(mockUser.getId(), result.getId());
        assertEquals(mockUser.getName(), result.getName());
        assertEquals(mockUser.getEmail(), result.getEmail());
    }

}
