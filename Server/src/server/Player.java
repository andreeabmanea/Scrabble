package server;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final Game game;
    private final String name;
    private final Integer score;
    private final LetterHolder holder;


    public Player(String name, Game game) {
        this.name = name;
        this.score = 0;
        this.game = game;
        holder = game.getLetterSack().getFirstDraw();
    }

    public LetterHolder getHolder() {
        return holder;
    }

    public void putLetterInTile(Letter letter, int i, int j) {
        if (holder.currentLetters.contains(letter)) {
            holder.currentLetters.remove(letter);
            game.addLetterToBoard(letter, i, j);
            game.getBoard().board[i][j].content = letter;

        }
    }

    public void refillAfterTurn() {
        while (holder.currentLetters.size() < 7) {
            holder.currentLetters.add(game.getLetterSack().drawLetter());
        }
    }

    public void shuffleHolder() {
        holder.currentLetters.clear();
        for (int i = 0; i < 7; i++)
            holder.currentLetters.add(game.getLetterSack().drawLetter());
    }
}
