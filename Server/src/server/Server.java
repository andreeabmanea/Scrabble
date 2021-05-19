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
        System.out.println("Game is ready, waiting for player to connect");
       // Server server = new Server();

        //Generate new sack
        LetterSack lb = new LetterSack();
        System.out.println("Here we have the sack:");
        System.out.println(lb.letterSack);
        System.out.println();

        System.out.println("Here we have the player's letters:");
        LetterHolder lh = new LetterHolder(lb);
        System.out.println(lh.currentLetters);
        System.out.println();

        System.out.println("This is how the board looks:");
        Board board = new Board();
        board.printBoard();

    }
}