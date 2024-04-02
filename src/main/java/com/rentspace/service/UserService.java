package com.rentspace.service;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.AppUser;
import com.rentspace.repository.UserRepository;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.rentspace.exception.ExceptionMessages.EMAIL_ALREADY_EXISTS;
import static com.rentspace.exception.ExceptionMessages.EMAIL_NOT_FOUND;

@Service
@AllArgsConstructor
public class UserService extends ModelMapperFuncs {

    private UserRepository<AppUser> userRepository;

    public void save(AppUser model){
        userRepository.save(model);
    }

    public ResponseUserDTO create(PersistUserDTO persistDTO) {
        if (this.userRepository.findByEmail(persistDTO.getEmail()).isPresent()) {
            throw new ApiRequestException(EMAIL_ALREADY_EXISTS);
        }

        AppUser appUser = map(persistDTO, persistDTO.getUserType().toClass());
        save(appUser);
        return map(appUser, ResponseUserDTO.class);
    }

    public ResponseUserDTO getByEmail(String userEmail) {
        AppUser appUser = this.userRepository.findByEmail(userEmail).orElseThrow();

        return map(appUser, ResponseUserDTO.class);


    }
}
