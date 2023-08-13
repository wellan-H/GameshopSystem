package com.wellan.gameshopsystem.rowmapper;

import com.wellan.gameshopsystem.model.Plate;
import com.wellan.gameshopsystem.model.PlateCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlateRowMapper implements RowMapper<Plate> {
    @Override
    public Plate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Plate plate = new Plate();
        plate.setPlateId(rs.getInt("plate_id"));
        plate.setPlateName(PlateCategory.valueOf(rs.getString("plate_name")));
        return plate;
    }
}
