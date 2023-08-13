package com.wellan.gameshopsystem.service;

import com.wellan.gameshopsystem.dto.GameAddRequest;
import com.wellan.gameshopsystem.dto.GameQueryParam;
import com.wellan.gameshopsystem.model.Game;
import com.wellan.gameshopsystem.model.GameDetail;

import java.util.List;

public interface GameService {
    Integer addGame(GameAddRequest gameAddRequest);

    GameDetail getGameDetailById(Integer id);

    void deleteGame(Integer gameId);

    void updateGame(Integer gameId, GameAddRequest gameAddRequest);

    Boolean isUnique(String gameName, Integer gameId);

    List<GameDetail> getGameDetails(GameQueryParam gameQueryParam);

    Integer getGameCount(GameQueryParam gameQueryParam);
}
