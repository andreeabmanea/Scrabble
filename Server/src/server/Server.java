package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // Define the port on which the server is listening
    public static final int PORT = 7070;
    public Server() throws IOException {
        ServerSocket serverSocket = null;
        try {
            System.out.println("Game is ready, waiting for player to connect");
            serverSocket = new ServerSocket(PORT);
            Socket socket = serverSocket.accept();
            // Execute the client's request in a new thread
            new ClientThread(socket).start();
            System.out.println("Client connected");
        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        } finally {
            serverSocket.close();
        }
    }

    public static void main ( String [] args ) throws IOException {

        Game game = new Game();
        //We generate the letter sack
        System.out.println("The initial letter sack is:");
        System.out.println(game.getLetterSack().letterSack);
        System.out.println();

        System.out.println("This is how the board looks like, based on the tiles type");
        game.getBoard().printBoard();
        System.out.println();

        //The player draws first letters
        Player currentPlayer = game.getCurrentPlayer();
        System.out.println("The player's letters are:");
        System.out.println(currentPlayer.getHolder().currentLetters);

        //The player puts a word on the board
        currentPlayer.putLetterInTile(currentPlayer.getHolder().currentLetters.get(1), 0, 0);
        currentPlayer.putLetterInTile(currentPlayer.getHolder().currentLetters.get(1), 0, 1);
        currentPlayer.putLetterInTile(currentPlayer.getHolder().currentLetters.get(1), 0, 2);
        currentPlayer.putLetterInTile(currentPlayer.getHolder().currentLetters.get(1), 0, 3);

        System.out.println();
        System.out.println("This is what he has left on the holder");
        System.out.println(currentPlayer.getHolder().currentLetters);

        System.out.println("This is how the board looks like, after first turn:");
        game.getBoard().printBoardWithContent();

        System.out.println("This was his word:");
        System.out.println(game.pendingWord);
        System.out.println(game.coordX + " " + game.coordY);
        System.out.println("The score for the word:");
        System.out.println(game.computeScoreOfWord());

        System.out.println("After refill:");
        System.out.println(currentPlayer.getHolder().currentLetters);

        System.out.println("Holder after shuffle:");
        currentPlayer.shuffleHolder();
        System.out.println(currentPlayer.getHolder().currentLetters);
        System.out.println("The word is: ");
        System.out.println(game.word);
        System.out.println("Here are some suggestions for anagrams: ");
        game.computeAnagrams("", game.word);
        game.showAnagrams();
        Server server = new Server();
    }
}