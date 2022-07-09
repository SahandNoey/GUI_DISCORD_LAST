package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
    private Accordion dmAccordion;

    @FXML
    private TitledPane dmTitlePane;

    @FXML
    private AnchorPane dmAnchorPane;

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
    private TextField addFriendusernameTxtFld;

    @FXML
    private Button sendReqAddFrndBtn;

    @FXML
    private Pane friend1Pane;

    @FXML
    private Pane friend1StautsPane;

    @FXML
    private Label friend1NameLabel;

    @FXML
    private Label friend1StatusLabel;

    @FXML
    private Button friend1SendMsgBtn;

    @FXML
    void addFriendClicked(MouseEvent event) {

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
    void dmAccordionClicked(MouseEvent event) {

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
    void onlineClicked(MouseEvent event) {

    }

    @FXML
    void pendingClicked(MouseEvent event) {

    }

    @FXML
    void sendRequestToAddFriendClicked(MouseEvent event) {

    }

    @FXML
    void sendMessageInFriendsListClicked(MouseEvent event) {

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
