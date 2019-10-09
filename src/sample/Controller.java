package sample;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

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
        File file0 = new File("C:/Project/TicTacToe/src/resources/0.jpg");
        File fileX = new File("C:/Project/TicTacToe/src/resources/X.png");
        localUrl0 = file0.toURI().toURL().toString();
        localUrlX = fileX.toURI().toURL().toString();

        rb0.setSelected(false);
        rbX.setSelected(true);

        rb0.setOnAction(event -> change0());
        rbX.setOnAction(event -> changeX());

        setGridOnOff(true);
        setGridDVisible(false);
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


    void checkTrap() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if ((grid[0][2] == player && grid[1][1] == player && grid[2][0] == bot) ||
                (grid[0][2] == bot && grid[1][1] == player && grid[2][0] == player)
        ) {
            arrayList.add(0);
            arrayList.add(8);
            doMove(getRandomButtonFromList(true, arrayList));
        } else if ((grid[0][0] == player && grid[1][1] == player && grid[2][2] == bot) ||
                (grid[0][0] == bot && grid[1][1] == player && grid[2][2] == player)) {
            arrayList.add(2);
            arrayList.add(6);
            doMove(getRandomButtonFromList(true, arrayList));
        }
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
            setGridDVisible(true);

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
        setGridDVisible(false);
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

        if (winBot != -1) doMove((Button) gridPane.getChildren().get(winBot));
        else if (winPlayer != -1) doMove((Button) gridPane.getChildren().get(winPlayer));
        else {
            switch (step) {
                case 0:
                    //если игрок выбрал О
                    if (checkAllZero()) {
                        doMove(bt5);
                        step = 1;
                    }
                    //если игрок Х поставил в центр, то ставить в угол
                    else if (grid[1][1] == player) {
                        Button bt = getRandomButtonFromList(true, null);
                        doMove(bt);
                        step = 2;
                    }
                    //если игрок Х поставил не в центр, то занять центр
                    else if (getLastSide() == null) {
                        doMove(bt5);
                        step = 4;
                    } else {
                        doMove(getRandomButtonFromList(true, getLastSide()));
                        step = 2;
                    }
                    break;
                case 1:
                    Button bt = makeTrap(player);
                    doMove(bt);
                    step = 2;
                    break;
                case 2:
                    if (grid[1][1] != 0) {
                        int lastPlaces = checkLastPlaces(bot);
                        if (lastPlaces != -1) {
                            doMove((Button) gridPane.getChildren().get(lastPlaces));
                        }
                    } else {
                        doMove(bt5);
                    }
                    break;
                case 3:
                    checkTrap();
                    step = 2;
                    break;
                case 4:
                    checkTrap();

                    doMove(getRandomButtonFromList(false, null));
                    step = 2;
                    break;

            }
        }
    }


    ArrayList getLastSide() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == player && !(i == 1 && j == 1) && (i * 3 + j) % 2 == 1) {

                    if (i == 0) {
                        arrayList.add(0);
                        arrayList.add(2);
                    } else if (i == 1 && j == 0) {
                        arrayList.add(0);
                        arrayList.add(6);
                    } else if (i == 1 && j == 2) {
                        arrayList.add(2);
                        arrayList.add(8);
                    } else {
                        arrayList.add(6);
                        arrayList.add(8);
                    }
                    return arrayList;
                }
            }
        }
        return null;
    }

    int checkLastPlaces(byte X_O) {

        ArrayList<Integer> arr = new ArrayList<Integer>();
        byte k = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if ((grid[i][j] == 0 && grid[i][(j + 1) % 3] == 0 && grid[i][(j + 2) % 3] == X_O) ||
                        (grid[i][j] == 0 && grid[(i + 1) % 3][j] == 0 && grid[(i + 2) % 3][j] == X_O) ||
                        (i == j && grid[i][j] == 0 && grid[(i + 1) % 3][(j + 1) % 3] == 0 && grid[(i + 2) % 3][(j + 2) % 3] == X_O) ||
                        (((i == 0 && j == 2) || (i == j) || (i == 2 && j == 0)) && grid[i][j] == 0 && grid[(i + 1) % 3][(j + 2) % 3] == 0 && grid[(i + 2) % 3][(j + 4) % 3] == X_O)) {
                    return i * 3 + j;
                } else if (grid[i][j] == 0) {
                    arr.add(i * 3 + j);
                    k++;
                }
            }
        }
        if (k != 0) {
            Button bt = getRandomButtonFromList(true, arr);
            doMove(bt);
        }
        return -1;
    }

    Button makeTrap(byte X_0) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == X_0 && (i * 3 + j) % 2 == 0) {
                    ArrayList<Integer> arrConnor = new ArrayList<>();
                    if (i == 0) arrConnor.add(((i + 2) % 3) * 3 + j);
                    else arrConnor.add(((i + 1) % 3) * 3 + j);
                    if (j == 0) arrConnor.add(((j + 2) % 3) + 3 * i);
                    else arrConnor.add(((j + 1) % 3) + 3 * i);
                    return getRandomButtonFromList(true, arrConnor);
                } else if (grid[i][j] == X_0) {
                    ArrayList<Integer> arrSide = new ArrayList<Integer>();
                    if (i == 0) {
                        arrSide.add(8);
                        arrSide.add(6);
                    } else if (i == 1 && j == 0) {
                        arrSide.add(2);
                        arrSide.add(8);
                    } else if (i == 1 && j == 2) {
                        arrSide.add(0);
                        arrSide.add(6);
                    } else {
                        arrSide.add(0);
                        arrSide.add(2);
                    }
                    return getRandomButtonFromList(true, arrSide);
                }
            }
        }
        return null;
    }

    Button getRandomButtonFromList(boolean true_conor, ArrayList<Integer> arrayList) {
        if (arrayList == null) {
            byte i = (byte) (Math.random() * 3);
            byte j = (byte) (Math.random() * 3);
            if (true_conor == true) {
                if (i != 1 && j != 1 && grid[i][j] == 0) {
                    return (Button) gridPane.getChildren().get(i * 3 + j);
                } else {
                    return getRandomButtonFromList(true, null);
                }
            } else {
                if ((i == 1 && j != 1 && grid[i][j] == 0) || (i != 1 && j == 1 && grid[i][j] == 0)) {
                    return (Button) gridPane.getChildren().get(i * 3 + j);
                } else {
                    return getRandomButtonFromList(false, null);
                }
            }
        } else {
            byte k;
            k = (byte) (Math.random() * arrayList.size());
            return (Button) gridPane.getChildren().get(arrayList.get(k));
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
                        (i == j && grid[i][j] == 0 && grid[(i + 1) % 3][(j + 1) % 3] == X_O && grid[(i + 2) % 3][(j + 2) % 3] == X_O) ||
                        (((i == 0 && j == 2) || (i == 1 && j == 1) || (i == 2 && j == 0)) && grid[i][j] == 0 && grid[(i + 1) % 3][(j + 2) % 3] == X_O && grid[(i + 2) % 3][(j + 4) % 3] == X_O)) {
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

    void setGridDVisible(Boolean status) {
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            gridPane.getChildren().get(i).setVisible(status);
        }
    }
}

