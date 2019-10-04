package sample;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;

public class Controller {

    @FXML
    private GridPane gridPane;

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


    private Byte priorityMove = 1;
    private Byte bot = 2, player = 1;
    private String localUrl0, localUrlX, playerImg, botImage, strCheckEnd;
    private Byte[][] grid =
            {
                    {0, 0, 0},
                    {0, 0, 0},
                    {0, 0, 0}
            };
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

        setGridOnOff(true);

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
            return;
        } else {
            rbX.setDisable(true);
            rb0.setDisable(true);
            btPlay.setText("Заново");
            setGridOnOff(false);

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
        setGridOnOff(true);
        if (_strCheckEnd.equals("win_bot")) lLose.setVisible(true);
        else if (_strCheckEnd.equals("win_player")) lWin.setVisible(true);
        else lDraw.setVisible(true);
    }

    void clear() {
        setGridOnOff(true);
        btPlay.setText("Начать");
        rbX.setDisable(false);
        rb0.setDisable(false);
        priorityMove = 1;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = 0;
            }
        }
        bot = 2;
        player = 1;
        step = 0;
        setStyleOnAllButton("-fx-background-color: #fafafa;");
        playerImg = "";
        botImage = "";
        strCheckEnd = "";
        lWin.setVisible(false);
        lLose.setVisible(false);
        lDraw.setVisible(false);
    }

    void setStyleOnAllButton(String style) {
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            gridPane.getChildren().get(i).setStyle(style);
        }
    }

    void switchMove() {

        if (priorityMove == 1) priorityMove = 2;
        else priorityMove = 1;

    }

    void moveBot() {
        int winBot = checkPossibilityToWin(bot);
        int winPlayer = checkPossibilityToWin(player);

        if (winBot != -1 ) doMove((Button) gridPane.getChildren().get(winBot));
        else if (winPlayer != -1) doMove((Button) gridPane.getChildren().get(winPlayer));
        else {
            switch (step) {
                case 0:
                    if (checkAllZero()) {
                        doMove(bt5);
                        step = 1;
                    } else if (grid[1][1] == player) {
                        Button bt = getRandomButtonFromList(true);
                        doMove(bt);
                    } else {
                        doMove(bt5);
                        step = 1;
                    }
                    break;
                case 1:
                    makeTrap();
                    break;
            }
        }
    }

    void makeTrap() {

    }

    Button getRandomButtonFromList(boolean true_conor) {
        byte i = (byte) (Math.random() * 3);
        byte j = (byte) (Math.random() * 3);
        if (true_conor == true) {
            if (i != 1 && j != 1 && grid[i][j]==0) {
                return (Button) gridPane.getChildren().get(i * 3 + j);
            } else {
                //рекурсия если нет углов
                return getRandomButtonFromList(true);
            }
        } else {
            if ((i == 1 && j != 1 && grid[i][j]==0) || (i != 1 && j == 1 && grid[i][j]==0)) {
                return (Button) gridPane.getChildren().get(i * 3 + j);
            } else {
                return getRandomButtonFromList(false);
            }
        }

    }

    String checkEnd() {
        if (
                (grid[0][0] == player && grid[0][1] == player && grid[0][2] == player) ||
                        (grid[1][0] == player && grid[1][1] == player && grid[1][2] == player) ||
                        (grid[2][0] == player && grid[2][1] == player && grid[2][2] == player) ||
                        (grid[0][0] == player && grid[1][1] == player && grid[2][2] == player) ||
                        (grid[2][0] == player && grid[1][1] == player && grid[0][2] == player) ||
                        (grid[0][0] == player && grid[1][0] == player && grid[2][0] == player) ||
                        (grid[0][1] == player && grid[1][1] == player && grid[2][1] == player) ||
                        (grid[2][2] == player && grid[1][2] == player && grid[0][2] == player)) {
            return "win_player";
        } else if (
                (grid[0][0] == bot && grid[0][1] == bot && grid[0][2] == bot) ||
                        (grid[1][0] == bot && grid[1][1] == bot && grid[1][2] == bot) ||
                        (grid[2][0] == bot && grid[2][1] == bot && grid[2][2] == bot) ||
                        (grid[0][0] == bot && grid[1][1] == bot && grid[2][2] == bot) ||
                        (grid[2][0] == bot && grid[1][1] == bot && grid[0][2] == bot) ||
                        (grid[0][0] == bot && grid[1][0] == bot && grid[2][0] == bot) ||
                        (grid[0][1] == bot && grid[1][1] == bot && grid[2][1] == bot) ||
                        (grid[2][2] == bot && grid[1][2] == bot && grid[0][2] == bot)) {
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

    int checkPossibilityToWin(byte X_O) {
        for (byte i = 0; i < grid.length; i++) {
            for (byte j = 0; j < grid[i].length; j++) {
                if ((grid[i][j] == 0 && grid[i][(j + 1) % 3] == X_O && grid[i][(j + 2) % 3] == X_O) ||
                        (grid[i][j] == 0 && grid[(i + 1) % 3][j] == X_O && grid[(i + 2) % 3][j] == X_O) ||
                        (grid[i][j] == 0 && grid[(i + 1) % 3][(j + 1) % 3] == X_O && grid[(i + 2) % 3][(j + 2) % 3] == X_O)) {
                    return i * 3 + j;
                }
            }
        }
        return -1;
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
        if (strCheckEnd != "null") {
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
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            gridPane.getChildren().get(i).setDisable(status);
        }
    }
}

