package com.rentspace.model.user;

import com.rentspace.model.GenericModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AppUser extends GenericModel {

    private String name;
    private String profilePhoto;
    private String email;
    private String telephone;
    private String webSite;

}
