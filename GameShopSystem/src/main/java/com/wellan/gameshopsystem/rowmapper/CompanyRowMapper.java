package com.wellan.gameshopsystem.rowmapper;

import com.wellan.gameshopsystem.model.Company;
import com.wellan.gameshopsystem.model.CompanyCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyRowMapper implements RowMapper<Company> {
    @Override
    public Company mapRow(ResultSet resultSet, int i) throws SQLException {
        Company company = new Company();
        company.setCompanyId(resultSet.getInt("company_id"));
        company.setCompanyName(resultSet.getString("company_name"));
        company.setOfficeWeb(resultSet.getString("office_web"));
        company.setCompanyCategory(CompanyCategory.valueOf(resultSet.getString("company_category")));
        company.setImagePath(resultSet.getString("image_path"));
        company.setCompanyIntroduction(resultSet.getString("company_introduction"));
        company.setCreatedDate(resultSet.getDate("created_date"));
        company.setLastModifiedDate(resultSet.getDate("last_modified_date"));
        return company;
    }
}
