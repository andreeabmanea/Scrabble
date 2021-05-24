package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // Define the port on which the server is listening
    public static final int PORT = 7070;

    public static void main ( String [] args ) {

        Game game = new Game(); // make a game

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());

            outStream.writeObject(game.getBoard()); // give client the board
            outStream.flush();

            // for client's response, information about the inputted letter, and score
            String response;
            int index;
            int row;
            int column;
            int score = 0;

            // while there are letters in the sack
            while (game.getLetterSack().getLetterSack().size() > 0) {
                while (true) {
                    outStream.reset();
                    outStream.writeObject(game.getCurrentPlayer().getHolder().getCurrentLetters()); // give player's letters
                    outStream.flush();

                    // still continue
                    response = in.readLine();
                    while (response.equals("help") || response.equals("shuffle")) { // can do multiple helps and shuffles
                        if (response.equals("help")) {
                            game.computeAnagrams("", in.readLine()); // generate anagrams based on client's letters choice
                            game.checkAnagrams();
                            outStream.reset();
                            outStream.writeObject(game.getAnagrams()); // send the anagrams to the client
                            outStream.flush();
                            game.anagrams.clear();
                        } else {
                            game.getCurrentPlayer().shuffleHolder(); // shuffle
                            outStream.reset();
                            outStream.writeObject(game.getCurrentPlayer().getHolder().getCurrentLetters());
                            outStream.flush();
                        }
                        response = in.readLine();
                    }
                    if (response.equals("no"))
                        break;

                    // index, row and column
                    index = Integer.parseInt(in.readLine());
                    row = Integer.parseInt(in.readLine());
                    column = Integer.parseInt(in.readLine());
                    System.out.println("The letter: " + game.getCurrentPlayer().getHolder().getCurrentLetters().get(index));

                    outStream.reset();
                    // if the player used a joker
                    if (game.getCurrentPlayer().getHolder().getCurrentLetters().get(index).getLetterName().equals("@")) {
                        outStream.write(1);
                        outStream.flush();
                        // change tile
                        game.getCurrentPlayer().putLetterInTile(game.getCurrentPlayer().getHolder().getCurrentLetters().get(index), row, column);
                        Letter jokersValue = new Letter(in.readLine(), 0);
                        game.getPendingWord().remove(game.getPendingWord().size() - 1);
                        game.getPendingWord().add(jokersValue);
                    }
                    else {
                        outStream.write(0);
                        outStream.flush();
                        // change tile
                        game.getCurrentPlayer().putLetterInTile(game.getCurrentPlayer().getHolder().getCurrentLetters().get(index), row, column);
                    }

                }
                // add missing letters from the board to player's word
                game.addMissing();
                // verify the word and make points
                game.transformPendingWord();
                System.out.println("This was his word: " + game.getWord());
                if (!game.confirmWord()) {
                    for (int i = 0; i < game.getAddedX().size(); i++)
                        game.getCurrentPlayer().getHolder().getCurrentLetters().add(game.getBoard().getBoard()[game.getAddedX().get(i)][game.getAddedY().get(i)].getContent());
                    game.removeWordFromBoard();
                    System.out.println("The word does not exist!");
                    game.overTurn();
                } else {
                    score = game.computeScoreOfWord();
                    System.out.println("The score for the word:" + score);
                    game.getCurrentPlayer().refillAfterTurn();
                }
                game.getBoard().printBoardWithContent();

                // send the new board
                outStream.reset();
                outStream.writeObject(game.getBoard());
                outStream.flush();
                outStream.reset();
                outStream.write(game.getCurrentPlayer().getScore());
                outStream.flush();
            }

        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        }
    }
}