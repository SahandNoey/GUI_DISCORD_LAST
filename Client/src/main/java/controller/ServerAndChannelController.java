package controller;

//import com.jfoenix.controls.JFXTextField;
//import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import model.other.MemberInfo;

import java.util.ArrayList;

public class ServerAndChannelController {

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

    @FXML
    void onMsgClicked(MouseEvent event) {

    }

    @FXML
    void addServerClicked(MouseEvent event) {

    }

    @FXML
    void channelSendMsgClicked(MouseEvent event) {

    }

    @FXML
    void createChannelClicked(MouseEvent event) {

    }

    @FXML
    void homeBtnClicked(MouseEvent event) {

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
    void serverSettingsClicked(MouseEvent event) {

    }

    @FXML
    void onMemberClicked(MouseEvent event){

    }

    @FXML
    void onKickClicked(MouseEvent event) {

    }

    @FXML
    void onCancelInSettingsClicked(MouseEvent event) {

    }

    @FXML
    void onDoneInSettingsClicked(MouseEvent event) {

    }

    @FXML
    void onUploadImgInSettingsClicked(MouseEvent event) {

    }

    @FXML
    void cancelBtnInCreateChannelPopupClicked(MouseEvent event) {

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

    public void setChannels(ArrayList<String> textChannels, ArrayList<String> voiceChannels, ArrayList<MemberInfo> members){
        this.textChannels = textChannels;
        this.voiceChannels = voiceChannels;
        this.members = members;
        showTextChannels();
        showVoiceChannels();
        showMembers();

    }

    public void showTextChannels(){
        for(String name : textChannels){
            HBox root;
            ImageView imageView = new ImageView(new Image("file:Client\\src\\main\\resources\\assets\\icons\\channelText.png"));
            imageView.setFitHeight(27);
            imageView.setFitWidth(28);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            Label label = new Label(name);
            label.setTextFill(Color.web("#c0bcbc"));
            label.setFont(new Font("System Bold", 15));
            label.setPadding(new Insets(5,5,5,5));
            root = new HBox(imageView, label);
            root.setAlignment(Pos.CENTER_LEFT);
            root.setSpacing(35);
            root.setStyle("-fx-background-color: #202225;");
            root.setPadding(new Insets(5,5,5,5));


            textChannelsVBox.getChildren().add(root);
        }
    }

    public void showVoiceChannels(){
        for(String name : voiceChannels){
            HBox root;
            ImageView imageView = new ImageView(new Image("file:Client\\src\\main\\resources\\assets\\icons\\channelVoice.png"));
            imageView.setFitHeight(27);
            imageView.setFitWidth(28);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            Label label = new Label(name);
            label.setTextFill(Color.web("#c0bcbc"));
            label.setFont(new Font("System Bold", 15));
            label.setPadding(new Insets(5,5,5,5));
            root = new HBox(imageView, label);
            root.setAlignment(Pos.CENTER_LEFT);
            root.setSpacing(35);
            root.setStyle("-fx-background-color: #202225;");
            root.setPadding(new Insets(5,5,5,5));
            voiceChannelsVBox.getChildren().add(root);

        }

    }

    public void showMembers(){
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
            }
            else {
                eachStatusOfflineMembersOfChannelVBox.getChildren().add(root);
            }
        }
    }

}
