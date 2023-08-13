package com.wellan.gameshopsystem.dto;

import com.wellan.gameshopsystem.model.CompanyCategory;
import lombok.Data;

@Data
public class CompanyQueryParam {
    private CompanyCategory category;
    private String search;
    private String orderBy;
    private String sort;
    private Integer limit;
    private Integer offset;
}
