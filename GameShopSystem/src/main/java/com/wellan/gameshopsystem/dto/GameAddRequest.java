package com.wellan.gameshopsystem.dto;

import com.wellan.gameshopsystem.model.Version;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
@Data
public class GameAddRequest {
    private String gameName;
    private String devCompany;
    private String pubCompany;
    private String intro;
    private String imagePath;
    @NotEmpty
    private List<String> properties;
    @NotEmpty
    private List<VersionRequest> versions;
}
