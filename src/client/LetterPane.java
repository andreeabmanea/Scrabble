package client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import server.Letter;

import java.util.ArrayList;
import java.util.List;

public class LetterPane extends Pane {
    public VBox root;
    List<Button> buttons;
    public Button button0 = new Button();
    public Button button1 = new Button();
    public Button button2 = new Button();
    public Button button3 = new Button();
    public Button button4 = new Button();
    public Button button5 = new Button();
    public Button button6 = new Button();
    public Image aLetter = new Image("resources_client/A_letter.png");
    public Image bLetter = new Image("resources_client/B_letter.png");
    public Image cLetter = new Image("resources_client/C_letter.png");
    public Image dLetter = new Image("resources_client/D_letter.png");
    public Image eLetter = new Image("resources_client/E_letter.png");
    public Image fLetter = new Image("resources_client/F_letter.png");
    public Image gLetter = new Image("resources_client/G_letter.png");
    public Image hLetter = new Image("resources_client/H_letter.png");
    public Image iLetter = new Image("resources_client/I_letter.png");
    public Image jLetter = new Image("resources_client/J_letter.png");
    public Image lLetter = new Image("resources_client/L_letter.png");
    public Image mLetter = new Image("resources_client/M_letter.png");
    public Image nLetter = new Image("resources_client/N_letter.png");
    public Image oLetter = new Image("resources_client/O_letter.png");
    public Image pLetter = new Image("resources_client/P_letter.png");
    public Image rLetter = new Image("resources_client/R_letter.png");
    public Image sLetter = new Image("resources_client/S_letter.png");
    public Image tLetter = new Image("resources_client/T_letter.png");
    public Image uLetter = new Image("resources_client/U_letter.png");
    public Image vLetter = new Image("resources_client/V_letter.png");
    public Image xLetter = new Image("resources_client/X_letter.png");
    public Image zLetter = new Image("resources_client/Z_letter.png");
    public Image joker = new Image("resources_client/JOKER_letter.png");

    public VBox getRoot() {
        return root;
    }

    public LetterPane(List<Letter> playerLetters) {
        root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20, 10, 20, 10));
        root.setSpacing(30);

        buttons = new ArrayList<>();
        buttons.add(button0);
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);

        int i = 0;
        for (Letter letter : playerLetters) {
            switch(letter.getLetterName()) {
                case "A" -> buttons.get(i).setGraphic(new ImageView(aLetter));
                case "B" -> buttons.get(i).setGraphic(new ImageView(bLetter));
                case "C" -> buttons.get(i).setGraphic(new ImageView(cLetter));
                case "D" -> buttons.get(i).setGraphic(new ImageView(dLetter));
                case "E" -> buttons.get(i).setGraphic(new ImageView(eLetter));
                case "F" -> buttons.get(i).setGraphic(new ImageView(fLetter));
                case "G" -> buttons.get(i).setGraphic(new ImageView(gLetter));
                case "H" -> buttons.get(i).setGraphic(new ImageView(hLetter));
                case "I" -> buttons.get(i).setGraphic(new ImageView(iLetter));
                case "J" -> buttons.get(i).setGraphic(new ImageView(jLetter));
                case "L" -> buttons.get(i).setGraphic(new ImageView(lLetter));
                case "M" -> buttons.get(i).setGraphic(new ImageView(mLetter));
                case "N" -> buttons.get(i).setGraphic(new ImageView(nLetter));
                case "O" -> buttons.get(i).setGraphic(new ImageView(oLetter));
                case "P" -> buttons.get(i).setGraphic(new ImageView(pLetter));
                case "R" -> buttons.get(i).setGraphic(new ImageView(rLetter));
                case "S" -> buttons.get(i).setGraphic(new ImageView(sLetter));
                case "T" -> buttons.get(i).setGraphic(new ImageView(tLetter));
                case "U" -> buttons.get(i).setGraphic(new ImageView(uLetter));
                case "V" -> buttons.get(i).setGraphic(new ImageView(vLetter));
                case "X" -> buttons.get(i).setGraphic(new ImageView(xLetter));
                case "Z" -> buttons.get(i).setGraphic(new ImageView(zLetter));
                case "@" -> buttons.get(i).setGraphic(new ImageView(joker));
            }
            i++;
        }




        root.getChildren().addAll(buttons.get(0), buttons.get(1), buttons.get(2), buttons.get(3), buttons.get(4), buttons.get(5), buttons.get(6));
    }


    public List<Button> getButtons() {
        return buttons;
    }
}
