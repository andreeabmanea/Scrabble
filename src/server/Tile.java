package server;

import java.io.Serializable;

public class Tile implements Serializable {

    int line, col;
    private final Board gameBoard;
    public String type;
    public Letter content;

    public Tile(Board gameBoard, int line, int col, String type, Letter content) {
        this.gameBoard = gameBoard;
        this.col = col;
        this.line = line;
        this.type = type;
        this.content = content;
    }

    public Letter getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
