package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.client.Client;
import model.other.MemberInfo;
import model.other.ServerInfo;
import starter.ClientStarter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private MemberInfo me;

    @FXML
    private Pane uploadServerImgPane;

    @FXML
    private TextField serverNameTxtFld;

    @FXML
    private TextField selectFriendTxtFld;

    @FXML
    private Button selectBtn;

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
    private Circle profilePhotoCircle;

    @FXML
    private Label userNameText;

    @FXML
    private Label statusLabel;

    @FXML
    private Circle myStatusCircle;

    @FXML
    private Label changeStatusBtn;

    @FXML
    private Button changeAvatarBtn;

    @FXML
    private Label userNameText2;

    @FXML
    private Label emailText;

    @FXML
    private CheckBox emailShowCheckBox;

    @FXML
    private Label phoneNumberText;

    @FXML
    private Button phoneNumberEditBtn;

    @FXML
    private Circle homeButtonCircle;

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



    @FXML
    void addServerClicked(MouseEvent event) throws IOException {
        Stage secondStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ClientStarter.class.getResource("/fxml/addServerPopUp.fxml"));
        Scene popUpScene = new Scene(fxmlLoader.load());
        secondStage.setScene(popUpScene);
        secondStage.show();
    }

    @FXML
    void changeAvatarBtnClicked(MouseEvent event) {

    }

    @FXML
    void changePasswordBtnClicked(MouseEvent event) {

    }

    @FXML
    void emailEditClicked(MouseEvent event) {

    }

    @FXML
    void friendsClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/friendsListPage.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void homeBtnClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void logOutBtnClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signIn.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void myAccountClicked(MouseEvent event) {

    }

    @FXML
    void phoneNumberEditClicked(MouseEvent event) {

    }

    @FXML
    void onDMFriendClicked(MouseEvent event){

    }

    @FXML
    void  addDMBtnClicked(MouseEvent event){

    }

    @FXML
    void backBtnClicked(MouseEvent event) {

    }

    @FXML
    void createBtnClicked(MouseEvent event) {

    }

    @FXML
    void uploadServerImageClicked(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButtonCircle.setFill(new ImagePattern(new Image("file:Client\\src\\main\\resources\\assets\\icons\\logo_white.png")));
        me = Client.getMyMemberInfo();
        //set profile pic
        Client.downloadProfilePicIfDontHave(me.getPhotoName());
        setProfilePic(me.getPhotoName());
        //set username
        userNameText.setText(me.getUserNameWithToken());
        userNameText2.setText(me.getUserNameWithToken());
        //set email
        String temp = me.getEmail().split("@")[0];
        StringBuilder temp2 = new StringBuilder();
        for(int i = 0; i < temp.length(); i++){
            temp2.append("*");
        }
        temp2.append(me.getEmail().split("@")[1]);
        emailText.setText(temp2.toString());
        //set phone number
        if(me.getPhoneNumber() != null){
            phoneNumberText.setText(me.getPhoneNumber());
        }
        else{
            phoneNumberText.setText("");
        }
        //set status
        if(me.getStatus().equals("Idle")){
            statusLabel.setText("Idle");
            statusLabel.setTextFill(Color.YELLOW);
        }
        else if(me.getStatus().equals("Invisible")){
            statusLabel.setText("Invisible");
            statusLabel.setTextFill(Color.GRAY);
        }
        else if(me.getStatus().equals("Do Not Disturb")){
            statusLabel.setText("Do Not Disturb");
            statusLabel.setTextFill(Color.RED);
        }

        //servers
        showServersInMainMenuList(Client.getServersForMainMenu());

    }
    @FXML
    void usernameEditClicked(MouseEvent event) {

    }
    @FXML
    void changeStatusClicked(MouseEvent event){

    }

    @FXML
    void selectBtnClicked(MouseEvent event) {

    }

    public void setProfilePic(String fileName){
        profilePhotoCircle.setFill(new ImagePattern(new Image("file:Client\\profilePics\\" + fileName)));
    }



    public void showServersInMainMenuList(ArrayList<ServerInfo> informations){
        for (ServerInfo information : informations){
            String name;
            String picName = information.getPicName();
            ImagePattern profilePic = new ImagePattern(new Image("file:Client\\serverPics\\" + picName));
            Pane root;


            //root childs
            Circle picCircle = new Circle();
            picCircle.setFill(profilePic);
            picCircle.setLayoutX(34);
            picCircle.setLayoutY(38);
            picCircle.setRadius(32);
            picCircle.setStroke(Color.BLACK);
            picCircle.setStrokeType(StrokeType.INSIDE);


            //done
            root = new Pane(picCircle);
            root.setPrefWidth(78);
            root.setPrefHeight(75);
            root.setStyle("-fx-background-color: #2f3136; -fx-background-radius: 100;");

            serversVBox.getChildren().add(root);
        }

    }

}
