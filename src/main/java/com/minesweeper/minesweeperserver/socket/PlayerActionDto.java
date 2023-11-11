package com.minesweeper.minesweeperserver.socket;

public record PlayerActionDto(int row, int col, String action) {
}
