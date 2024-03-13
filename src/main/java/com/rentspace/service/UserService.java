package com.rentspace.service;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.model.user.AppUser;
import com.rentspace.repository.UserRepository;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService extends ModelMapperFuncs {

    private UserRepository<AppUser> userRepository;

    private void save(AppUser appUser){
        userRepository.save(appUser);
    }

    public ResponseUserDTO registerUser(PersistUserDTO persistUserDTO) {
        AppUser appUser = map(persistUserDTO, persistUserDTO.getUserType().toClass());
        save(appUser);
        return map(appUser, ResponseUserDTO.class);
    }
}
