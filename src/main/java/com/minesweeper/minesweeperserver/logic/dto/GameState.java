package com.minesweeper.minesweeperserver.logic.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class GameState {
    private Date startDate;
    private int remainingMines;
    private Status currentStatus;

    public GameState(int remainingMines) {
        this.startDate = new Date();
        this.remainingMines = remainingMines;
        this.currentStatus = Status.ONGOING;
    }
    public void updateRemainingMines(int toAdd){
        remainingMines += toAdd;
    }

    public boolean isGameOver(){
        return currentStatus != Status.ONGOING;
    }
    public enum Status {
        WON, LOST, ONGOING
    }
}