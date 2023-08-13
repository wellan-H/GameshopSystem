package com.wellan.gameshopsystem.dao;

import com.wellan.gameshopsystem.dto.CompanyQueryParam;
import com.wellan.gameshopsystem.dto.CompanyRequest;
import com.wellan.gameshopsystem.model.Company;
import com.wellan.gameshopsystem.model.CompanyCategory;

import java.util.List;

public interface CompanyDao {
    Company getCompanyById(Integer companyId);
    Integer addCompany(CompanyRequest companyRequest);

    void updateCompanyById(Integer companyId, CompanyRequest companyRequest);

    void deleteCompanyById(Integer companyId);

    List<Company> getCompanys(CompanyQueryParam companyQueryParam);

    Integer getCompanyCount(CompanyQueryParam companyQueryParam);
}
