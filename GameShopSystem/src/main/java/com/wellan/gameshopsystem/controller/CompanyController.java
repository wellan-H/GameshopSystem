package com.wellan.gameshopsystem.controller;

import com.wellan.gameshopsystem.service.CompanyService;
import com.wellan.gameshopsystem.dto.CompanyQueryParam;
import com.wellan.gameshopsystem.dto.CompanyRequest;
import com.wellan.gameshopsystem.model.Company;
import com.wellan.gameshopsystem.model.CompanyCategory;
import com.wellan.gameshopsystem.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @GetMapping("/company/getAll")
    public ResponseEntity<Page<Company>> getCompanys(@RequestParam(required = false) CompanyCategory category,
                                                     @RequestParam(required = false) String search,
                                                     @RequestParam(defaultValue = "company_name")String orderBy,
                                                     @RequestParam(defaultValue = "ASC") String sort,
                                                     @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
                                                     @RequestParam(defaultValue = "0") @Min(0) Integer offset){
        CompanyQueryParam companyQueryParam = new CompanyQueryParam();
        companyQueryParam.setCategory(category);
        companyQueryParam.setSearch(search);
        companyQueryParam.setOrderBy(orderBy);
        companyQueryParam.setSort(sort);
        companyQueryParam.setLimit(limit);
        companyQueryParam.setOffset(offset);
        List<Company> companyList = companyService.getCompanys(companyQueryParam);
        Page<Company> companyPage = new Page<>();
        companyPage.setLimit(limit);
        companyPage.setOffset(offset);
        companyPage.setTotal(companyService.getCompanyCount(companyQueryParam));
        companyPage.setList(companyList);
        return ResponseEntity.status(HttpStatus.OK).body(companyPage);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Integer companyId){
        Company company = companyService.getCompanyById(companyId);
        if(company != null){
            return ResponseEntity.status(HttpStatus.OK).body(company);
        }else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
    @PostMapping("/company/add")
    public ResponseEntity<Company> addCompany(@RequestBody @Valid CompanyRequest companyRequest){
        Integer id = companyService.addCompany(companyRequest);
        Company company = companyService.getCompanyById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);

    }
    @PutMapping("/company/{companyId}")
    public ResponseEntity<Company> updateCompany(
            @PathVariable Integer companyId,
            @RequestBody @Valid CompanyRequest companyRequest){
        Company company = companyService.getCompanyById(companyId);
        if(company == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        companyService.updateCompany(companyId,companyRequest);
        Company updateCompany = companyService.getCompanyById(companyId);
        return  ResponseEntity.status(HttpStatus.OK).body(updateCompany);
    }
    @DeleteMapping("/company/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable Integer companyId){
        companyService.deleteCompany(companyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
