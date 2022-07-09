package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.client.Client;
import model.other.MemberInfo;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    MemberInfo me;

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
    private Circle profilePhotoCircle;

    @FXML
    private Label userNameText;

    @FXML
    private Label userNameText2;

    @FXML
    private Label phoneNumberText;

    @FXML
    private Label emailText;

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
    void emailShowCheckBoxClicked(ActionEvent event){
        if(emailShowCheckBox.isSelected()){
            emailText.setText(me.getEmail());
        }
        else {
            String temp = me.getEmail().split("@")[0];
            StringBuilder temp2 = new StringBuilder();
            for(int i = 0; i < temp.length(); i++){
                temp2.append("*");
            }
            temp2.append(me.getEmail().split("@")[1]);
            emailText.setText(temp2.toString());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        me = Client.getMyMemberInfo();
        Client.downloadProfilePicIfDontHave(me.getPhotoName());
        setProfilePic(me.getPhotoName());
        userNameText.setText(me.getUserNameWithToken());
        userNameText2.setText(me.getUserNameWithToken());
        String temp = me.getEmail().split("@")[0];
        StringBuilder temp2 = new StringBuilder();
        for(int i = 0; i < temp.length(); i++){
            temp2.append("*");
        }
        temp2.append(me.getEmail().split("@")[1]);
        emailText.setText(temp2.toString());
        if(me.getPhoneNumber() != null){
            phoneNumberText.setText(me.getPhoneNumber());
        }
        else{
            phoneNumberText.setText("");
        }

    }
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
    public void setProfilePic(String fileName){
        profilePhotoCircle.setFill(new ImagePattern(new Image("file:Client\\profilePics\\" + fileName)));
    }

}
