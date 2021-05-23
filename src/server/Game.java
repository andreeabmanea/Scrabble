package server;

import java.io.*;
import java.util.*;

public class Game implements Serializable{
    private final List<Player> players = new ArrayList<>();
    private Integer turn;
    private Player currentPlayer;
    public final LetterSack letterSack = new LetterSack();
    public final Board board = new Board(this);
    public List<Letter> pendingWord = new ArrayList<>();
    //We use this for computing the score of the whole word, after adding the missing tiles that were already on the board
    public final List<Integer> coordX = new ArrayList<>();
    public final List<Integer> coordY = new ArrayList<>();
    //We use this to memorize only the positions of the letters that were added by the player at a round
    public final List<Integer> addedX = new ArrayList<>();
    public final List<Integer> addedY = new ArrayList<>();
    public List<String> anagrams = new ArrayList<>();
    public String word = new String();
    boolean doubleWord = false;
    boolean tripleWord = false;
    File dictionary = new File("src/resources/dictionar.txt");

    public Game() {
        addPlayer("John");
    }

    public Board getBoard() {
        return board;
    }

    LetterSack getLetterSack() {
        return letterSack;
    }

    void addLetterToBoard(Letter letter, int i, int j) {
        board.placeLetter(letter, i, j);
        pendingWord.add(letter);
        coordX.add(i);
        coordY.add(j);
        addedX.add(i);
        addedY.add(j);
    }

    void addMissing() {
        List<Integer> missing = new ArrayList<>();
        List<Letter> temp = new ArrayList<>();
        Integer line = 0;
        Integer col = 0;
        if (coordX.get(0) == coordX.get(1)) {
            if (board.board[coordX.get(0)][coordY.get(0)-1].content!=null) {
                missing.add(coordY.get(0) - 1);
            }
            if (board.board[coordX.get(0)][coordY.get(coordY.size() - 1) + 1].content!=null) {
                missing.add(coordY.get(coordY.size() - 1) + 1);
            }
            line = coordX.get(0);
            for (int i = 0; i < coordY.size() - 1; i++)
                if (coordY.get(i) != coordY.get(i + 1) - 1) {
                    missing.add(coordY.get(i) + 1);
                }
            for (int i = 0; i < missing.size(); i++) {
                coordY.add(missing.get(i));
                coordX.add(line);
                pendingWord.add(board.board[line][missing.get(i)].content);

//                System.out.println(missing);
            }
            List<Letter> sorted = new ArrayList(pendingWord);
            Collections.sort(sorted, Comparator.comparing(s->coordY.get(pendingWord.indexOf(s))));
            pendingWord = sorted;
        } else if (coordY.get(0) == coordY.get(1)) {
            if (board.board[coordX.get(0) - 1][coordY.get(0)].content!=null) {
                missing.add(coordX.get(0) - 1);
            }
            if (board.board[coordX.get(coordX.size()-1)+1][coordY.get(0)].content!=null) {
                missing.add(coordX.get(coordX.size()-1)+1);
            }
            col = coordY.get(0);
            for (int i = 0; i < coordX.size() - 1; i++)
                if (coordX.get(i) != coordX.get(i + 1) - 1) {
                    missing.add(coordX.get(i) + 1);
                }

            for (int i = 0; i < missing.size(); i++) {
                coordX.add(missing.get(i));
                coordY.add(col);
                pendingWord.add(board.board[missing.get(i)][col].content);
//                System.out.println(missing);
            }
            List<Letter> sorted = new ArrayList(pendingWord);
            Collections.sort(sorted, Comparator.comparing(s->coordX.get(pendingWord.indexOf(s))));
            pendingWord = sorted;
        }
    }

