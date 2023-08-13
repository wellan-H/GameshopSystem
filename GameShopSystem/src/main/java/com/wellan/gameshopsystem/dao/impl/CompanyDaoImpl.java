package com.wellan.gameshopsystem.dao.impl;

import com.wellan.gameshopsystem.dao.CompanyDao;
import com.wellan.gameshopsystem.dto.CompanyQueryParam;
import com.wellan.gameshopsystem.dto.CompanyRequest;
import com.wellan.gameshopsystem.model.Company;
import com.wellan.gameshopsystem.model.CompanyCategory;
import com.wellan.gameshopsystem.rowmapper.CompanyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CompanyDaoImpl implements CompanyDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Company> getCompanys(CompanyQueryParam companyQueryParam) {
        String sql ="SELECT company_id,company_name,office_web,company_category," +
                "image_path,company_introduction,created_date,last_modified_date " +
                "FROM company WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        sql = addFilteringSql(sql,map,companyQueryParam);
        sql += " ORDER BY "+companyQueryParam.getOrderBy()+" "+companyQueryParam.getSort();
        sql += " LIMIT :limit OFFSET :offset";
        map.put("limit",companyQueryParam.getLimit());
        map.put("offset",companyQueryParam.getOffset());
        List<Company> companyList = namedParameterJdbcTemplate.query(sql, map, new CompanyRowMapper());
        return companyList;
    }

    @Override
    public Integer getCompanyCount(CompanyQueryParam companyQueryParam) {
        String sql ="SELECT count(*) FROM company WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        sql = addFilteringSql(sql,map,companyQueryParam);
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return count;
    }

    @Override
    public Company getCompanyById(Integer companyId) {
        String sql = "SELECT company_id,company_name,office_web,company_category," +
                "image_path,company_introduction,created_date,last_modified_date " +
                "FROM company WHERE company_id = :companyId";
        Map<String,Object> map = new HashMap<>();
        map.put("companyId",companyId);
        List<Company> companyList = namedParameterJdbcTemplate.query(sql, map, new CompanyRowMapper());

        if(companyList.size()>0){
            return companyList.get(0);
        }else return  null;
    }

    @Override
    public Integer addCompany(CompanyRequest companyRequest) {
        String sql ="INSERT INTO company(company_name,office_web,company_category,image_path,company_introduction,created_date,last_modified_date)" +
                "VALUES (:companyName,:officeWeb,:companyCategory,:imagePath,:companyIntroduction,:createDate,:lastModifiedDate)";
        Map<String,Object> map  = new HashMap<>();
        map.put("companyName",companyRequest.getCompanyName());
        map.put("officeWeb",companyRequest.getOfficeWeb());
        map.put("companyCategory",companyRequest.getCompanyCategory().toString());
        map.put("imagePath",companyRequest.getImagePath());
        map.put("companyIntroduction",companyRequest.getCompanyIntroduction());
        Date now = new Date();
        map.put("createDate",now);
        map.put("lastModifiedDate",now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        Integer id = keyHolder.getKey().intValue();
        return id;
    }

    @Override
    public void updateCompanyById(Integer companyId, CompanyRequest companyRequest) {
        String sql = "UPDATE company SET company_name=:companyName,office_web=:officeWeb," +
                "company_category=:companyCategory,image_path=:imagePath,company_introduction=:companyIntroduction," +
                "last_modified_date=:lastModifiedDate WHERE company_id=:companyId";
        Map<String,Object> map  = new HashMap<>();
        map.put("companyName",companyRequest.getCompanyName());
        map.put("officeWeb",companyRequest.getOfficeWeb());
        map.put("companyCategory",companyRequest.getCompanyCategory().toString());
        map.put("imagePath",companyRequest.getImagePath());
        map.put("companyIntroduction",companyRequest.getCompanyIntroduction());
        map.put("lastModifiedDate",new Date());
        map.put("companyId",companyId);
        namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public void deleteCompanyById(Integer companyId) {
        String sql = "DELETE FROM company WHERE company_id = :companyId";
        Map<String ,Object> map = new HashMap<>();
        map.put("companyId",companyId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    private String addFilteringSql(String sql,Map map,CompanyQueryParam companyQueryParam){
        if(companyQueryParam.getCategory()!=null){
            sql += " AND company_category = :companyCategory OR company_category = 'BOTH'";
            map.put("companyCategory",companyQueryParam.getCategory().name());
        }
        if(companyQueryParam.getSearch()!=null){
            sql += " AND company_name LIKE :search";
            map.put("search","%"+companyQueryParam.getSearch()+"%");
        }
        return sql;
    }
}
