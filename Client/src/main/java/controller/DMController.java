package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import model.client.Client;
import model.client.ClientOut;
import model.other.MemberInfo;
import model.other.Message;

import java.io.IOException;
import java.util.HashMap;

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

    private HashMap<Integer, String> tokenToName;
    private MemberInfo me;
    private MemberInfo friend;

    @FXML
    private Pane specialInviteMsgPane;

    @FXML
    private HBox specialInviteMsgHBox;

    @FXML
    private Circle specialInviteMsgImageCircle;

    @FXML
    private Label specialInviteMsgTxtField;

    @FXML
    private Label inviteServerNameTxtFld;

    @FXML
    void acceptBtnClicked(MouseEvent event) {

    }

    @FXML
    void declineBtnClicked(MouseEvent event) {

    }

    @FXML
    void addDMBtnClicked(MouseEvent event) {

    }

    @FXML
    void addServerClicked(MouseEvent event) {

    }

    @FXML
    void dmSendBtnClicked(MouseEvent event) {
        if(dmTxtFld.getText() != null) {
            if(!dmTxtFld.getText().equals("")) {
                ClientOut.sendCommand(dmTxtFld.getText());
                dmTxtFld.setText("");
            }
        }
    }

    @FXML
    void friendsClicked(MouseEvent event) {

    }

    @FXML
    void homeBtnClicked(MouseEvent event) throws IOException {
        ClientOut.sendCommand("%%!getOutOfChat");
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

    public void addMessage(Message m){
        MemberInfo memberInfo;
        if(m.getAuthorToken() == Integer.parseInt(me.getUserNameWithToken().split("#")[1])){
            memberInfo = me;
        }
        else{
            memberInfo = friend;
        }
        String text = m.getMessage();
        Image profilePic = new Image("file:Client\\profilePics\\" + memberInfo.getPhotoName());


        Pane root;

        //root childs
        HBox hBox1;

        //hbox1 childs
        Pane pane1;
        Label label1 = new Label(text);
        label1.setPrefHeight(22);
        label1.setPrefWidth(729);
        label1.setTextFill(Color.WHITE);
        label1.setWrapText(true);
        label1.setFont(new Font("Comic Sans MS", 15));

        //pane1 childs
        Circle profilePicCircle = new Circle();
        profilePicCircle.setLayoutX(20);
        profilePicCircle.setLayoutY(22);
        profilePicCircle.setRadius(21);
        profilePicCircle.setStroke(Color.BLACK);
        profilePicCircle.setStrokeType(StrokeType.INSIDE);
        profilePicCircle.setFill(new ImagePattern(profilePic));

        //done
        pane1 = new Pane(profilePicCircle);
        pane1.setStyle("-fx-background-radius: 100;");
        hBox1 = new HBox(pane1, label1);
        hBox1.setAlignment(Pos.CENTER_LEFT);
        hBox1.setSpacing(20);
        root = new Pane(hBox1);
        root.setStyle("-fx-background-color: #2f3136; -fx-background-radius: 10;");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dmMessagesVBox.getChildren().add(root);
            }
        });


    }

    public void completeTokenToName(HashMap<Integer, String > x, MemberInfo me, MemberInfo friend){
        tokenToName = x;
        this.me = me;
        this.friend = friend;
    }

}
