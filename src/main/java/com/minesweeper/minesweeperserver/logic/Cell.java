package com.minesweeper.minesweeperserver.logic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cell {
    boolean visible = false;
    boolean flagged = false;
    private CellContent content;
    private int surroundingMines;

    public Cell(CellContent content) {
        this.content = content;
    }

    public boolean isMine() {
        return content.equals(CellContent.MINE);
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean toggleFlag() {
        flagged = !flagged;
        return flagged;
    }
}