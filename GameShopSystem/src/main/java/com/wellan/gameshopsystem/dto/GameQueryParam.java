package com.wellan.gameshopsystem.dto;

import com.wellan.gameshopsystem.model.PlateCategory;
import lombok.Data;

@Data
public class GameQueryParam {
    private String search;
    private String tag;
    private PlateCategory plateName;
    private String orderBy;
    private String sort;
    private Integer limit;
    private Integer offset;
}
