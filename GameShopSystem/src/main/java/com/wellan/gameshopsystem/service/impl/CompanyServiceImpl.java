package com.wellan.gameshopsystem.service.impl;

import com.wellan.gameshopsystem.service.CompanyService;
import com.wellan.gameshopsystem.dao.CompanyDao;
import com.wellan.gameshopsystem.dto.CompanyQueryParam;
import com.wellan.gameshopsystem.dto.CompanyRequest;
import com.wellan.gameshopsystem.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyDao companyDao;

    @Override
    public Integer getCompanyCount(CompanyQueryParam companyQueryParam) {
        return companyDao.getCompanyCount(companyQueryParam);
    }

    @Override
    public List<Company> getCompanys(CompanyQueryParam companyQueryParam) {
        return companyDao.getCompanys(companyQueryParam);
    }

    @Override
    public Company getCompanyById(Integer companyId) {
        return companyDao.getCompanyById(companyId);
    }

    @Override
    public Integer addCompany(CompanyRequest companyRequest) {
        return  companyDao.addCompany(companyRequest);
    }

    @Override
    public void updateCompany(Integer companyId, CompanyRequest companyRequest) {
        companyDao.updateCompanyById(companyId,companyRequest);
    }

    @Override
    public void deleteCompany(Integer companyId) {
        companyDao.deleteCompanyById(companyId);
    }
}
