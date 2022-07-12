package controller;

//import com.jfoenix.controls.JFXTextField;
//import com.jfoenix.controls.JFXToggleButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.client.Client;
import model.client.ClientOut;
import model.other.AdminInfo;
import model.other.MemberInfo;
import model.other.ServerInfo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class ServerAndChannelController implements Initializable {

//    @FXML
//    private JFXTextField roleTxtFld;
//
//    @FXML
//    private JFXToggleButton createChannelToggleBtn;
//
//    @FXML
//    private JFXToggleButton removeChannelToggleBtn;
//
//    @FXML
//    private JFXToggleButton kickMemberToggleBtn;
//
//    @FXML
//    private JFXToggleButton limitChannelMembersToggleBtn;
//
//    @FXML
//    private JFXToggleButton limitMemberAccessToggleBtn;
//
//    @FXML
//    private JFXToggleButton changeServerNameToggleBtn;
//
//    @FXML
//    private JFXToggleButton accessHistoryToggleBtn;
//
//    @FXML
//    private JFXToggleButton pinMessageToggleBtn;

    @FXML
    private Label infoUsernameLabel;

    @FXML
    private Circle infoStatusCircle;

    @FXML
    private Label infoStatusLabel;

    @FXML
    private Label infoStatusContentLabel;

    @FXML
    private Label infoRoleLabel;

    @FXML
    private VBox serversVBox;

    @FXML
    private Pane homeBtn;

    @FXML
    private Pane addServerBtn;

    @FXML
    private VBox accountAndDMVBox;

    @FXML
    private VBox textChannelsVBox;

    @FXML
    private VBox voiceChannelsVBox;

    @FXML
    private HBox eachChannelHBox;

    @FXML
    private ImageView channelTypeImgView;

    @FXML
    private Label channelName;

    @FXML
    private Circle serverImgCircleInSettings;

    @FXML
    private TextField newServerNameInSettings;

    @FXML
    private VBox msgAndSettingsVBox;

    @FXML
    private ImageView onTopChannelTypeImgView;

    @FXML
    private Label onTopChannelName;

    @FXML
    private VBox onTopChannelVBox;

    @FXML
    private HBox eachChannelMsgHBox;

    @FXML
    private Circle channelMsgSenderProfileCircle;

    @FXML
    private Label channelMsgTextField;

    @FXML
    private TextField channelSendMsgTxtFld;

    @FXML
    private TextField searchMemberTxtFld;

    @FXML
    private VBox allMemberTypesVBox;

    @FXML
    private VBox eachStatusOnlineMembersOfChannelVBox;

    @FXML
    private VBox eachStatusOfflineMembersOfChannelVBox;

    @FXML
    private Label eachStatusTypeAndNumLabel;

    @FXML
    private HBox eachMemberOfChannelHBox;

    @FXML
    private Circle eachMemberOfChannelProfileCircle;

    @FXML
    private Circle eachMemberOfChannelStatusCircle;

    @FXML
    private Label eachMemberOfChannelNameLabel;

    @FXML
    private RadioButton textChannelRadioBtn;

    @FXML
    private ToggleGroup group;

    @FXML
    private RadioButton voiceChannelRadioBtn;

    @FXML
    private TextField channelNameTxtFld;

    @FXML
    private Pane kickBtnPopup;

    @FXML
    private Pane giveRoleBtnPopup;

    @FXML
    private Pane infoBtnPopup;

    @FXML
    private VBox eachChannelMsgVBox;

    @FXML
    private ImageView pinImgViewInReactionsPopup;

    @FXML
    private HBox emojiHBox;

    @FXML
    private ImageView emojiImgView;

    @FXML
    private Label emojiNumberLabel;

    @FXML
    private HBox specialPinnedMsgHBox;

    @FXML
    private Circle specialPinnedMsgCircle;

    @FXML
    private Label specialPinnedMsgLabel;

    private ArrayList<String> textChannels;
    private ArrayList<String> voiceChannels;
    private ArrayList<MemberInfo> members;
    private int id;
    private File pic;
    private ArrayList<ServerInfo> allServers;
    private ArrayList<AdminInfo> admins;


    @FXML
    void onMsgClicked(MouseEvent event) {

    }

    @FXML
    void addServerClicked(MouseEvent event) {

    }

    @FXML
    void channelSendMsgClicked(MouseEvent event) {
        if (!channelSendMsgTxtFld.getText().equals("")){
            FXMLLoader msgLoader = new FXMLLoader();
            msgLoader.setLocation(MessageViewCreator.class.getResource("/fxml/msgVBox.fxml"));
            try {
                msgLoader.load();
            }catch (Exception e){
                e.printStackTrace();
            }
            MessageViewCreator msgController = msgLoader.getController();
            msgController.setInfo(MainController.getMe(), channelSendMsgTxtFld.getText());
            onTopChannelVBox.getChildren().add(msgLoader.getRoot());
        }
    }

    @FXML
    void createChannelClicked(MouseEvent event) {

    }

    @FXML
    void homeBtnClicked(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/fxml/mainMenu.fxml"));

    }

    @FXML
    void invitePeopleClicked(MouseEvent event) {

    }

    @FXML
    void onChannelClicked(MouseEvent event) {

    }

    @FXML
    void onDMFriendClicked(MouseEvent event) {

    }

    @FXML
    void searchBtnClicked(MouseEvent event) {

    }

    @FXML
    void serverSettingsClicked(MouseEvent event) throws IOException, InterruptedException {
        gotoServerMenu("serverSettings");
    }

    @FXML
    void onMemberClicked(MouseEvent event){

    }

    @FXML
    void onKickClicked(MouseEvent event) {

    }

    @FXML
    void onCancelInSettingsClicked(MouseEvent event) throws InterruptedException {
        gotoServerMenu("serverMainPage");
    }

    @FXML
    void onDoneInSettingsClicked(MouseEvent event) throws IOException, InterruptedException {
        String name = newServerNameInSettings.getText();
        if(name != null){
            if(!name.equals("")){
                Client.changeServerName(name);
                TimeUnit.MILLISECONDS.sleep(50);
            }
        }
        if(pic != null){
            Client.changeServerPic(pic);
            TimeUnit.MILLISECONDS.sleep(70);
        }
    }

    @FXML
    void onUploadImgInSettingsClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null){
            if(file.getName().endsWith(".jpg")){
                this.pic = file;
                serverImgCircleInSettings.setFill(new ImagePattern(new Image("file:" + file.getAbsolutePath())));
            }
        }
    }

    @FXML
    void cancelBtnInCreateChannelPopupClicked(MouseEvent event) throws IOException {
    }

    @FXML
    void createChannelBtnInPopupClicked(MouseEvent event) {

    }

    @FXML
    void onGiveRoleClicked(MouseEvent event) {

    }

    @FXML
    void onInfoClicked(MouseEvent event) {

    }

    @FXML
    void infoCloseBtnClicked(MouseEvent event) {

    }

    @FXML
    void cancelInGiveRolePermissionClicked(MouseEvent event) {

    }

    @FXML
    void doneInGiveRolePermissionClicked(MouseEvent event) {

    }

    @FXML
    void dislikeClicked(MouseEvent event) {

    }

    @FXML
    void laughClicked(MouseEvent event) {

    }

    @FXML
    void likeClicked(MouseEvent event) {

    }

    @FXML
    void pinClicked(MouseEvent event) {

    }

    @FXML
    void onUploadFileClicked(MouseEvent event) {

    }




    public void gotoServerMenu(String menu) throws InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + menu + ".fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerAndChannelController controller = loader.getController();
        Client.updateInfosInServer(id);
        TimeUnit.MILLISECONDS.sleep(100);
        controller.updateInfos(textChannels, voiceChannels, members, allServers, id);
        Client.changeScene(new Scene(root));
    }




    public void updateInfos(ArrayList<String> textChannels, ArrayList<String> voiceChannels, ArrayList<MemberInfo> members,ArrayList<ServerInfo> allServers, int id){
        this.textChannels = textChannels;
        this.voiceChannels = voiceChannels;
        this.members = members;
        this.allServers = allServers;
        showTextChannels();
        showVoiceChannels();
        showMembers();
        showServersInMainMenuList(allServers);
        if(id != -1) {
            this.id = id;
        }
    }

    public void updateInfosFromClientIn(ArrayList<String> textChannels, ArrayList<String> voiceChannels, ArrayList<MemberInfo> members, ArrayList<AdminInfo> admins, ArrayList<ServerInfo> allServers){
        this.textChannels = textChannels;
        this.voiceChannels = voiceChannels;
        this.members = members;
        this.allServers = allServers;
        this.admins = admins;
    }

    public void setPicName(String name){
        for(ServerInfo serverInfo : allServers){
            if(serverInfo.getId() == id){
                serverInfo.setPicName(name);
            }
        }
    }

    public void showTextChannels(){
        if(textChannelsVBox != null) {
            for (String name : textChannels) {
                HBox root;
                ImageView imageView = new ImageView(new Image("file:Client\\src\\main\\resources\\assets\\icons\\channelText.png"));
                imageView.setFitHeight(27);
                imageView.setFitWidth(28);
                imageView.setPickOnBounds(true);
                imageView.setPreserveRatio(true);
                Label label = new Label(name);
                label.setTextFill(Color.web("#c0bcbc"));
                label.setFont(new Font("System Bold", 15));
                label.setPadding(new Insets(5, 5, 5, 5));
                root = new HBox(imageView, label);
                root.setAlignment(Pos.CENTER_LEFT);
                root.setSpacing(35);
                root.setStyle("-fx-background-color: #202225;");
                root.setPadding(new Insets(5, 5, 5, 5));

                root.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                    }
                });


                textChannelsVBox.getChildren().add(root);
            }
        }
    }

    public void showVoiceChannels(){
        if(voiceChannelsVBox != null) {
            for (String name : voiceChannels) {
                HBox root;
                ImageView imageView = new ImageView(new Image("file:Client\\src\\main\\resources\\assets\\icons\\channelVoice.png"));
                imageView.setFitHeight(27);
                imageView.setFitWidth(28);
                imageView.setPickOnBounds(true);
                imageView.setPreserveRatio(true);
                Label label = new Label(name);
                label.setTextFill(Color.web("#c0bcbc"));
                label.setFont(new Font("System Bold", 15));
                label.setPadding(new Insets(5, 5, 5, 5));
                root = new HBox(imageView, label);
                root.setAlignment(Pos.CENTER_LEFT);
                root.setSpacing(35);
                root.setStyle("-fx-background-color: #202225;");
                root.setPadding(new Insets(5, 5, 5, 5));
                voiceChannelsVBox.getChildren().add(root);

            }
        }

    }

    public void showMembers(){
        if(eachStatusOnlineMembersOfChannelVBox != null) {
            for (MemberInfo member : members) {
                String name = member.getUserNameWithToken();
                String status = member.getStatus();
                Image profilePic = new Image("file:Client\\profilePics\\" + member.getPhotoName());
                Image statusPic = new Image("file:Client\\files\\statuspics\\" + status + ".png");


                HBox root;

                //root childs
                Pane pane;
                Label label = new Label(name);
                label.setTextFill(Color.WHITE);
                label.setWrapText(true);

                //pane childs
                Circle profilePhotoCircle = new Circle();
                profilePhotoCircle.setFill(new ImagePattern(profilePic));
                profilePhotoCircle.setLayoutX(17);
                profilePhotoCircle.setLayoutY(16);
                profilePhotoCircle.setRadius(17);
                profilePhotoCircle.setStroke(Color.BLACK);
                profilePhotoCircle.setStrokeType(StrokeType.INSIDE);
                Circle statusCircle = new Circle();
                statusCircle.setFill(new ImagePattern(statusPic));
                statusCircle.setLayoutX(27);
                statusCircle.setLayoutY(26);
                statusCircle.setRadius(7);
                statusCircle.setStroke(Color.BLACK);
                statusCircle.setStrokeType(StrokeType.INSIDE);

                pane = new Pane(profilePhotoCircle, statusCircle);
                root = new HBox(pane, label);
                root.setAlignment(Pos.CENTER_LEFT);
                root.setSpacing(15);
                root.setStyle("-fx-background-color: #202225;");
                root.setPadding(new Insets(10, 10, 10, 10));

                if (!status.equals("offline")) {
                    eachStatusOnlineMembersOfChannelVBox.getChildren().add(root);
                } else {
                    eachStatusOfflineMembersOfChannelVBox.getChildren().add(root);
                }
            }
        }
    }

    public void showServersInMainMenuList(ArrayList<ServerInfo> informations){
        if(serversVBox != null) {
            for (ServerInfo information : informations) {
                String name;
                String picName = information.getPicName();
                ImagePattern profilePic = new ImagePattern(new Image("file:Client\\serverPics\\" + picName));
                Pane root;
                if(information.getId() == id){
                    if(serverImgCircleInSettings != null){
                        serverImgCircleInSettings.setFill(profilePic);
                    }
                }


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

                root.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Client.serverLogout();
                        try {
                            TimeUnit.MILLISECONDS.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int id = information.getId();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/serverMainPage.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ServerAndChannelController controller = loader.getController();
                        Client.updateInfosInServer(id);
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        controller.updateInfos(textChannels, voiceChannels, members, allServers, id);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Client.gotoServer(controller, id);
                                }catch (Exception e){

                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        Client.changeScene(new Scene(root));

                    }
                });

                serversVBox.getChildren().add(root);
            }
        }

    }


    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
