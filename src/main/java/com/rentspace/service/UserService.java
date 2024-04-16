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

        AppUser appUser = map(persistDTO, persistDTO.getUserType().toClass());
        save(appUser);
        return map(appUser, ResponseUserDTO.class);
    }

    public ResponseUserDTO getByEmail(String email) {
        return map(get(email), ResponseUserDTO.class);
    }

    public ResponseUserDTO update(Long id, PersistUserDTO persistUserDTO) {

        AppUser user = get(id);
        user = buildModel(user, persistUserDTO);

        userRepository.save(user);
        return map(user, ResponseUserDTO.class);
    }
}