    public void addPlayer(String name) {
        Player player = new Player(name, this);
        players.add(player);
        currentPlayer = player;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Letter> getPendingWord() {
        return pendingWord;
    }

    public void overTurn(){
        pendingWord.clear();
        coordX.clear();
        coordY.clear();
        addedX.clear();
        addedY.clear();
        word = new String();
    }

    public Integer computeScoreOfWord() throws IOException {
        if (confirmWord()) {
            int wordScore = 0;
            for (int i = 0; i < pendingWord.size(); i++) {
                Tile currentTile = board.board[coordX.get(i)][coordY.get(i)];
                switch (currentTile.type) {
                    case "2L":
                        wordScore = wordScore + 2 * currentTile.content.value;
                        break;
                    case "3L":
                        wordScore = wordScore + 3 * currentTile.content.value;
                        break;
                    case "2W":
                        wordScore = wordScore + currentTile.content.value;
                        doubleWord = true;
                        break;
                    case "3W":
                        wordScore = wordScore + currentTile.content.value;
                        tripleWord = true;
                        break;
                    default:
                        wordScore = wordScore + currentTile.content.value;
                }
            }
            if (doubleWord)
                wordScore = wordScore * 2;
            if (tripleWord)
                wordScore = wordScore * 3;
            doubleWord = false;
            tripleWord = false;
            currentPlayer.refillAfterTurn();
            pendingWord.clear();
            coordX.clear();
            coordY.clear();
            addedX.clear();
            addedY.clear();
            word = new String();
            currentPlayer.score += wordScore;
            return wordScore;
        }
        word = new String();
        System.out.println("The word does not exist!");
        return 0;
    }

    public void transformPendingWord() {
        for (int i = 0; i < pendingWord.size(); i++)
            word = word + pendingWord.get(i).letterName;
    }
    //TODO: search more efficiently
    public Boolean checkWord(String word) throws IOException {

        FileReader fileReader = new FileReader(dictionary);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line= bufferedReader.readLine())!=null) {
            if (line.equals(word)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getAnagrams() {
        return anagrams;
    }

    public Boolean confirmWord() throws IOException {

        if (checkWord(word.toLowerCase())) {
            return true;
        }
        return false;
    }

    public void computeAnagrams(String prefix, String word) throws IOException {
        int n = word.length();
        if (n == 0) anagrams.add(prefix);
        else {
            for (int i = 0; i < n; i++)
                computeAnagrams(prefix + word.charAt(i), word.substring(0, i) + word.substring(i+1, n));
        }
    }

    public void checkAnagrams() throws IOException {
        for (int i = 0 ; i < anagrams.size(); i++)
            if (!checkWord(anagrams.get(i).toLowerCase())) {
                anagrams.remove(i);
            }
    }

    public void showAnagrams() {
        for (String anagram : anagrams)
            System.out.println(anagram);
    }

    public void removeWordFromBoard() {
        for (int i = 0; i < addedX.size(); i++)
            board.board[addedX.get(i)][addedY.get(i)].content = null;
    }

    // This was used as a test to simulate a turn

//    public void playTurn() throws IOException {
//        // we put tiles on the board until a button is pressed
//
//            Scanner in = new Scanner(System.in);
//            String command = null;
//            int letterOnHolder = 0;
//            while (true) {
//                System.out.println("This is your holder:");
//                System.out.println(currentPlayer.getHolder().currentLetters);
//                System.out.println("Please enter the index of the letter, the line and the column where you want to place it");
//                letterOnHolder = in.nextInt();
//                if (letterOnHolder == 404){
//                    break;
//                }
//                int posX = in.nextInt();
//                int posY = in.nextInt();
//                currentPlayer.putLetterInTile(currentPlayer.getHolder().currentLetters.get(letterOnHolder), posX, posY);
//            }
//        addMissing();
//        transformPendingWord();
//        System.out.print("This was his word: ");
//        System.out.println(pendingWord);
//        System.out.println(coordX + " " + coordY);
//        System.out.print("Added: " + addedX + " " + addedY);
//
//        if (!confirmWord()) {
//            for (int i = 0; i < addedX.size(); i++)
//                currentPlayer.getHolder().getCurrentLetters().add(board.board[addedX.get(i)][addedY.get(i)].content);
//            removeWordFromBoard();
//            System.out.println("The word does not exist!");
//        } else {
//            System.out.println("The score for the word:" + computeScoreOfWord());
//            currentPlayer.refillAfterTurn();
//        }
//        board.printBoardWithContent();
//
//        System.out.println(currentPlayer.getHolder().currentLetters);
//        overTurn();
//    }
}
