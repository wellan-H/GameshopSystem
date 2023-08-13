package com.wellan.gameshopsystem.controller;

import com.wellan.gameshopsystem.dto.GameAddRequest;
import com.wellan.gameshopsystem.dto.GameQueryParam;
import com.wellan.gameshopsystem.model.CompanyCategory;
import com.wellan.gameshopsystem.model.Game;
import com.wellan.gameshopsystem.model.GameDetail;
import com.wellan.gameshopsystem.model.PlateCategory;
import com.wellan.gameshopsystem.service.GameService;
import com.wellan.gameshopsystem.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class GameController {
    @Autowired
    private GameService gameService;
    //增加
    @PostMapping("/games/add")
    public ResponseEntity<GameDetail> createGame(@RequestBody @Valid GameAddRequest gameAddRequest){
        Integer id = gameService.addGame(gameAddRequest);
        GameDetail game = gameService.getGameDetailById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }
    //刪除遊戲
     @DeleteMapping("games/{gameId}")
    public ResponseEntity<?> deleteGame(@PathVariable Integer gameId){
        gameService.deleteGame(gameId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
     }
    //更新遊戲
    @PutMapping("/games/{gameId}")
    public ResponseEntity<?> updateGame(@PathVariable Integer gameId,
                                                 @RequestBody @Valid GameAddRequest gameAddRequest){
        //檢測是否存在對應此gameId的遊戲
        GameDetail game = gameService.getGameDetailById(gameId);
        if(game==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //檢測更新後的名稱是否與現有的遊戲重名，若重名則回傳BAD REQUEST
        //此處查找的結果及必須排除自身，避免永遠無法更新成功
        Boolean res = gameService.isUnique(gameAddRequest.getGameName(),gameId);
        if(!res){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("修改後的名稱已存在，請確認後再試一次");
        }
        gameService.updateGame(gameId,gameAddRequest);
        GameDetail updateGame = gameService.getGameDetailById(gameId);
        return ResponseEntity.status(HttpStatus.OK).body(updateGame);
    }
    //查詢所有遊戲
    @GetMapping("/games")
    public ResponseEntity<Page<GameDetail>> getGames(@RequestParam(required = false) String search,
                                                     @RequestParam(required = false) String tag,
                                                     @RequestParam(required = false) PlateCategory plateName,
                                                     @RequestParam(defaultValue = "created_date")String orderBy,
                                                     @RequestParam(defaultValue = "DESC") String sort,
                                                     @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
                                                     @RequestParam(defaultValue = "0") @Min(0) Integer offset){
        GameQueryParam gameQueryParam = new GameQueryParam();
        gameQueryParam.setSearch(search);
        gameQueryParam.setTag(tag);
        gameQueryParam.setPlateName(plateName);
        gameQueryParam.setSort(sort);
        gameQueryParam.setOffset(offset);
        gameQueryParam.setLimit(limit);
        gameQueryParam.setOrderBy(orderBy);
        List<GameDetail> gameDetailList = gameService.getGameDetails(gameQueryParam);
        Page<GameDetail> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(gameService.getGameCount(gameQueryParam));
        page.setList(gameDetailList);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
    //查詢特定遊戲
    @GetMapping("/games/{gameId}")
    public ResponseEntity<GameDetail> getGameById(@PathVariable Integer gameId){
        GameDetail gameDetail = gameService.getGameDetailById(gameId);
        if(gameDetail==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else return ResponseEntity.status(HttpStatus.OK).body(gameDetail);
    }


}
