package com.wellan.gameshopsystem.dao;

import com.wellan.gameshopsystem.dto.GameAddRequest;
import com.wellan.gameshopsystem.dto.GameQueryParam;
import com.wellan.gameshopsystem.dto.VersionRequest;
import com.wellan.gameshopsystem.model.Game;
import com.wellan.gameshopsystem.model.Version;

import java.util.List;

public interface GameDao {
    Game getGameByName(String gameName);
    Integer addGame(GameAddRequest gameAddRequest);

    Game getGameById(Integer gameId);

    List<Version> getVersionsByGameId(Integer gameId);

    List<String> getPropertiesByGameId(Integer gameId);

    void setProperties(Integer gameId, List<String> property);

    void setVersions(Integer gameId, List<VersionRequest> versions);

    void deleteGameByGameId(Integer gameId);

    void deleteVersionsByGameId(Integer gameId);

    void deletePropertiesByGameId(Integer gameId);

    void updateGameById(Integer gameId, GameAddRequest gameAddRequest);

    Game getGameByNameUnique(String gameName, Integer gameId);

    Integer getGameCount(GameQueryParam gameQueryParam);

    List<Game> getGames(GameQueryParam gameQueryParam);

    Version getVersionByGameIdAndPlateId(Integer gameId, Integer plateId);

    String getPlateNameByPlateId(Integer plateId);

    void updateStock(Integer gameId, Integer plateId, Integer remaining);
}
