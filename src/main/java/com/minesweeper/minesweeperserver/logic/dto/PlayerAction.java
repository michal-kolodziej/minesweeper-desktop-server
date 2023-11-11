package com.minesweeper.minesweeperserver.logic.dto;

import com.minesweeper.minesweeperserver.logic.enums.Action;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerAction {
    private int col;
    private int row;
    private Action action;
}