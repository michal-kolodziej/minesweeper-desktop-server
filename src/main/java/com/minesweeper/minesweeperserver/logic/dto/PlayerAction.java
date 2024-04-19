package com.minesweeper.minesweeperserver.logic.dto;

import com.minesweeper.minesweeperserver.logic.enums.Action;

public record PlayerAction(int col, int row, Action action, String username) {
}