package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.client.Client;

import java.io.IOException;

public class FriendsController {

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
    private Label onlineBtn;

    @FXML
    private Label allBtn;

    @FXML
    private Label pendingBtn;

    @FXML
    private Label blockedBtn;

    @FXML
    private Label addFriendBtn;

    @FXML
    private Pane friend1Pane;

    @FXML
    private Circle friendImageCircle;

    @FXML
    private Circle friendStautsCircle;

    @FXML
    private Label friendNameLabel;

    @FXML
    private Label friendStatusLabel;

    @FXML
    private Button friend1SendMsgBtn;

    @FXML
    private ImageView threeDotsForFriend;


    @FXML
    private Label removeFriendBtn;

    @FXML
    private Label blockFriendBtn;

    @FXML
    private TextField addFriendusernameTxtFld;

    @FXML
    private Button sendReqAddFrndBtn;

    @FXML
    private Label sendFriendRequestResultTxt;

    @FXML
    void addDMBtnClicked(MouseEvent event) {

    }

    @FXML
    void addFriendClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addFriendspage.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void addServerClicked(MouseEvent event) {

    }

    @FXML
    void allClicked(MouseEvent event) {

    }

    @FXML
    void blockedClicked(MouseEvent event) {

    }

    @FXML
    void friendsClicked(MouseEvent event) {

    }

    @FXML
    void homeBtnClicked(MouseEvent event) {

    }

    @FXML
    void myAccountClicked(MouseEvent event) {

    }

    @FXML
    void onDMFriendClicked(MouseEvent event) {

    }

    @FXML
    void onlineClicked(MouseEvent event) {

    }

    @FXML
    void pendingClicked(MouseEvent event) {

    }

    @FXML
    void sendRequestToAddFriendClicked(MouseEvent event) {
        if (addFriendusernameTxtFld.getText() != null) {
            if (!addFriendusernameTxtFld.equals("")) {
                String res = Client.checkIfCanSendFriendRequestTo(addFriendusernameTxtFld.getText());
                if (res.equals("yes")) {
                    sendFriendRequestResultTxt.setText("friend request sent.");
                    sendFriendRequestResultTxt.setTextFill(Color.GREEN);
                    sendFriendRequestResultTxt.setOpacity(1);
                } else {
                    sendFriendRequestResultTxt.setText(res);
                    sendFriendRequestResultTxt.setTextFill(Color.RED);
                    sendFriendRequestResultTxt.setOpacity(1);
                }
            }
        }
    }

    @FXML
    void blockFriendClicked(MouseEvent event) {

    }

    @FXML
    void removeFriendClicked(MouseEvent event) {

    }

    @FXML
    void sendMessageInFriendsListClicked(MouseEvent event) {

    }

    @FXML
    void onFriendThreeDotsClicked(MouseEvent event) {

    }


    //    @FXML
//    VBox mainVbox;
//    @FXML
//    TextField addFriendTextField;
//    @FXML
//    Text resultText;
//
//
//    @FXML
//    public void addFriendButtonClicked(){
//        if(addFriendTextField.getText() != null){
//            if(!addFriendTextField.getText().equals("")){
//                String res = Client.checkIfCanSendFriendRequestTo(addFriendTextField.getText());
//                if(res.equals("yes")){
//                    resultText.setText("friend request sent.");
//                    resultText.setFill(Color.GREEN);
//                    resultText.setOpacity(1);
//                }
//                else{
//                    resultText.setText(res);
//                    resultText.setFill(Color.RED);
//                    resultText.setOpacity(1);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        ArrayList<MemberInfo> infos = Client.getFriendsForFriendsMenu();
//        for (MemberInfo info : infos){
//            mainVbox.getChildren().add(new HBox(new Text(info.getUserNameWithToken())));
//        }
//        infos = Client.getFriendRequestForFriendsMenu();
//        for (MemberInfo info : infos){
//            mainVbox.getChildren().add(new HBox(new Text(info.getUserNameWithToken())));
//        }
//    }

}
