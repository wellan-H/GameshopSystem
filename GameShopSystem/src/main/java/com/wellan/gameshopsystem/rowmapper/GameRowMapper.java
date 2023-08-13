package com.wellan.gameshopsystem.rowmapper;

import com.wellan.gameshopsystem.model.Game;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameRowMapper implements RowMapper<Game> {
    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
        Game game = new Game();
        game.setGameId(rs.getInt("game_id"));
        game.setGameName(rs.getString("game_name"));
        game.setDevCompany(rs.getString("dev_company"));
        game.setPubCompany(rs.getString("pub_company"));
        game.setIntro(rs.getString("intro"));
        game.setImagePath(rs.getString("image_path"));
        game.setCreatedDate(rs.getDate("created_date"));
        game.setLastModifiedDate(rs.getDate("last_modified_date"));
        return game;
    }
}
