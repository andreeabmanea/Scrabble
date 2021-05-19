package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Board {

    Tile[][] board = new Tile[15][15];

    public Board(){
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

    @Override
    public String toString() {
        return "Board{" +
                "board=" + Arrays.toString(board) +
                '}';
    }
}
