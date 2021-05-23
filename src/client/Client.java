package client;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import server.*;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public class Client extends Application {
    public static final int PORT = 7070;
    public static final String ADDRESS = "127.0.0.1";
    public static final int NUM_COLS = 15;
    public static final int NUM_ROWS = 15;
    public static Game game;

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(ADDRESS, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream()) ) {
            Scanner scan = new Scanner(System.in);

//            game = (Game) inStream.readObject(); // read the game

            System.out.println("Hello! Here is the current board");
            Board board = (Board) inStream.readObject();
            board.printBoard();
            List<Letter> letters;
            String command;
            while (true) {
                for (int i = 0; i < 7; i++) {
                    // read letters
                    System.out.println("Here are your letters: " + inStream.readObject());

                    System.out.print("Continue? yes/no/help/shuffle: ");
                    command = scan.next();
                    while (command.equals("help") || command.equals("shuffle")) {
                        // give a response
                        out.println(command);
                        out.flush();

                        if (command.equals("help")) {
                            System.out.print("Please enter your letters (ex: ABC): ");
                            out.println(scan.next());
                            System.out.println("This are the results: " + inStream.readObject());
                        } else {
                            System.out.println("Here are your new letters: " + inStream.readObject());
                        }

                        System.out.print("Continue? yes/no/help/shuffle: ");
                        command = scan.next();
                    }
                    if (command.equals("no")) {
                        out.println(command);
                        out.flush();
                        break;
                    }
                    out.println(command);
                    out.flush();

                    System.out.print("Please enter the index of the letter: ");
                    out.println(scan.nextInt());
                    System.out.print("The line where you want the letter to be: ");
                    out.println(scan.nextInt());
                    System.out.print("And the column where you want the letter to be: ");
                    out.println(scan.nextInt());
                }
                board = (Board) inStream.readObject();
                board.printBoardWithContent();
                System.out.println("Current score: " + inStream.read());
            }
//            launch(args);
        } catch (UnknownHostException | ClassNotFoundException e) {
            System.err.println("No server listening... " + e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        // images
        Image whiteTile = new Image("resources_client/Clear_tile.png");
        Image tripleWordTile = new Image("resources_client/Tile_3W.png");
        Image doubleWordTile = new Image("resources_client/Tile_2W.png");
        Image tripleLetterTile = new Image("resources_client/Tile_3L.png");
        Image doubleLetterTile = new Image("resources_client/Tile_2L.png");
        Image centerTile = new Image("resources_client/Star.png");

        StackPane window = new StackPane();
        Scene scene = new Scene(window, 1000, 600);

        Board mainBoard = game.getBoard();
        Tile tile;

        GridPane board = new GridPane();
        for (int i = 0; i < NUM_COLS; i++)
            for (int j = 0; j < NUM_ROWS; j++) {
                tile = mainBoard.getTile(i, j); // get board
                // set tiles
                switch (tile.getType()) {
                    case "00" -> board.add((new ImageView(whiteTile)), j, i);
                    case "Mi" -> board.add((new ImageView(centerTile)), j, i);
                    case "3W" -> board.add((new ImageView(tripleWordTile)), j, i);
                    case "2W" -> board.add((new ImageView(doubleWordTile)), j, i);
                    case "3L" -> board.add((new ImageView(tripleLetterTile)), j, i);
                    case "2L" -> board.add((new ImageView(doubleLetterTile)), j, i);
                }
            }

        LetterPane letterBar = new LetterPane(game.getCurrentPlayer().getHolder().getCurrentLetters());


        board.setAlignment(Pos.CENTER);
        BorderPane mainPane = new BorderPane(board, null, null, null, letterBar.getRoot());

        window.getChildren().add(mainPane);
        primaryStage.setTitle("Scrabble Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}