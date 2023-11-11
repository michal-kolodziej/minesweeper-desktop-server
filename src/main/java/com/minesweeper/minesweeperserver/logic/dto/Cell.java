package com.minesweeper.minesweeperserver.logic.dto;

import com.minesweeper.minesweeperserver.logic.enums.CellContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cell {
    private CellContent content;
    private int surroundingMines;

    public Cell(CellContent content) {
        this.content = content;
    }

    public boolean isMine() {
        return content.equals(CellContent.MINE);
    }
}