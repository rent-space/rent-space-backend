package com.rentspace.service;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.AppUser;
import com.rentspace.repository.UserRepository;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.rentspace.exception.ExceptionMessages.EMAIL_ALREADY_EXISTS;

@Service
@AllArgsConstructor
public class UserService extends ModelMapperFuncs {

    private UserRepository<AppUser> userRepository;

    private void save(AppUser appUser){
        userRepository.save(appUser);
    }

    public ResponseUserDTO create(PersistUserDTO persistUserDTO) {
        if (this.userRepository.findByEmail(persistUserDTO.getEmail()).isPresent()) {
            throw new ApiRequestException(EMAIL_ALREADY_EXISTS);
        }

        AppUser appUser = map(persistUserDTO, persistUserDTO.getUserType().toClass());
        save(appUser);
        return map(appUser, ResponseUserDTO.class);
    }
}
