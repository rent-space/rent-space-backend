package com.rentspace.model.user;

import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class User {

    private String name;
    private String profilePhoto;
    private String email;
    private String telephone;
    private String webSite;

    @Id
    private Long id;

    public User(String name, String profilePhoto, String email, String telephone, String webSite) {
        this.name = name;
        this.profilePhoto = profilePhoto;
        this.email = email;
        this.telephone = telephone;
        this.webSite = webSite;
    }
}
