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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.client.Client;
import model.client.ClientOut;
import model.other.MemberInfo;
import model.other.Message;
import model.other.ServerInfo;
import starter.ClientStarter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

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
    private Text tempText;

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

    public void setTempText(String text){
        tempText.setText(text);
    }

    public void addMessage(Message m) {
        MemberInfo memberInfo;
        if (m.getAuthorToken() == Integer.parseInt(me.getUserNameWithToken().split("#")[1])) {
            memberInfo = me;
        } else {
            memberInfo = friend;
        }


        String text;
        if(m.getMessage().startsWith("%%!file:::")){
            text = "New File :" + m.getMessage().split(":::")[1];
        }
        else {
            text = m.getMessage();
        }


        Image profilePic = new Image("file:Client\\profilePics\\" + memberInfo.getPhotoName());

        Pane root;

        //root childs
        HBox hBox1;

        //hbox1 childs
        Pane pane1;
        Label label1 = new Label(text);
        label1.setPrefHeight(22);
        label1.setPrefWidth(729);
        if(m.getMessage().startsWith("%%!file:::")){
            System.out.println("fhf");
            label1.setTextFill(Color.RED);
        }
        else {
            label1.setTextFill(Color.WHITE);
        }
        label1.setWrapText(true);
        label1.setFont(new Font("Comic Sans MS", 15));
        Label label2 = null;
        if(m.getMessage().startsWith("%%!file:::")){
            label2 = new Label("Download");
            label2.setFont(new Font("Comic Sans MS", 15));
            label2.setTextFill(Color.GREEN);
            label2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    tempText.setText("downloading a file...");
                    ClientOut.sendCommand("%%!getFile:::" + m.getMessage());
                }
            });
        }

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
        if(m.getMessage().startsWith("%%!file:::")){
            hBox1 = new HBox(pane1, label2, label1);
        }
        else{
            hBox1 = new HBox(pane1, label1);
        }
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
        //friend profile
        friendNameLabel.setText(friend.getUserNameWithToken());
        friendStatusLabel.setText(friend.getStatus());
        dmMsgImageCircle.setFill(new ImagePattern(new Image("file:Client\\profilePics\\" + friend.getPhotoName())));
        dmFriendStatusCircle.setFill(new ImagePattern(new Image("file:Client\\files\\statuspics\\" + friend.getStatus() + ".png")));
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
                    ClientOut.sendCommand("%%!getOutOfChat");
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
                            try {
                                Client.gotoDMWith(controller, id);
                            } catch (IOException e) {
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

    public void initialize(URL url, ResourceBundle resourceBundle) {
        //servers
        showServersInMainMenuList(Client.getServersForMainMenu());
        //Direct messages
        showDMsInMainMenuList(Client.getFriendsWithDMForFriendsMenu());

    }

    @FXML
    void keyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            if (dmTxtFld.getText() != null) {
                if (!dmTxtFld.getText().equals("")) {
                    ClientOut.sendCommand(dmTxtFld.getText());
                    dmTxtFld.setText("");
                }
            }
        }
    }
    @FXML
    void uploadButtonClicked(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(ClientStarter.stage);
        if(file != null){
            tempText.setText("sending a file...");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileInputStream fl = null;
                    try {
                        fl = new FileInputStream(file);
                        byte[] content = new byte[(int) file.length()];
                        content = fl.readAllBytes();
                        ClientOut.sendFile(new Message(content, file.getName(), Integer.parseInt(me.getUserNameWithToken().split("#")[1])));
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                tempText.setText("");
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }
    }

}

