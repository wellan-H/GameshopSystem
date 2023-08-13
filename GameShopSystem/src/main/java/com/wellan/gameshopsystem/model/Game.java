package com.wellan.gameshopsystem.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class Game {
    private Integer gameId;
    private String gameName;
    private String devCompany;
    private String pubCompany;
    private String intro;
    private String imagePath;
    private Date createdDate;
    private Date lastModifiedDate;
}
