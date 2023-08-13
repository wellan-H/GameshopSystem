package com.wellan.gameshopsystem.dto;

import com.wellan.gameshopsystem.model.PlateCategory;
import lombok.Data;

@Data
public class VersionRequest {

    private PlateCategory plateName;
    private Integer price;
    private Integer quantity;
}
