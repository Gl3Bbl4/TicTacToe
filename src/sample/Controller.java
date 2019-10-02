package sample;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

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

    private Byte priorityMove=1;
    private Byte X = 2, O = 1;
    private String localUrl0, localUrlX;
    private Byte[][] grid =
            {
                    {0, 0, 0},
                    {0, 0, 0},
                    {0, 0, 0}
            };

    @FXML
    void initialize() throws MalformedURLException {
        File file0 = new File("C:/Project/TicTacToe/src/resources/0.jpg");
        File fileX = new File("C:/Project/TicTacToe/src/resources/X.png");
        localUrl0 = file0.toURI().toURL().toString();
        localUrlX = fileX.toURI().toURL().toString();

        rb0.setSelected(true);
        rbX.setSelected(false);

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
        }
        rbX.setDisable(true);
        rb0.setDisable(true);
        btPlay.setText("Заново");
        setGridOnOff(true);
        if (rbX.isSelected() == true) {
            priorityMove=2;
            moveBot();
        }
        while (checkEnd()=="null"){
            if (priorityMove == 1){
                priorityMove=2;
                moveBot();
            }
        }
        setGridOnOff(false);
        if(rbX.isSelected() == true) {
            if (checkEnd().equals("win_X")) lWin.setVisible(true);
            else if(checkEnd().equals("win_O")) lLose.setVisible(true);
            else lDraw.setVisible(true);
        }
    }

    void clear(){
        setGridOnOff(true);
        btPlay.setText("Начать");
        rbX.setVisible(true);
        rb0.setVisible(true);
    }

    void switchMove() {
        if(priorityMove == 1) priorityMove=2;
        else priorityMove = 1;
    }

    void moveBot() {
        switchMove();
    }

    String checkEnd() {
        if(
                (grid[0][0]==O && grid[0][1]==O && grid[0][2]==O) ||
                (grid[1][0]==O && grid[1][1]==O && grid[1][2]==O) ||
                (grid[2][0]==O && grid[2][1]==O && grid[2][2]==O) ||
                (grid[0][0]==O && grid[1][1]==O && grid[2][2]==O) ||
                (grid[2][0]==O && grid[1][1]==O && grid[0][2]==O))
        {
            return "win_O";
        } else if (
                        (grid[0][0]==X && grid[0][1]==X && grid[0][2]==X) ||
                        (grid[1][0]==X && grid[1][1]==X && grid[1][2]==X) ||
                        (grid[2][0]==X && grid[2][1]==X && grid[2][2]==X) ||
                        (grid[0][0]==X && grid[1][1]==X && grid[2][2]==X) ||
                        (grid[2][0]==X && grid[1][1]==X && grid[0][2]==X))
        {
            return "win_X";
        } else if (!checkAnyZero()) return "draw";
        else return "null";
    }

    boolean checkAnyZero(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j]==0) return true;
            }
        } return false;
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
        if (priorityMove==1){
        if (rb0.isSelected() == true) {
            bt.setStyle("-fx-background-image: url('" + localUrl0 + "'); " +
                    "-fx-background-position: center center; " +
                    "-fx-background-repeat: stretch;");
            bt.setDisable(true);
            setGridPosition(bt, O);
        } else {
            bt.setStyle("-fx-background-image: url('" + localUrlX + "'); " +
                    "-fx-background-position: center center; " +
                    "-fx-background-repeat: stretch;");
            bt.setDisable(true);
            setGridPosition(bt, X);
        }
        switchMove();}
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

