package com.minesweeper.minesweeperserver.logic.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class GameState {
    private Date startDate;
    private int remainingBombs;

    public GameState(int remainingBombs) {
        this.startDate = new Date();
        this.remainingBombs = remainingBombs;
    }
}