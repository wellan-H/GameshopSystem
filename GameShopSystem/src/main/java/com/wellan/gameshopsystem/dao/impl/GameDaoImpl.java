package com.wellan.gameshopsystem.dao.impl;

import com.wellan.gameshopsystem.dao.GameDao;
import com.wellan.gameshopsystem.dto.GameAddRequest;
import com.wellan.gameshopsystem.dto.GameQueryParam;
import com.wellan.gameshopsystem.dto.VersionRequest;
import com.wellan.gameshopsystem.model.Game;
import com.wellan.gameshopsystem.model.Plate;
import com.wellan.gameshopsystem.model.Tag;
import com.wellan.gameshopsystem.model.Version;
import com.wellan.gameshopsystem.rowmapper.GameRowMapper;
import com.wellan.gameshopsystem.rowmapper.PlateRowMapper;
import com.wellan.gameshopsystem.rowmapper.TagRowMapper;
import com.wellan.gameshopsystem.rowmapper.VersionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class GameDaoImpl implements GameDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Game getGameByNameUnique(String gameName, Integer gameId) {
        String sql = "SELECT game_id,game_name,dev_company,pub_company,intro,image_path,created_date,last_modified_date " +
                " FROM `game` WHERE game_name=:gameName AND game_id <> :gameId";
        Map<String ,Object> map = new HashMap<>();
        map.put("gameName",gameName);
        map.put("gameId",gameId);
        List<Game> gameList = namedParameterJdbcTemplate.query(sql, map, new GameRowMapper());
        if (gameList.size()>0){
            return gameList.get(0);
        }else return null;
    }

    @Override
    public Game getGameByName(String gameName) {
        String sql = "SELECT game_id,game_name,dev_company,pub_company,intro,image_path,created_date,last_modified_date " +
                " FROM `game` WHERE game_name=:gameName";
        Map<String ,Object> map = new HashMap<>();
        map.put("gameName",gameName);
        List<Game> gameList = namedParameterJdbcTemplate.query(sql, map, new GameRowMapper());
        if (gameList.size()>0){
            return gameList.get(0);
        }else return null;
    }

    @Override
    public void setProperties(Integer gameId, List<String> property) {
        //先驗證Tag是否存在於表中，若沒有則加入到tag表中，並回傳tag_id的list
        List<Integer> tagIdList = setTagReturnIdList(property);
        //將回傳的tag_id的list統一插入property表中（多對多）
        String propertyInsertSql = "INSERT INTO property(game_id,tag_id) VALUES (:gameId,:tagId)";
        MapSqlParameterSource[] mapSqlParameterSourceForProperty = new MapSqlParameterSource[tagIdList.size()];
        for (int i = 0; i < tagIdList.size(); i++) {
            Integer tagId = tagIdList.get(i).intValue();
            mapSqlParameterSourceForProperty[i] = new MapSqlParameterSource();
            mapSqlParameterSourceForProperty[i].addValue("gameId",gameId);
            mapSqlParameterSourceForProperty[i].addValue("tagId",tagId);
        }
        namedParameterJdbcTemplate.batchUpdate(propertyInsertSql,mapSqlParameterSourceForProperty);
    }

    @Override
    public void setVersions(Integer gameId, List<VersionRequest> versions) {
        //添加Version資訊
        String versionSql = "INSERT INTO version(game_id,plate_id,price,quantity) " +
                "VALUES(:gameId,:plateId,:price,:quantity)";
        MapSqlParameterSource[] mapSqlParameterSource = new MapSqlParameterSource[versions.size()];
        for (int i = 0; i < versions.size(); i++) {
            VersionRequest versionRequest = versions.get(i);
            mapSqlParameterSource[i] = new MapSqlParameterSource();
            mapSqlParameterSource[i].addValue("gameId",gameId);
            //需要獲取plate_id
            mapSqlParameterSource[i].addValue("plateId",getPlateIdByName(versionRequest.getPlateName().name()));
            mapSqlParameterSource[i].addValue("price",versionRequest.getPrice());
            mapSqlParameterSource[i].addValue("quantity",versionRequest.getQuantity());
        }
        namedParameterJdbcTemplate.batchUpdate(versionSql,mapSqlParameterSource);
    }

    @Override
    public List<Version> getVersionsByGameId(Integer gameId) {
        String sql = "SELECT plate_name,price,quantity FROM version AS V INNER JOIN plate AS P " +
                "ON V.plate_id = P.plate_id WHERE V.game_id=:gameId";
        Map<String,Object> map  = new HashMap<>();
        map.put("gameId",gameId);
        List<Version> versions = namedParameterJdbcTemplate.query(sql, map, new VersionRowMapper());
        if(versions.size()>0){
            return versions;
        }else return null;
    }

    @Override
    public List<String> getPropertiesByGameId(Integer gameId) {
        String sql  = "SELECT T.tag_id,T.tag_name FROM tag AS T INNER JOIN property AS P ON T.tag_id=P.tag_id WHERE P.game_id=:gameId";
        Map<String,Object> map = new HashMap<>();
        map.put("gameId",gameId);
        List<Tag> tagList = namedParameterJdbcTemplate.query(sql, map, new TagRowMapper());
        List<String> tags = new ArrayList<>();
        for (Tag tag:tagList) {
            tags.add(tag.getTagName());
        }
        if(tags.size()>0){
            return tags;
        }else return null;
    }

    @Override
    public Game getGameById(Integer gameId) {
        String sql  = "SELECT game_id,game_name,dev_company,pub_company,intro,image_path,created_date,last_modified_date FROM " +
                "game WHERE game_id=:gameId";
        Map<String,Object> map = new HashMap<>();
        map.put("gameId",gameId);
        List<Game> games = namedParameterJdbcTemplate.query(sql, map, new GameRowMapper());
        if(games.size()>0){
            return games.get(0);
        }else return null;
    }

    @Override
    public Integer addGame(GameAddRequest gameAddRequest) {
        String sql = "INSERT INTO game(game_name,dev_company,pub_company,intro,image_path,created_date,last_modified_date)" +
                "VALUE(:gameName,:devCompany,:pubCompany,:intro,:imagePath,:createdDate,:lastModifiedDate)";
        Map<String,Object> map = new HashMap<>();
        map.put("gameName",gameAddRequest.getGameName());
        map.put("devCompany",gameAddRequest.getDevCompany());
        map.put("pubCompany",gameAddRequest.getPubCompany());
        map.put("intro",gameAddRequest.getIntro());
        map.put("imagePath",gameAddRequest.getImagePath());
        Date now  = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        Integer gameId = keyHolder.getKey().intValue();
        return gameId;
    }

    private List<Integer> setTagReturnIdList(List<String> tagList) {
        List<Integer> tagIdList = new ArrayList<>();
        for (String tag:tagList) {
            String tagSql = "SELECT tag_id,tag_name FROM tag WHERE tag_name=:tagName";
            Map<String,Object> map = new HashMap<>();
            map.put("tagName",tag);
            List<Tag> tagQueryList = namedParameterJdbcTemplate.query(tagSql,map,new TagRowMapper());
            if (tagQueryList.size()>0){
                //代表此tag已被存入資料表中，不需要再添加一次，回傳tag_id後跳入下一個迴圈
                tagIdList.add(tagQueryList.get(0).getTagId());
            }else {
                //代表此tag尚未存於資料表中，需添加，並回傳其tag_id
                String tagInsertSql = "INSERT INTO tag(tag_name) VALUE (:tagName)";
                Map<String,Object> tagMap = new HashMap<>();
                tagMap.put("tagName",tag);
                KeyHolder keyHolder = new GeneratedKeyHolder();
                namedParameterJdbcTemplate.update(tagInsertSql, new MapSqlParameterSource(tagMap),keyHolder);
                Integer tagId = keyHolder.getKey().intValue();
                tagIdList.add(tagId);
            }
        }
        return tagIdList;

    }

    private Integer getPlateIdByName(String plateName) {
        //方法1
//        String sql = "SELECT plate_id,plate_name FROM plate WHERE plate_name=:plateName";
//        Map<String,Object> map = new HashMap<>();
//        map.put("plateName",plateName);
//        List<Plate> plateList = namedParameterJdbcTemplate.query(sql, map, new PlateRowMapper());
//        if(plateList.size()>0){
//            return plateList.get(0).getPlateId();
//        } else return null;
        //方法2
        //由於plate為一個枚舉類型，先將所有的plate_id與plate_name取出，放入Map，之後直接透過此Map進行比對即可
        String sql = "SELECT plate_id,plate_name FROM plate WHERE 1=1";
        List<Plate> plateList = namedParameterJdbcTemplate.query(sql, new HashMap<>(), new PlateRowMapper());
        Map<String,Integer> plateMap = new HashMap<>();
        for (Plate plate: plateList) {
            plateMap.put(plate.getPlateName().name(),plate.getPlateId());
        }
        return plateMap.get(plateName);
    }

    @Override
    public void deleteGameByGameId(Integer gameId) {
        String sql = "DELETE FROM game WHERE game_id=:gameId";
        Map<String,Object> map = new HashMap<>();
        map.put("gameId",gameId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteVersionsByGameId(Integer gameId) {
        String sql = "DELETE FROM version WHERE game_id=:gameId";
        Map<String,Object> map = new HashMap<>();
        map.put("gameId",gameId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deletePropertiesByGameId(Integer gameId) {
        String sql = "DELETE FROM property WHERE game_id=:gameId";
        Map<String,Object> map = new HashMap<>();
        map.put("gameId",gameId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void updateGameById(Integer gameId, GameAddRequest gameAddRequest) {
        String sql = "UPDATE game SET game_name=:gameName, dev_company=:devCompany, pub_company=:pubCompany, " +
                "intro=:intro, image_path=:imagePath, last_modified_date=:lastModifiedDate WHERE game_id = :gameId";
        Map<String,Object> map = new HashMap<>();
        map.put("gameName",gameAddRequest.getGameName());
        map.put("devCompany",gameAddRequest.getDevCompany());
        map.put("pubCompany",gameAddRequest.getPubCompany());
        map.put("intro",gameAddRequest.getIntro());
        map.put("imagePath",gameAddRequest.getImagePath());
        map.put("lastModifiedDate",new Date());
        map.put("gameId",gameId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public Integer getGameCount(GameQueryParam gameQueryParam) {
        String sql = "SELECT COUNT(DISTINCT (g.game_id)) FROM game  g INNER JOIN (tag INNER JOIN property p on tag.tag_id = p.tag_id) ON g.game_id=p.game_id " +
                "    INNER JOIN (version v INNER JOIN plate pl ON v.plate_id=pl.plate_id) ON v.game_id=g.game_id " +
                "WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        sql = addFilteringSql(sql,map,gameQueryParam);
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return count;
    }

    @Override
    public List<Game> getGames(GameQueryParam gameQueryParam) {
        String sql = "SELECT DISTINCT(game_name),g.game_id,dev_company,pub_company,intro,image_path,created_date,last_modified_date " +
                "FROM game  g INNER JOIN (tag INNER JOIN property p on tag.tag_id = p.tag_id) ON g.game_id=p.game_id " +
                "INNER JOIN (version v INNER JOIN plate pl ON v.plate_id=pl.plate_id) ON v.game_id=g.game_id " +
                "WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        sql = addFilteringSql(sql,map,gameQueryParam);
        sql += " ORDER BY "+gameQueryParam.getOrderBy()+" "+gameQueryParam.getSort();
        sql += " LIMIT :limit OFFSET :offset";
        map.put("limit",gameQueryParam.getLimit());
        map.put("offset",gameQueryParam.getOffset());
        List<Game> gameList = namedParameterJdbcTemplate.query(sql, map, new GameRowMapper());
        return gameList;
    }

    private String addFilteringSql(String sql, Map<String, Object> map, GameQueryParam gameQueryParam) {
        //根據名稱
        if(gameQueryParam.getSearch()!=null){
            sql+="AND game_name LIKE :gameName ";
            map.put("gameName","%"+gameQueryParam.getSearch()+"%");
        }
        //根據tag
        if(gameQueryParam.getTag()!=null){
            sql+="AND tag_name = :tagName ";
            map.put("tagName",gameQueryParam.getTag());
        }
        //根據plate
        if(gameQueryParam.getPlateName()!=null){
            sql+="AND plate_name = :plateName ";
            map.put("plateName",gameQueryParam.getPlateName().name());
        }
        return sql;
    }

    @Override
    public Version getVersionByGameIdAndPlateId(Integer gameId, Integer plateId) {
        String sql= "SELECT plate_name,price,quantity " +
                "  FROM  version INNER JOIN plate p on version.plate_id = p.plate_id " +
                "WHERE 1=1 AND game_id=:gameId AND p.plate_id=:plateId";
        Map<String,Object> map  = new HashMap<>();
        map.put("gameId",gameId);
        map.put("plateId",plateId);
        List<Version> versions = namedParameterJdbcTemplate.query(sql, map, new VersionRowMapper());
        if(versions!=null){
            return versions.get(0);
        }else return null;
    }

    @Override
    public String getPlateNameByPlateId(Integer plateId) {
        String sql = "SELECT plate_name FROM plate WHERE plate_id=:plateId";
        Map<String,Object> map = new HashMap<>();
        map.put("plateId",plateId);
        String plateName = namedParameterJdbcTemplate.queryForObject(sql, map, String.class);
        return plateName;
    }

    @Override
    public void updateStock(Integer gameId, Integer plateId, Integer remaining) {
        String sql = "UPDATE version SET quantity=:quantity WHERE game_id=:gameId AND plate_id=:plateId ";
        Map<String,Object> map = new HashMap<>();
        map.put("quantity",remaining);
        map.put("gameId",gameId);
        map.put("plateId",plateId);
        namedParameterJdbcTemplate.update(sql,map);
    }
}

