package com.wellan.gameshopsystem.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GameDetail {
    public GameDetail(){}
    public GameDetail(Game game,List<Version> versions,List<String> tags){
        this.gameId=(game.getGameId());
        this.gameName=(game.getGameName());
        this.devCompany=(game.getDevCompany());
        this.pubCompany=(game.getPubCompany());
        this.intro=(game.getIntro());
        this.imagePath=(game.getImagePath());
        this.createdDate=(game.getCreatedDate());
        this.lastModifiedDate=(game.getLastModifiedDate());
        this.properties=(tags);
        this.versions=(versions);
    }
    private Integer gameId;
    private String gameName;
    private String devCompany;
    private String pubCompany;
    private String intro;
    private String imagePath;
    private List<String> properties;
    private List<Version> versions;
    private Date createdDate;
    private Date lastModifiedDate;
}
