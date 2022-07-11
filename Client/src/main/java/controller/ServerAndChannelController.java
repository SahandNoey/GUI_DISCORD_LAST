package controller;

//import com.jfoenix.controls.JFXTextField;
//import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

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
    private VBox typeChannelVBox;

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
    private VBox eachStatusMembersOfChannelVBox;

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

}
