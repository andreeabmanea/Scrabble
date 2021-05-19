package server;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> players = new ArrayList<>();
    private Player currentPlayer;
    public final LetterSack letterSack = new LetterSack();
    public final Board board = new Board(this);
    public final List<Letter> pendingWord = new ArrayList<>();
    public final List<Integer> coordX = new ArrayList<>();
    public final List<Integer> coordY = new ArrayList<>();
    boolean doubleWord = false;
    boolean tripleWord = false;
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

    public Integer computeScoreOfWord() {
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
        pendingWord.clear();
        coordX.clear();
        coordY.clear();
        currentPlayer.refillAfterTurn();
        return wordScore;
    }
}
