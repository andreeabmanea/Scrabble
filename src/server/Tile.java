package server;

import java.io.Serializable;

public class Tile implements Serializable {

    int line, col;
    private final Board gameBoard;
    String type;
    Letter content;

    public Tile(Board gameBoard, int line, int col, String type, Letter content) {
        this.gameBoard = gameBoard;
        this.col = col;
        this.line = line;
        this.type = type;
        this.content = content;
    }

    @Override
    public String toString() {
        return type;
    }
}
