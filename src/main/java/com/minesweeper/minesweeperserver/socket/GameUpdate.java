package com.minesweeper.minesweeperserver.socket;

import lombok.Data;

@Data
public class GameUpdate {
    final String[][] board;
    final int remainingMines;
}
