package com.minesweeper.minesweeperserver.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameSettings {
    private int rows;
    private int cols;
    private int mines;
}