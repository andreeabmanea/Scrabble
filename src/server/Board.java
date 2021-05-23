package server;

import java.io.*;
import java.util.Arrays;

public class Board implements Serializable {

    Tile[][] board = new Tile[15][15];

    public Board(Game game){
        try (FileReader fileReader = new FileReader("src/resources/boardConfiguration.csv");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tileInfo = line.split(",");
                int row = Integer.parseInt(tileInfo[0]);
                int col = Integer.parseInt(tileInfo[1]);
                String type = tileInfo[2];
                board[row][col] = new Tile(this, row, col, type, null);
                }
            } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++) {
                if (board[i][j] == null) {
                    board[i][j] = new Tile(this, i, j, "00", null);
                }
            }
    }

    public void printBoard() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void placeLetter(Letter letter, int i, int j) {
        if (board[i][j].content == null) {
            board[i][j].content = letter;
        }
    }

    public void printBoardWithContent() {
        System.out.print("/" + " ");
        for (int i = 0; i < 15; i++)
            System.out.print(i%10 + " ");
        System.out.println();
        for (int i = 0; i < 15; i++) {
            System.out.print(i%10 + " ");
            for (int j = 0; j < 15; j++)
                if (board[i][j].content == null)
                    System.out.print("-" + " ");
                else
                    System.out.print(board[i][j].content + " ");
            System.out.println();
        }
    }

    public Tile getTile(int i, int j) {

        return board[i][j];
    }

    //from one tile we compute the neighbours

    public Tile getLeft(Tile tile) {

        if (tile.col == 0)
            return null;
        else return getTile(tile.line, tile.col - 1);
    }

    public Tile getUp(Tile tile) {

        if (tile.line == 0)
            return null;
        else return getTile(tile.line + 1, tile.col);
    }

    public Tile getRight(Tile tile) {

        if (tile.col == 14)
            return null;
        else return getTile(tile.line, tile.col + 1);
    }

    public Tile getDown(Tile tile) {
        if (tile.line == 14)
            return null;
        else return getTile(tile.line - 1, tile.col);
    }

    @Override
    public String toString() {
        return "Board{" +
                "board=" + Arrays.toString(board) +
                '}';
    }
}
