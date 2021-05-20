package client;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import server.Board;
import server.Letter;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Client extends Application {
    public static final int PORT = 7070;
    public static final String ADDRESS = "127.0.0.1";

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(ADDRESS, PORT);
             ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream()) ) {

            Board board = (Board)inStream.readObject(); // get board
//            board.printBoard();
            List<Letter> pouch = (List<Letter>) inStream.readObject(); // get initial letters
//            System.out.println(pouch);


        } catch (UnknownHostException | ClassNotFoundException e) {
            System.err.println("No server listening... " + e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        StackPane window = new StackPane();
        Scene scene = new Scene(window, 1000, 600);
        GridPane board = new GridPane();
        Image whiteTile = new Image("resources/Casuta_goala.png");
        Image tripleWordTile = new Image("resources/Casuta_3W.png");
        Image doubleWordTile = new Image("resources/Casuta_2W.png");
        Image tripleLetterTile = new Image("resources/Casuta_3L.png");
        Image doubleLetterTile = new Image("resources/Casuta_2L.png");
        Image centerTile = new Image("resources/Stea.png");
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++) {
                if ((i == 0 || i == 7 || i == 14) && j == 0 || ((i == 14 || i == 7 || i == 0) && j == 14) || (i == 0 && j == 7) || (i == 14 && j == 7)) {
                    ImageView tripleWordView = new ImageView(tripleWordTile);
                    board.add(tripleWordView, j, i);
                } else if (((i == 3 || i == 11) && (j == 0 || j == 14)) || ((j == 3 || j == 11) && (i == 0 || i == 14)) || ((i == 2 || i == 12) && (j == 6 || j == 8)) || ((i == 3 || i == 11) && j == 7) || ((i == 6 || i == 8) && (j == 2 || j == 6 || j == 8 || j == 12)) || (i == 7 && (j== 3 || j == 11))){
                    ImageView doubleLetterView = new ImageView(doubleLetterTile);
                    board.add(doubleLetterView, j, i);
                } else if ((i == 5 || i == 9) && (j == 1 || j == 5 || j == 9 || j == 13) || ((i == 1 || i == 13) && (j == 5 || j == 9))) {
                    ImageView tripleLetterView = new ImageView(tripleLetterTile);
                    board.add(tripleLetterView, j, i);
                } else if (i == 7 && j == 7) {
                    ImageView centerView = new ImageView(centerTile);
                    board.add(centerView, j, i);
                } else if ((i == j ||  i + j == 14) && (i > 0 && i < 5 || i > 9 && i < 14)) {
                    ImageView doubleWordView = new ImageView(doubleWordTile);
                    board.add(doubleWordView, j, i);
                }
                else {
                    ImageView whiteView = new ImageView(whiteTile);
                    board.add(whiteView, j, i);
                }
            }

        window.getChildren().add(board);
        board.setAlignment(Pos.CENTER);
        primaryStage.setTitle("Scrabble Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}