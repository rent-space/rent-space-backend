package com.rentspace.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String profilePhoto;
    private String email;
    private String telephone;
    private String webSite;
}
