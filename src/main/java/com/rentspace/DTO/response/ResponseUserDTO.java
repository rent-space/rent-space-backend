package com.rentspace.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseUserDTO {
    private String name;
    private String profilePhoto;
    private String email;
    private String telephone;
    private String webSite;
}
