package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import server.*;

import java.awt.*;
import java.awt.event.MouseEvent;
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
    public static Board board;
    public static List<Letter> letters;
    public static Integer score;

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(ADDRESS, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream()) ) {
            Scanner scan = new Scanner(System.in);

//            game = (Game) inStream.readObject(); // read the game

            System.out.println("Hello! Here is the current board");
            board = (Board) inStream.readObject();
            board.printBoardWithContent();
            String command;

            while (true) {
                for (int i = 0; i < 7; i++) {
                    // read letters

                    letters = (List<Letter>) inStream.readObject();
                    System.out.println("Here are your letters: " + letters);
                  launch(args);

                    if (i == 0) {
                        System.out.print("Continue? yes/no/help/shuffle: ");
                    } else {
                        System.out.print("Continue? yes/no/help: ");
                    }
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
                            letters = (List<Letter>) inStream.readObject();
                            System.out.println("Here are your new letters: " + letters);
                        }

                        if (i == 0) {
                            System.out.print("Continue? yes/no/help/shuffle: ");
                        } else {
                            System.out.print("Continue? yes/no/help: ");
                        }
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

                    if (inStream.read() == 1) {
                        System.out.print("Enter Joker's value: ");
                        out.println(scan.next());
                    }
                }
                board = (Board) inStream.readObject();
                board.printBoardWithContent();
                score = inStream.read();
                System.out.println("Current score: " + score);
            }
        } catch (UnknownHostException | ClassNotFoundException e) {
            System.err.println("No server listening... " + e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Image whiteTile = new Image("resources_client/Clear_tile.png");
        Image tripleWordTile = new Image("resources_client/Tile_3W.png");
        Image doubleWordTile = new Image("resources_client/Tile_2W.png");
        Image tripleLetterTile = new Image("resources_client/Tile_3L.png");
        Image doubleLetterTile = new Image("resources_client/Tile_2L.png");
        Image centerTile = new Image("resources_client/Star.png");

        Tile tile;

        GridPane boardPane = new GridPane();
        for (int i = 0; i < NUM_COLS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                tile = board.getTile(i, j); // get board
                // set tiles
                switch (tile.getType()) {
                    case "00" -> boardPane.add((new ImageView(whiteTile)), j, i);
                    case "Mi" -> boardPane.add((new ImageView(centerTile)), j, i);
                    case "3W" -> boardPane.add((new ImageView(tripleWordTile)), j, i);
                    case "2W" -> boardPane.add((new ImageView(doubleWordTile)), j, i);
                    case "3L" -> boardPane.add((new ImageView(tripleLetterTile)), j, i);
                    case "2L" -> boardPane.add((new ImageView(doubleLetterTile)), j, i);
                }
            }
        }


        StackPane window = new StackPane();
        Scene scene = new Scene(window, 1000, 600);

        LetterPane letterBar = new LetterPane(letters);
        ControlPane controlPane = new ControlPane();


        boardPane.setAlignment(Pos.CENTER);
        BorderPane mainPane = new BorderPane(boardPane, null, controlPane.getRoot(), null, letterBar.getRoot());

        window.getChildren().add(mainPane);
        primaryStage.setTitle("Scrabble Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        letterBar.getButtons().get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                boardPane.add(letterBar.getButtons().get(0).getGraphic(), 7, 7);
                letterBar.getButtons().get(0).setGraphic(null);

            }
        });

        letterBar.getButtons().get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                boardPane.add(letterBar.getButtons().get(1).getGraphic(), 8, 7);
                letterBar.getButtons().get(1).setGraphic(null);

            }
        });

        letterBar.getButtons().get(2).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                boardPane.add(letterBar.getButtons().get(2).getGraphic(), 9, 7);
                letterBar.getButtons().get(2).setGraphic(null);

            }
        });

        letterBar.getButtons().get(3).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                boardPane.add(letterBar.getButtons().get(3).getGraphic(), 10, 7);
                letterBar.getButtons().get(3).setGraphic(null);

            }
        });

        letterBar.getButtons().get(4).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                boardPane.add(letterBar.getButtons().get(4).getGraphic(), 11, 7);
                letterBar.getButtons().get(4).setGraphic(null);

            }
        });

        letterBar.getButtons().get(5).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                boardPane.add(letterBar.getButtons().get(5).getGraphic(), 7, 8);
                letterBar.getButtons().get(5).setGraphic(null);

            }
        });

        letterBar.getButtons().get(6).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                boardPane.add(letterBar.getButtons().get(6).getGraphic(), 7, 9);
                letterBar.getButtons().get(6).setGraphic(null);

            }
        });

    }
}