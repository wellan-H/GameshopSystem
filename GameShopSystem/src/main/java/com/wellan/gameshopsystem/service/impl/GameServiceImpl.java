package com.wellan.gameshopsystem.service.impl;

import com.wellan.gameshopsystem.dao.GameDao;
import com.wellan.gameshopsystem.dto.GameAddRequest;
import com.wellan.gameshopsystem.dto.GameQueryParam;
import com.wellan.gameshopsystem.model.Game;
import com.wellan.gameshopsystem.model.GameDetail;
import com.wellan.gameshopsystem.model.Version;
import com.wellan.gameshopsystem.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameServiceImpl implements GameService {

    private final static Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

    @Autowired
    private GameDao gameDao;
    @Transactional
    @Override
    public Integer addGame(GameAddRequest gameAddRequest) {
        Game game = gameDao.getGameByName(gameAddRequest.getGameName());
        if(game!=null){
            log.warn("該遊戲：{} 已存在",gameAddRequest.getGameName());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Integer gameId = gameDao.addGame(gameAddRequest);
        //新增property的內容
        gameDao.setProperties(gameId,gameAddRequest.getProperties());
        //新增version的內容
        gameDao.setVersions(gameId,gameAddRequest.getVersions());
        return gameId;
    }

    @Override
    public GameDetail getGameDetailById(Integer id) {
        Game game = gameDao.getGameById(id);
        List<Version> versions = gameDao.getVersionsByGameId(id);
        List<String> tags = gameDao.getPropertiesByGameId(id);
        GameDetail gameDetail = new GameDetail(game,versions,tags);
        return gameDetail;
    }

    @Override
    @Transactional
    public void deleteGame(Integer gameId) {
        gameDao.deleteGameByGameId(gameId);
        gameDao.deleteVersionsByGameId(gameId);
        gameDao.deletePropertiesByGameId(gameId);
    }

    @Override
    @Transactional
    public void updateGame(Integer gameId, GameAddRequest gameAddRequest) {
        gameDao.updateGameById(gameId,gameAddRequest);
        //Version/Property資料表由於是多對多的關係，因此將其對應的gameId整個刪除，再重新添加
        gameDao.deleteVersionsByGameId(gameId);
        gameDao.deletePropertiesByGameId(gameId);
        //重新添加
        gameDao.setVersions(gameId,gameAddRequest.getVersions());
        gameDao.setProperties(gameId,gameAddRequest.getProperties());
    }


    @Override
    public Boolean isUnique(String gameName, Integer gameId) {
        Game game = gameDao.getGameByNameUnique(gameName, gameId);
        if (game==null){
            //代表沒有跟其他遊戲重複名稱
            return Boolean.TRUE;
        }else return Boolean.FALSE;
    }

    @Override
    @Transactional
    public List<GameDetail> getGameDetails(GameQueryParam gameQueryParam) {
        List<Game> gameList = gameDao.getGames(gameQueryParam);
        List<GameDetail> gameDetails = new ArrayList<>();
        for (Game game:gameList) {
            //注意，此處每經過一個循環就必須新創建一個GameDetail物件，以免call by ref的影響，回傳的結果都變成同一筆紀錄
            GameDetail gameDetail = new GameDetail();
            List<Version> versions = gameDao.getVersionsByGameId(game.getGameId());
            List<String> properties = gameDao.getPropertiesByGameId(game.getGameId());
            gameDetail.setGameId(game.getGameId());
            gameDetail.setGameName(game.getGameName());
            gameDetail.setDevCompany(game.getDevCompany());
            gameDetail.setPubCompany(game.getPubCompany());
            gameDetail.setIntro(game.getIntro());
            gameDetail.setImagePath(game.getImagePath());
            gameDetail.setVersions(versions);
            gameDetail.setProperties(properties);
            gameDetail.setCreatedDate(game.getCreatedDate());
            gameDetail.setLastModifiedDate(game.getLastModifiedDate());
            gameDetails.add(gameDetail);
        }
        return gameDetails;
    }

    @Override
    public Integer getGameCount(GameQueryParam gameQueryParam) {
        return gameDao.getGameCount(gameQueryParam);
    }
}
