package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // Define the port on which the server is listening
    public static final int PORT = 7070;

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


//        System.out.println();
//        System.out.println("This is what he has left on the holder");
//        System.out.println(currentPlayer.getHolder().currentLetters);
//
//        System.out.println("This is how the board looks like, after first turn:");
//        game.getBoard().printBoardWithContent();



//        System.out.println("After refill:");
//        System.out.println(currentPlayer.getHolder().currentLetters);
//
//        System.out.println("Holder after shuffle:");
//        currentPlayer.shuffleHolder();
//        System.out.println(currentPlayer.getHolder().currentLetters);
   //     System.out.println("The word is: " + game.word);
//        System.out.println("Here are some suggestions for anagrams: ");
//        game.computeAnagrams("", game.word);
//        game.showAnagrams();
//        game.overTurn();

        while (game.getLetterSack().letterSack.size() > 0)
            game.playTurn();

//        currentPlayer.putLetterInTile(currentPlayer.getHolder().currentLetters.get(1), 2, 0);
//        currentPlayer.putLetterInTile(currentPlayer.getHolder().currentLetters.get(1), 2, 2);
//        game.gameBoard.printBoardWithContent();
//        game.addMissing();
//        System.out.println(game.getPendingWord() + " " + game.coordX + " " + game.coordY);
//        System.out.println("Added:" + game.addedX + " " + game.addedY);
//        System.out.println("The score: " + game.computeScoreOfWord());
//        game.removeWordFromBoard();
//        game.getGameBoard().printBoardWithContent();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Socket socket = serverSocket.accept();
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());

            outStream.writeObject(new Game()); // send the game

            outStream.writeObject(game.getCurrentPlayer().getHolder().getCurrentLetters()); // send his letters

        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        }
    }
}