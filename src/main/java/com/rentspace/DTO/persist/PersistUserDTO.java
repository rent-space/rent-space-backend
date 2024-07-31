package com.rentspace.DTO.persist;

import com.rentspace.model.user.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class PersistUserDTO {

    @NotNull
    private UserType userType;

    @NotNull
    private String name;

    private String profilePhoto;

    @NotNull
    private String email;

    @NotNull
    private String telephone;

    private String webSite;
}
