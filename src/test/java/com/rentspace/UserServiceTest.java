package com.rentspace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.rentspace.model.user.AppUser;
import com.rentspace.repository.UserRepository;
import com.rentspace.service.UserService;
import static org.mockito.Mockito.*;

public class UserServiceTest {

	@Mock
    private UserRepository<AppUser> userRepository;
	
	@Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    	userService = new UserService(userRepository);
    }

    @Test
    public void saveUser() { 
        AppUser userToSave = new AppUser("John Doe", "johndoe@example.com", "", "", "");
        when(userRepository.save(any(AppUser.class))).thenReturn(userToSave);

        userService.save(userToSave);

        verify(userRepository, times(1)).save(userToSave);
    }

}
