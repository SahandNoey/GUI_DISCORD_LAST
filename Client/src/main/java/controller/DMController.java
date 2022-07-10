package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import model.client.Client;

import java.io.IOException;

public class DMController {

    @FXML
    private VBox serversVBox;

    @FXML
    private Pane homeBtn;

    @FXML
    private Pane addServerBtn;

    @FXML
    private VBox accountAndDMVBox;

    @FXML
    private ImageView addDMBtn;

    @FXML
    private HBox dmEachFriendHBox;

    @FXML
    private Circle dmEachFriendCircle;

    @FXML
    private Circle dmEachFriendStatusCircle;

    @FXML
    private Label dmEachFriendNameLabel;

    @FXML
    private VBox msgAndSettingsVBox;

    @FXML
    private Pane friend1Pane;

    @FXML
    private Circle dmMsgImageCircle;

    @FXML
    private Circle dmFriendStatusCircle;

    @FXML
    private Label friendNameLabel;

    @FXML
    private Label friendStatusLabel;

    @FXML
    private VBox dmMessagesVBox;

    @FXML
    private Pane msgPane;

    @FXML
    private HBox msgHBox;

    @FXML
    private Circle msgImageCircle;

    @FXML
    private Label msgTxtField;

    @FXML
    private Pane friend1Pane11;

    @FXML
    private TextField dmTxtFld;

    @FXML
    private ImageView dmSendBtn;

    @FXML
    void addDMBtnClicked(MouseEvent event) {

    }

    @FXML
    void addServerClicked(MouseEvent event) {

    }

    @FXML
    void dmSendBtnClicked(MouseEvent event) {

    }

    @FXML
    void friendsClicked(MouseEvent event) {

    }

    @FXML
    void homeBtnClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void myAccountClicked(MouseEvent event) {

    }

    @FXML
    void onDMFriendClicked(MouseEvent event) {

    }

}
