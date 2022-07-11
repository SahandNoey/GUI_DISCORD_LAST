package controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import model.other.ServerInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DMController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

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
        if (dmTxtFld.getText() != null) {
            if (!dmTxtFld.getText().equals("")) {
                ClientOut.sendCommand(dmTxtFld.getText());
                dmTxtFld.setText("");
            }
        }
    }

    @FXML
    void friendsClicked(MouseEvent event) throws IOException {
        ClientOut.sendCommand("%%!getOutOfChat");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/friendsListPage.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void homeBtnClicked(MouseEvent event) throws IOException {
        ClientOut.sendCommand("%%!getOutOfChat");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void myAccountClicked(MouseEvent event) throws IOException {
        ClientOut.sendCommand("%%!getOutOfChat");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void onDMFriendClicked(MouseEvent event) {

    }

    public void addMessage(Message m) {
        scrollPane.setContent(dmMessagesVBox);
        MemberInfo memberInfo;
        if (m.getAuthorToken() == Integer.parseInt(me.getUserNameWithToken().split("#")[1])) {
            memberInfo = me;
        } else {
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
                scrollPane.setVvalue(1);
            }
        });
    }

    public void completeTokenToName(HashMap<Integer, String> x, MemberInfo me, MemberInfo friend) {
        tokenToName = x;
        this.me = me;
        this.friend = friend;
    }


    public void showServersInMainMenuList(ArrayList<ServerInfo> informations) {
        for (ServerInfo information : informations) {
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

    public void showDMsInMainMenuList(ArrayList<MemberInfo> informations) {
        for (MemberInfo information : informations) {
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
            root.setPadding(new Insets(10, 10, 10, 10));
            root.setSpacing(15);
            root.setAlignment(Pos.CENTER_LEFT);

            root.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int id = Integer.parseInt(information.getUserNameWithToken().split("#")[1]);
                    MemberInfo me = Client.getMyMemberInfo();
                    MemberInfo friend = Client.getInfoOfToken(id);
                    HashMap<Integer, String> temp = new HashMap<>();
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
                            Client.gotoDMWith(controller, id);
                        }
                    }).start();
                    Client.changeScene(new Scene(root));
                }
            });
            accountAndDMVBox.getChildren().add(root);
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        //servers
        showServersInMainMenuList(Client.getServersForMainMenu());
        //Direct messages
        showDMsInMainMenuList(Client.getFriendsWithDMForFriendsMenu());


    }
}

