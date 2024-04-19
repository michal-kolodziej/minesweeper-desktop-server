package com.minesweeper.minesweeperserver.logic;

import com.minesweeper.minesweeperserver.logic.GameStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class GameState {
    private Date startDate;
    private int remainingMines;
    private GameStatus currentStatus;

    public GameState(int remainingMines) {
        this.startDate = new Date();
        this.remainingMines = remainingMines;
        this.currentStatus = GameStatus.ONGOING;
    }
    public void updateRemainingMines(int toAdd){
        remainingMines += toAdd;
    }

    public boolean isGameOver(){
        return currentStatus != GameStatus.ONGOING;
    }

}