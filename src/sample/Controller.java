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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

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

    @FXML
    void initialize() throws MalformedURLException {
        File file0 = new File("C:/TicTacToe/src/resources/0.jpg");
        File fileX = new File("C:/TicTacToe/src/resources/X.png");
        String localUrl0 = file0.toURI().toURL().toString();
        String localUrlX = fileX.toURI().toURL().toString();
        Image image0 = new Image(localUrl0);

        rb0.setSelected(true);
        rbX.setSelected(false);

        rb0.setOnAction(event -> {
            rb0.setSelected(true);
            rbX.setSelected(false);
        });

        rbX.setOnAction(event -> {
            rbX.setSelected(true);
            rb0.setSelected(false);
        });

        bt7.setOnAction(event -> {
            if(rb0.isSelected() == true){
//                bt7.setStyle("-fx-background-image: url('" + localUrl0 + "'); " +
//                        "-fx-background-position: center center; " +
//                        "-fx-background-repeat: stretch;");
                bt7.setDisable(true);
                gpGrid.add(new ImageView(image0), 2, 2);
                gpGrid.get
            }
            else {
                bt7.setStyle("-fx-background-image: url('" + localUrlX + "'); " +
                        "-fx-background-position: center center; " +
                        "-fx-background-repeat: stretch;");
                bt7.setDisable(true);
            }


        });
    }
}
