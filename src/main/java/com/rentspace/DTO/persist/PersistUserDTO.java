package com.rentspace.DTO.persist;

import com.rentspace.model.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PersistUserDTO {
    private UserType userType;
    private String name;
    private String profilePhoto;
    private String email;
    private String telephone;
    private String webSite;
}
