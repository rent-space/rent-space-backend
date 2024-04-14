package com.rentspace.DTO.listed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListedServiceDTO {

    private Long id;
    private String title;
    private String firstMedia;

}
