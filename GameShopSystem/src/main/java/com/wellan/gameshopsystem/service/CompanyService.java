package com.wellan.gameshopsystem.service;

import com.wellan.gameshopsystem.dto.CompanyQueryParam;
import com.wellan.gameshopsystem.dto.CompanyRequest;
import com.wellan.gameshopsystem.model.Company;

import java.util.List;

public interface CompanyService {
    Company getCompanyById(Integer companyId);

    Integer addCompany(CompanyRequest companyRequest);

    void updateCompany(Integer companyId, CompanyRequest companyRequest);

    void deleteCompany(Integer companyId);


    List<Company> getCompanys(CompanyQueryParam companyQueryParam);

    Integer getCompanyCount(CompanyQueryParam companyQueryParam);
}
