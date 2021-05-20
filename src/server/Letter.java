package server;

import java.io.Serializable;

public class Letter implements Serializable {

    public final String letterName;
    public final int value;

    public Letter(String letterName, int value) {
        this.letterName = letterName;
        this.value = value;
    }

    @Override
    public String toString() {
        return letterName;
    }
}
