package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LetterHolder implements Serializable {

    public final List<Letter> currentLetters = new ArrayList<>();
    private final LetterSack letterSack;

    LetterHolder(LetterSack letterSack) {
        this.letterSack = letterSack;
        for (int i = 0; i < 7; i++) {
            currentLetters.add(letterSack.drawLetter());
        }
    }

    public Letter takeLetter(int index) {

        Letter chosenLetter = currentLetters.get(index);
        currentLetters.remove(index);
        return chosenLetter;
    }


    public List<Letter> getCurrentLetters() {
        return currentLetters;
    }
}
