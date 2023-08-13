package com.wellan.gameshopsystem.rowmapper;

import com.wellan.gameshopsystem.model.PlateCategory;
import com.wellan.gameshopsystem.model.Version;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VersionRowMapper implements RowMapper<Version> {
    @Override
    public Version mapRow(ResultSet rs, int rowNum) throws SQLException {
        Version version = new Version();
        version.setPlateName(PlateCategory.valueOf(rs.getString("plate_name")));
        version.setPrice(rs.getInt("price"));
        version.setQuantity(rs.getInt("quantity"));
        return version;
    }
}
