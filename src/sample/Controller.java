package sample;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private GridPane gpGrid;

    @FXML
    private URL location;

    @FXML
    private Button bt7;

    @FXML
    private Button bt8;

    @FXML
    private Button bt9;

    @FXML
    private Button bt4;

    @FXML
    private Button bt5;

    @FXML
    private Button bt6;

    @FXML
    private Button bt1;

    @FXML
    private Button bt2;

    @FXML
    private Button bt3;

    @FXML
    private Button btPlay;

    @FXML
    private RadioButton rbX;

    @FXML
    private RadioButton rb0;

    @FXML
    private Label lWin;

    @FXML
    private Label lDraw;

    @FXML
    private Label lLose;

    @FXML
    private Group gRb;

    private Byte priorityMove = 1;
    private Byte bot = 2, player = 1;
    private String localUrl0, localUrlX, playerImg, botImage, strCheckEnd;
    private Byte[][] grid =
            {
                    {0, 0, 0},
                    {0, 0, 0},
                    {0, 0, 0}
            };

    private String[] sides = new String[]{"bt2", "bt4", "bt6", "bt8"};
    private String[] conors = new String[]{"bt1", "bt3", "bt7", "bt9"};
    private Byte step = 0;

    @FXML
    void initialize() throws MalformedURLException {
        File file0 = new File("C:/TicTacToe/src/resources/0.jpg");
        File fileX = new File("C:/TicTacToe/src/resources/X.png");
        localUrl0 = file0.toURI().toURL().toString();
        localUrlX = fileX.toURI().toURL().toString();


        rb0.setSelected(false);
        rbX.setSelected(true);

        rb0.setOnAction(event -> change0());
        rbX.setOnAction(event -> changeX());

        setGridOnOff(false);

        bt1.setOnAction(event -> doMove(bt1));
        bt2.setOnAction(event -> doMove(bt2));
        bt3.setOnAction(event -> doMove(bt3));
        bt4.setOnAction(event -> doMove(bt4));
        bt5.setOnAction(event -> doMove(bt5));
        bt6.setOnAction(event -> doMove(bt6));
        bt7.setOnAction(event -> doMove(bt7));
        bt8.setOnAction(event -> doMove(bt8));
        bt9.setOnAction(event -> doMove(bt9));

        btPlay.setOnAction(event -> start());
    }

    void start() {
        if (btPlay.getText().equals("Заново")) {
            clear();
        } else {
            rbX.setDisable(true);
            rb0.setDisable(true);
            btPlay.setText("Заново");
            setGridOnOff(true);

        }
        if (rb0.isSelected() == true) {
            botImage = localUrlX;
            playerImg = localUrl0;
            priorityMove = 2;
            moveBot();
        } else {
            botImage = localUrl0;
            playerImg = localUrlX;
        }

    }

    void gameEnd(String _strCheckEnd) {
        setGridOnOff(false);

        if (_strCheckEnd.equals("win_bot")) lLose.setVisible(true);
        else if (_strCheckEnd.equals("win_player")) lWin.setVisible(true);
        else lDraw.setVisible(true);

    }

    void clear() {
        setGridOnOff(false);
        btPlay.setText("Начать");
        rbX.setVisible(true);
        rb0.setVisible(true);
        priorityMove = 1;
        Byte[][] grid =
                {
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}
                };
        bt1.setStyle("-fx-background-color: #fafafa;");
        bt1.setStyle("-fx-background-color: #fafafa;");
        bt2.setStyle("-fx-background-color: #fafafa;");
        bt3.setStyle("-fx-background-color: #fafafa;");
        bt4.setStyle("-fx-background-color: #fafafa;");
        bt5.setStyle("-fx-background-color: #fafafa;");
        bt6.setStyle("-fx-background-color: #fafafa;");
        bt7.setStyle("-fx-background-color: #fafafa;");
        bt8.setStyle("-fx-background-color: #fafafa;");
        bt9.setStyle("-fx-background-color: #fafafa;");
    }

    void switchMove() {

            if (priorityMove == 1) priorityMove = 2;
            else priorityMove = 1;

    }

    void moveBot() {
        switch (step) {

            case 0:
                if (checkAllZero()) {
                    doMove(bt5);

                } else if (grid[1][1] == player) {

                    doMove(bt7);

                } else {
                    doMove(bt5);

                }
                step++;
                break;
            case 1:

                break;
        }

    }

    void makeTrap(){
        //вилка
    }

    Button getRandomButtonFromList(Button[] buttonList) {
        byte index = (byte) (Math.random() * 4);
        return buttonList[index];
    }

    String checkEnd() {
        if (
                (grid[0][0] == player && grid[0][1] == player && grid[0][2] == player) ||
                        (grid[1][0] == player && grid[1][1] == player && grid[1][2] == player) ||
                        (grid[2][0] == player && grid[2][1] == player && grid[2][2] == player) ||
                        (grid[0][0] == player && grid[1][1] == player && grid[2][2] == player) ||
                        (grid[2][0] == player && grid[1][1] == player && grid[0][2] == player)) {
            return "win_player";
        } else if (
                (grid[0][0] == bot && grid[0][1] == bot && grid[0][2] == bot) ||
                        (grid[1][0] == bot && grid[1][1] == bot && grid[1][2] == bot) ||
                        (grid[2][0] == bot && grid[2][1] == bot && grid[2][2] == bot) ||
                        (grid[0][0] == bot && grid[1][1] == bot && grid[2][2] == bot) ||
                        (grid[2][0] == bot && grid[1][1] == bot && grid[0][2] == bot)) {
            return "win_bot";
        } else if (!checkAnyZero()) return "draw";
        else return "null";
    }

    boolean checkAnyZero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == 0) return true;
            }
        }
        return false;
    }

    boolean checkAllZero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] != 0) return false;
            }
        }
        return true;
    }

    void checkPossibilityToWin(int X_O) {
        switch (X_O) {
            case 0:
        }
    }

    void change0() {
        rb0.setSelected(true);
        rbX.setSelected(false);
    }

    void changeX() {
        rbX.setSelected(true);
        rb0.setSelected(false);
    }

    void doMove(Button bt) {

        if (priorityMove == 1) {
            bt.setStyle("-fx-background-image: url('" + playerImg + "'); " +
                    "-fx-background-position: center center; " +
                    "-fx-background-repeat: stretch;");
            bt.setDisable(true);
            setGridPosition(bt, player);
            switchMove();
            moveBot();
        } else {
            bt.setStyle("-fx-background-image: url('" + botImage + "'); " +
                    "-fx-background-position: center center; " +
                    "-fx-background-repeat: stretch;");
            bt.setDisable(true);
            setGridPosition(bt, bot);
            switchMove();
        }
        strCheckEnd = checkEnd();
        if (strCheckEnd!= "null") {
            gameEnd(strCheckEnd);
        }

    }


    void setGridPosition(Button bt, Byte X_0) {
        if (bt.equals(bt1)) {
            grid[2][0] = X_0;
        } else if (bt.equals(bt2)) {
            grid[2][1] = X_0;
        } else if (bt.equals(bt3)) {
            grid[2][2] = X_0;
        } else if (bt.equals(bt4)) {
            grid[1][0] = X_0;
        } else if (bt.equals(bt5)) {
            grid[1][1] = X_0;
        } else if (bt.equals(bt6)) {
            grid[1][2] = X_0;
        } else if (bt.equals(bt7)) {
            grid[0][0] = X_0;
        } else if (bt.equals(bt8)) {
            grid[0][1] = X_0;
        } else if (bt.equals(bt9)) {
            grid[0][2] = X_0;
        }
    }

    void setGridOnOff(Boolean status) {
        bt1.setVisible(status);
        bt2.setVisible(status);
        bt3.setVisible(status);
        bt4.setVisible(status);
        bt5.setVisible(status);
        bt6.setVisible(status);
        bt7.setVisible(status);
        bt8.setVisible(status);
        bt9.setVisible(status);
    }
}

