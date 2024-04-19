package com.minesweeper.minesweeperserver.logic;

public record PlayerAction(int col, int row, PlayerActionType action, String username) {
}