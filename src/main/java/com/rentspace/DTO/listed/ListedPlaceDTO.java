package com.rentspace.DTO.listed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListedPlaceDTO {

    private Long id;
    private String title;
    private Integer maximumCapacity;
    private Double pricePerHour;
    private String description;

    public ListedPlaceDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
