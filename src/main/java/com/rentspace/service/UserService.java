package com.rentspace.service;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.AppUser;
import com.rentspace.repository.UserRepository;
import com.rentspace.util.ModelMapperFuncs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.rentspace.exception.ExceptionMessages.*;
import static com.rentspace.util.UserTypeConverter.toClass;

@Service
@AllArgsConstructor
public class UserService extends ModelMapperFuncs {

    private UserRepository<AppUser> userRepository;

    public void save(AppUser model){
        userRepository.save(model);
    }

    public AppUser get(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(USER_EMAIL_NOT_FOUND + email));
    }

    public AppUser get(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND + id));
    }

    public ResponseUserDTO create(PersistUserDTO persistDTO) {
        if (this.userRepository.findByEmail(persistDTO.getEmail()).isPresent()) {
            throw new ApiRequestException(EMAIL_ALREADY_EXISTS);
        }

        AppUser appUser = map(persistDTO, toClass(persistDTO.getUserType()));
        save(appUser);
        return buildListServiceDTO(appUser);
    }

    public ResponseUserDTO getByEmail(String email) {
        return buildListServiceDTO(get(email));
    }

    public ResponseUserDTO update(Long id, PersistUserDTO persistUserDTO) {

        AppUser user = get(id);
        user = buildModel(user, persistUserDTO);

        userRepository.save(user);
        return buildListServiceDTO(user);
    }

    public ResponseUserDTO delete(Long id) {
        AppUser user = get(id);
        userRepository.delete(user);
        return buildListServiceDTO(user);
    }
}
