package com.wellan.gameshopsystem.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
@Data
public class OrderAddRequest {
    @NotEmpty
    private List<BuyItem> buyItemList;

}
