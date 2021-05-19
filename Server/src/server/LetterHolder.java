package server;

import java.util.ArrayList;
import java.util.List;

public class LetterHolder {

    public final List<Letter> currentLetters = new ArrayList<>();
    private final LetterSack letterSack;

    LetterHolder(LetterSack letterSack) {
        this.letterSack = letterSack;
        for (int i = 0; i < 7; i++) {
            currentLetters.add(letterSack.drawLetter());
        }
    }
}
