package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LetterSack {

    public final List<Letter> letterSack = new ArrayList<>();

    //the initial letter bag
    public LetterSack() {
        try (FileReader fileReader = new FileReader("src/resources/letterConfiguration.csv");
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] letterInfo = line.split(",");
                    int count = Integer.parseInt(letterInfo[1]);
                    for (int i = 0; i < count; i++) {
                        String letter = letterInfo[0];
                        int value = Integer.parseInt(letterInfo[2]);
                        letterSack.add(new Letter(letter, value));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Letter drawLetter() {

        Random rand = new Random();
        int available = letterSack.size();
        int index = rand.nextInt(available);
        letterSack.remove(index);
        return letterSack.get(index);

    }

    LetterHolder getFirstDraw() {
        return new LetterHolder(this);
    }

}
