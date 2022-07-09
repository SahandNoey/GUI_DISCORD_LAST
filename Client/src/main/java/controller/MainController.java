package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainController {

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
    private Button changeAvatarBtn;

    @FXML
    private CheckBox emailShowCheckBox;

    @FXML
    private Button phoneNumberEditBtn;

    @FXML
    private Label statusLabel;

    @FXML
    private Pane statusPane;

    @FXML
    private Label changeStatusBtn;

    @FXML
    void addServerClicked(MouseEvent event) {

    }

    @FXML
    void changeAvatarBtnClicked(MouseEvent event) {

    }

    @FXML
    void changePasswordBtnClicked(MouseEvent event) {

    }

    @FXML
    void dmAccordionClicked(MouseEvent event) {

    }

    @FXML
    void emailEditClicked(MouseEvent event) {

    }

    @FXML
    void friendsClicked(MouseEvent event) {

    }

    @FXML
    void homeBtnClicked(MouseEvent event) {

    }

    @FXML
    void logOutBtnClicked(MouseEvent event) {

    }

    @FXML
    void myAccountClicked(MouseEvent event) {

    }

    @FXML
    void phoneNumberEditClicked(MouseEvent event) {

    }

    @FXML
    void usernameEditClicked(MouseEvent event) {

    }

    @FXML
    void changeStatusClicked(MouseEvent event) {

    }

//    @Override
////    public void initialize(URL url, ResourceBundle resourceBundle) {
////
////        MemberInfo me = Client.getMyMemberInfo();
////        Client.downloadProfilePicIfDontHave(me.getPhotoName());
//////        setProfilePic(me.getPhotoName());
////    }

    //    @FXML
//    void friendsButtonClicked(MouseEvent event) throws IOException {
//        Client.friendsController = new FriendsController();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("addFriendspage.fxml"));
//        FriendsController friendsController = new FriendsController();
//        loader.setController(friendsController);
//        Parent root = loader.load();
//        Client.changeScene(new Scene(root));
//    }


//
//    public void setProfilePic(String fileName){
//        profilePicCircle.setFill(new ImagePattern(new Image("file:Client\\profilePics\\" + fileName)));
//    }

}
