package com.minesweeper.minesweeperserver.socket.playeraction;

public class PlayerAction {
    String field;
    String action;

    PlayerAction() {
    }

    public Field getParsedField() {
        String[] coords = field.split(",");
        if (coords.length != 2) throw new RuntimeException();
        return new Field(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
