package controller;

import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.client.Client;
import model.other.MemberInfo;
import model.other.ServerInfo;
import starter.ClientStarter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private MemberInfo me;

    @FXML
    private PasswordField currentPasswordTxtFld;

    @FXML
    private PasswordField newPasswordTxtFld;

    @FXML
    private PasswordField confirmPasswordTxtFld;

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
    private Label serverNameEmptyLabel;

    @FXML
    private Label noFieldCanBeEmptyLabel;

    @FXML
    private PasswordField currentEmailTxtFld;

    @FXML
    private PasswordField newEmailTxtFld;

    private PopupLoader addServerPopupLoader;
    private File serverPhoto;
    private PopupLoader changePasswordPopupLoader;
    private PopupLoader changeEmailPopupLoader;

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
    void addServerClicked(MouseEvent event) {
        if (this.addServerPopupLoader == null)
        {
            this.addServerPopupLoader = new PopupLoader(this, "/fxml/addServerPopup.fxml");
            addServerPopupLoader.popup(event);
        }else {
            addServerPopupLoader.close();
            addServerPopupLoader = null;
        }


    }

    @FXML
    void changeAvatarBtnClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select your profile photo");
        File file = fileChooser.showOpenDialog(new Stage());

        // backend ...
    }

    @FXML
    void cancelBtnInChangePasswordClicked(MouseEvent event) {
        changePasswordPopupLoader.close();
        changePasswordPopupLoader = null;
    }

    @FXML
    void doneBtnInChangePasswordClicked(MouseEvent event) {
        if (currentPasswordTxtFld.getText().equals("") || newPasswordTxtFld.getText().equals("")
                || confirmPasswordTxtFld.getText().equals(""))
        {
            noFieldCanBeEmptyLabel.setVisible(true);
        }else if (!currentPasswordTxtFld.getText().equals(me.getPassword()))
        {
            noFieldCanBeEmptyLabel.setText("Invalid Credentials");
            noFieldCanBeEmptyLabel.setVisible(true);
        }else if (confirmPasswordTxtFld.getText().equals(newPasswordTxtFld.getText())){
            changePasswordPopupLoader.close();
            changePasswordPopupLoader = null;



            // backend for changing password in server and local device


        }
    }

    @FXML
    void changePasswordBtnClicked(MouseEvent event) {
        if (changePasswordPopupLoader == null)
        {
            changePasswordPopupLoader = new PopupLoader(this, "/fxml/changePasswordPopup.fxml");
            changePasswordPopupLoader.popup();
        }else {
            changePasswordPopupLoader.close();
            changePasswordPopupLoader = null;
        }
    }

    @FXML
    void cancelBtnInChangeEmailClicked(MouseEvent event) {
        changeEmailPopupLoader.close();
        changeEmailPopupLoader = null;
    }

    @FXML
    void doneBtnInChangeEmailClicked(MouseEvent event) {
        if ((!currentEmailTxtFld.getText().equals(me.getEmail())) || currentEmailTxtFld.getText().equals("")
        || newEmailTxtFld.getText().equals(""))
        {
            noFieldCanBeEmptyLabel.setText("Invalid Credentials");
            noFieldCanBeEmptyLabel.setVisible(true);
        }else {
            changeEmailPopupLoader.close();
            changeEmailPopupLoader = null;

            // now backend for changing email



        }
    }

    @FXML
    void emailEditClicked(MouseEvent event) {
        if (changeEmailPopupLoader == null)
        {
            changeEmailPopupLoader = new PopupLoader(this, "/fxml/changeEmailPopup.fxml");
            changeEmailPopupLoader.popup();
        }else {
            changeEmailPopupLoader.close();
            changeEmailPopupLoader = null;
        }
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
        Client.logOut();
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
        addServerPopupLoader.close();
        addServerPopupLoader = null;
    }

    @FXML
    void createBtnClicked(MouseEvent event) throws IOException {
        if (serverNameTxtFld.getText().equals("")){
            serverNameEmptyLabel.setVisible(true);
        }else {
            addServerPopupLoader.close();
            addServerPopupLoader = null;
            // backend for creating a new server and also adding it to gui ...
            Client.createNewServer(serverPhoto, serverNameTxtFld.getText());
            serverPhoto = null;
        }
    }

    @FXML
    void uploadServerImageClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose server photo");
        File serverPhoto = fileChooser.showOpenDialog(new Stage());

        // now handling that the file must be photo and backend for creating server
        if(serverPhoto != null){
            if(serverPhoto.getName().endsWith(".jpg")){
                this.serverPhoto = serverPhoto;
                System.out.println("ff");
            }
        }

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
        if(serversVBox.getChildren().size() < 3) {
            showServersInMainMenuList(Client.getServersForMainMenu());
        }

        //Direct messages
        try {
            if(accountAndDMVBox.getChildren().size() < 5)
            showDMsInMainMenuList(Client.getFriendsWithDMForFriendsMenu());
        }catch (Exception e){
            e.printStackTrace();
        }


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

    public void showDMsInMainMenuList(ArrayList<MemberInfo> informations) throws IOException{
        for (MemberInfo information : informations){
            String name = information.getUserNameWithToken();
            String status = information.getStatus();
            Image profilePic = new Image("file:Client\\profilePics\\" + information.getPhotoName());
            Image statusPic = new Image("file:Client\\files\\statuspics\\" + status + ".png");

            HBox root;

            //root childs
            Pane pane1;
            Label nameLabel = new Label(name);
            nameLabel.setTextFill(Color.WHITE);
            nameLabel.setFont(new Font("System Bold", 16));

            //pane1 childs
            Circle profilePicCircle = new Circle();
            profilePicCircle.setFill(new ImagePattern(profilePic));
            profilePicCircle.setLayoutX(25);
            profilePicCircle.setLayoutY(25);
            profilePicCircle.setRadius(24);
            profilePicCircle.setStroke(Color.BLACK);
            profilePicCircle.setStrokeType(StrokeType.INSIDE);

            Circle statusPicCircle = new Circle();
            statusPicCircle.setFill(new ImagePattern(statusPic));
            statusPicCircle.setLayoutX(42);
            statusPicCircle.setLayoutY(43);
            statusPicCircle.setRadius(7);
            profilePicCircle.setStroke(Color.BLACK);
            profilePicCircle.setStrokeType(StrokeType.INSIDE);

            //done
            pane1 = new Pane(profilePicCircle, statusPicCircle);
            pane1.setStyle("-fx-background-color: #202225; -fx-background-radius: 100;");
            root = new HBox(pane1, nameLabel);
            root.setStyle("-fx-background-color: #202225;");
            root.setPadding(new Insets(10,10,10,10));
            root.setSpacing(15);
            root.setAlignment(Pos.CENTER_LEFT);

            root.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int id = Integer.parseInt(information.getUserNameWithToken().split("#")[1]);
                    MemberInfo me = Client.getMyMemberInfo();
                    MemberInfo friend = Client.getInfoOfToken(id);
                    HashMap<Integer,String > temp = new HashMap<>();
                    temp.put(Integer.parseInt(me.getUserNameWithToken().split("#")[1]), me.getUserNameWithToken().split("#")[0]);
                    temp.put(Integer.parseInt(friend.getUserNameWithToken().split("#")[1]), friend.getUserNameWithToken().split("#")[0]);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/friendDM.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    DMController controller = loader.getController();
                    controller.completeTokenToName(temp, me, friend);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Client.gotoDMWith(controller, id);
                            }catch (Exception e){

                                e.printStackTrace();
                            }
                        }
                    }).start();
                    Client.changeScene(new Scene(root));
                }
            });
            accountAndDMVBox.getChildren().add(root);
        }
    }

}


