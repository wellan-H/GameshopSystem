package com.wellan.gameshopsystem.dto;

import com.wellan.gameshopsystem.model.CompanyCategory;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CompanyRequest {
    @NotBlank
        private String companyName;
        private String officeWeb;
        private CompanyCategory companyCategory;
        private String imagePath;
        private String companyIntroduction;

}
