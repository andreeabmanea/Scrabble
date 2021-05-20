package server;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {
    private final List<Player> players = new ArrayList<>();
    private Integer turn;
    private Player currentPlayer;
    public final LetterSack letterSack = new LetterSack();
    public final Board board = new Board(this);
    public List<Letter> pendingWord = new ArrayList<>();
    public final List<Integer> coordX = new ArrayList<>();
    public final List<Integer> coordY = new ArrayList<>();
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
    }

    void addMissing() {
        List<Integer> missing = new ArrayList<>();
        List<Letter> temp = new ArrayList<>();
        Integer line = 0;
        Integer col = 0;
        if (coordX.get(0) == coordX.get(1)) {
            line = coordX.get(0);
            for (int i = 0; i < coordY.size() - 1; i++)
                if (coordY.get(i) != coordY.get(i + 1) - 1) {
                    missing.add(coordY.get(i) + 1);
                }

            for (int i = 0; i < missing.size(); i++) {
                coordY.add(missing.get(i));
                coordX.add(line);
                pendingWord.add(board.board[line][missing.get(i)].content);

                System.out.println(missing);
            }

            for (int i = 0; i < coordY.size(); i++)
                temp.add(pendingWord.get(i));
            System.out.println(temp);
            Collections.sort(coordY);
            pendingWord = temp;
        } else if (coordY.get(0) == coordY.get(1)) {
            col = coordY.get(0);
            for (int i = 0; i < coordX.size() - 1; i++)
                if (coordX.get(i) != coordX.get(i + 1) - 1) {
                    missing.add(coordX.get(i) + 1);
                }

            for (int i = 0; i < missing.size(); i++) {
                coordX.add(missing.get(i));
                coordY.add(col);
                pendingWord.add(board.board[missing.get(i)][col].content);
                System.out.println(missing);
            }
            temp = new ArrayList<>();
            for (int i = 0; i < coordX.size(); i++)
                temp.add(pendingWord.get(i));
            System.out.println("Temp" + temp);
            Collections.sort(coordX);
            pendingWord = temp;
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
    }

    public Integer computeScoreOfWord() throws IOException {

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
                default: wordScore = wordScore + currentTile.content.value;
            }
        }
        if (doubleWord)
            wordScore = wordScore * 2;
        if (tripleWord)
            wordScore = wordScore * 3;
        doubleWord = false;
        tripleWord = false;
        confirmWord();
        //word = new String();

        currentPlayer.refillAfterTurn();
        return wordScore;
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

    public void confirmWord() throws IOException {

        transformPendingWord();
        if (checkWord(word)) {
            System.out.println("Confirmed");
        }
    }

    public void computeAnagrams(String prefix, String word) throws IOException {
        int n = word.length();
        if (n == 0) anagrams.add(prefix);
        else {
            for (int i = 0; i < n; i++)
                computeAnagrams(prefix + word.charAt(i), word.substring(0, i) + word.substring(i+1, n));
        }
    }

    public void showAnagrams() {
        for (String anagram : anagrams)
            System.out.println(anagram);
    }
}
