package com.wellan.gameshopsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class User {
    Integer userId;
    String email;
    @JsonIgnore
    String password;
    Date createdDate;
    Date lastModifiedDate;
}
