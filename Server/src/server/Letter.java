package server;

public class Letter {

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
