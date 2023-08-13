package com.wellan.gameshopsystem.model;


import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
public class Company {
    @NotNull
    private Integer companyId;
    @NotBlank
    private String companyName;
    private String officeWeb;
    private CompanyCategory companyCategory;
    private String imagePath;
    private String companyIntroduction;
    private Date createdDate;
    private Date lastModifiedDate;



}
