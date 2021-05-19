package client;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Application {
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
    public static void main (String[] args) throws IOException {
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 7070; // The server's port
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader (
                        new InputStreamReader(socket.getInputStream())) ) {
            // Send a request to the server
            String request;
            launch(args);
            while (true) {
                Scanner scanner = new Scanner(System.in);
                request = scanner.next();
                if ("exit".equals(request)) {
                    System.out.println("Done");
                    break;
                } else {
                    out.println(request);
                    String response = in.readLine();
                    System.out.println(response);
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }

    }

}
