package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // Define the port on which the server is listening
    public static final int PORT = 7070;

    public static void main ( String [] args ) throws IOException {

        Game game = new Game();
//        //We generate the letter sack
//        System.out.println("The initial letter sack is:");
//        System.out.println(game.getLetterSack().letterSack);
//        System.out.println();
//
//        System.out.println("This is how the board looks like, based on the tiles type");
//        game.getBoard().printBoard();
//        System.out.println();
//
//        //The player draws first letters
//        Player currentPlayer = game.getCurrentPlayer();
//        System.out.println("The player's letters are:");
//        System.out.println(currentPlayer.getHolder().currentLetters);

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

//        while (game.getLetterSack().letterSack.size() > 0)
//            game.playTurn();

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
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());

            outStream.writeObject(game.getBoard()); // give client the board
            outStream.flush();

            String response;
            int index;
            int row;
            int column;
            int score = 0;

            while (game.getLetterSack().getLetterSack().size() > 0) {
                while (true) {
                    outStream.reset();
                    outStream.writeObject(game.getCurrentPlayer().getHolder().getCurrentLetters());
                    outStream.flush();

                    // still continue
                    response = in.readLine();
                    while (response.equals("help") || response.equals("shuffle")) {
                        if (response.equals("help")) {
                            game.computeAnagrams("", in.readLine());
                            game.checkAnagrams();
                            outStream.reset();
                            outStream.writeObject(game.getAnagrams());
                            outStream.flush();
                            game.anagrams.clear();
                        } else {
                            game.getCurrentPlayer().shuffleHolder();
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
                // verify the word and make points
                game.addMissing();
                game.transformPendingWord();
                System.out.println("This was his word: " + game.word);
                if (!game.confirmWord()) {
                    for (int i = 0; i < game.addedX.size(); i++)
                        game.getCurrentPlayer().getHolder().getCurrentLetters().add(game.getBoard().board[game.addedX.get(i)][game.addedY.get(i)].content);
                    game.removeWordFromBoard();
                    System.out.println("The word does not exist!");
                    game.overTurn();
                } else {
                    score = game.computeScoreOfWord();
                    System.out.println("The score for the word:" + score);
                    game.getCurrentPlayer().refillAfterTurn();
                }
                game.board.printBoardWithContent();

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