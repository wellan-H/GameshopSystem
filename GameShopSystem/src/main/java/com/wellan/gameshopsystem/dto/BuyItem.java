package com.wellan.gameshopsystem.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BuyItem {
    @NotNull
    private Integer gameId;
    @NotNull
    private Integer plateId;
    @NotNull
    private Integer quantity;
}
