package com.minesweeper.minesweeperserver.logic.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GameSettings {
    private int rows;
    private int cols;
    private int mines;
}