package com.rentspace.DTO.response;

import com.rentspace.model.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseUserDTO {

    private Long id;
    private String name;
    private String profilePhoto;
    private String email;
    private String telephone;
    private String webSite;
    private UserType userType;
}
