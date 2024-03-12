package com.rentspace.DTO.persist;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PersistUserDTO {
    private String name;
    private String profilePhoto;
    private String email;
    private String telephone;
    private String webSite;
}
