package client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import server.Letter;

import java.util.List;

public class LetterPane extends Pane {
    public VBox root;
    public Button button1 = new Button();
    public Button button2 = new Button();
    public Button button3 = new Button();
    public Button button4 = new Button();
    public Button button5 = new Button();
    public Button button6 = new Button();
    public Button button7 = new Button();
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

//        button1.setPrefSize(30,30);
//        button2.setPrefSize(30,30);
//        button3.setPrefSize(30,30);
//        button4.setPrefSize(30,30);
//        button5.setPrefSize(30,30);
//        button6.setPrefSize(30,30);
//        button7.setPrefSize(30,30);

        button1.setGraphic(new ImageView(aLetter));
//        for (Letter letter : playerLetters) {
//            switch(letter.getLetterName()) {
//                case "A" -> button1.setGraphic(new ImageView(aLetter));
//                case "B" -> button1.setGraphic(new ImageView(bLetter));
//                case "C" -> button1.setGraphic(new ImageView(cLetter));
//                case "D" -> button1.setGraphic(new ImageView(dLetter));
//                case "E" -> button1.setGraphic(new ImageView(eLetter));
//                case "F" -> button1.setGraphic(new ImageView(fLetter));
//                case "G" -> button1.setGraphic(new ImageView(gLetter));
//                case "H" -> button1.setGraphic(new ImageView(hLetter));
//                case "I" -> button1.setGraphic(new ImageView(iLetter));
//                case "J" -> button1.setGraphic(new ImageView(jLetter));
//                case "L" -> button1.setGraphic(new ImageView(lLetter));
//                case "M" -> button1.setGraphic(new ImageView(mLetter));
//                case "N" -> button1.setGraphic(new ImageView(nLetter));
//                case "O" -> button1.setGraphic(new ImageView(oLetter));
//                case "P" -> button1.setGraphic(new ImageView(pLetter));
//                case "R" -> button1.setGraphic(new ImageView(rLetter));
//                case "S" -> button1.setGraphic(new ImageView(sLetter));
//                case "T" -> button1.setGraphic(new ImageView(tLetter));
//                case "U" -> button1.setGraphic(new ImageView(uLetter));
//                case "V" -> button1.setGraphic(new ImageView(vLetter));
//                case "X" -> button1.setGraphic(new ImageView(xLetter));
//                case "Z" -> button1.setGraphic(new ImageView(zLetter));
//                case "Joker" -> button1.setGraphic((new ImageView(joker)));
//            }
//        }
        root.getChildren().addAll(button1, button2, button3, button4, button5, button6, button7);
    }
}
