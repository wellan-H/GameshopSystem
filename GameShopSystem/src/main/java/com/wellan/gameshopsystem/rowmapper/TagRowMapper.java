package com.wellan.gameshopsystem.rowmapper;

import com.wellan.gameshopsystem.model.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRowMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setTagId(rs.getInt("tag_id"));
        tag.setTagName(rs.getString("tag_name"));
        return tag;
    }
}
