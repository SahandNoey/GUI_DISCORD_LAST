package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import javafx.scene.text.Text;
import model.client.Client;
import model.other.MemberInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FriendsController implements Initializable {

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
    private Pane friend1Pane;

    @FXML
    private Circle friendImageCircle;

    @FXML
    private Circle friendStautsCircle;

    @FXML
    private Label friendNameLabel;

    @FXML
    private Label friendStatusLabel;

    @FXML
    private Button friend1SendMsgBtn;

    @FXML
    private ImageView threeDotsForFriend;

    @FXML
    private VBox OnlineFriendsListVBox;

    @FXML
    private VBox blockedListVBox;

    @FXML
    private VBox pendingFriendsListVBox;


    @FXML
    private Label removeFriendBtn;

    @FXML
    private Label blockFriendBtn;

    @FXML
    private TextField addFriendusernameTxtFld;

    @FXML
    private Button sendReqAddFrndBtn;

    @FXML
    private Label sendFriendRequestResultTxt;

    @FXML
    private VBox friendsListVBox;

    @FXML
    void addDMBtnClicked(MouseEvent event) {

    }

    @FXML
    void addFriendClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addFriendspage.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void addServerClicked(MouseEvent event) {

    }

    @FXML
    void allClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/friendsListPage.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void blockedClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/friendsListPageBlocked.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void friendsClicked(MouseEvent event) {

    }

    @FXML
    void homeBtnClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void myAccountClicked(MouseEvent event) {

    }

    @FXML
    void onDMFriendClicked(MouseEvent event) {

    }

    @FXML
    void onlineClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/friendsListPageOnline.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void pendingClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/friendsListPagePending.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void sendRequestToAddFriendClicked(MouseEvent event) {
        if (addFriendusernameTxtFld.getText() != null) {
            if (!addFriendusernameTxtFld.equals("")) {
                String res = Client.checkIfCanSendFriendRequestTo(addFriendusernameTxtFld.getText());
                if (res.equals("yes")) {
                    sendFriendRequestResultTxt.setText("friend request sent.");
                    sendFriendRequestResultTxt.setTextFill(Color.GREEN);
                    sendFriendRequestResultTxt.setOpacity(1);
                } else {
                    if (res.equals("invalid username")) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("FRIEND REQUEST FAILED");
                        a.setContentText("Hm,that dont work. check username.");
                        a.show();
                    }
                    sendFriendRequestResultTxt.setText(res);
                    sendFriendRequestResultTxt.setTextFill(Color.RED);
                    sendFriendRequestResultTxt.setOpacity(1);
                }
            }
        }
    }

    @FXML
    void blockFriendClicked(MouseEvent event) {

    }

    @FXML
    void removeFriendClicked(MouseEvent event) {

    }

    @FXML
    void sendMessageInFriendsListClicked(MouseEvent event) {

    }

    @FXML
    void onFriendThreeDotsClicked(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (friendsListVBox != null) {
            ArrayList<MemberInfo> informations = Client.getFriendsForFriendsMenu();
            showFriendsInFriendsList(informations);
        } else if (OnlineFriendsListVBox != null) {
            ArrayList<MemberInfo> friends = Client.getFriendsForFriendsMenu();
            ArrayList<MemberInfo> onlines = new ArrayList<>();
            for (MemberInfo friend : friends) {
                if (friend.getStatus().equals("online")) {
                    onlines.add(friend);
                }
            }
            showFriendsInFriendsList(onlines);
        } else if (blockedListVBox != null) {
            ArrayList<MemberInfo> blocks = Client.getBlocksForFriendsMenu();
            showFriendsInFriendsList(blocks);
        } else if (pendingFriendsListVBox != null) {
            ArrayList<MemberInfo> requests = Client.getFriendRequestForFriendsMenu();
            showFriendRequestsInFriendsList(requests);
            ArrayList<MemberInfo> sentRequests = Client.getSentFriendRequestForFriendsMenu();
            showSentFriendRequestsInFriendsList(sentRequests);
        }

    }


    public void showFriendsInFriendsList(ArrayList<MemberInfo> informations) {
        VBox target = null;
        if (friendsListVBox != null) {
            target = friendsListVBox;
        } else if (OnlineFriendsListVBox != null) {
            target = OnlineFriendsListVBox;
        }
        for (MemberInfo information : informations) {
            String name = information.getUserNameWithToken();
            String status = information.getStatus();
            Image profilePic = new Image("file:Client\\profilePics\\" + information.getPhotoName());
            Image statusPic = new Image("file:Client\\files\\statuspics\\" + status + ".png");
            Pane root;


            //root childs
            HBox hBox1;
            Button btn1 = new Button("Send Message");
            btn1.setStyle("-fx-background-color :  #5865F2;");
            btn1.setTextFill(Color.BLACK);
            btn1.setLayoutY(26);
            btn1.setLayoutX(638);
            btn1.setMnemonicParsing(false);
            btn1.setStyle("-fx-background-color: #5865F2;");
            ImageView imageView1 = new ImageView(new Image("file:Client\\src\\main\\resources\\assets\\icons\\threeDots.png"));
            imageView1.setFitHeight(30);
            imageView1.setFitWidth(30);
            imageView1.setLayoutX(775);
            imageView1.setLayoutY(26);
            imageView1.setPickOnBounds(true);
            imageView1.setPreserveRatio(true);

            //hbox1 childs
            Pane pane1;
            VBox vbox1;

            //pane1 childs
            Circle profilePicCircle = new Circle();
            Circle statusCircle = new Circle();
            profilePicCircle.setFill(new ImagePattern(profilePic));
            statusCircle.setFill(new ImagePattern(statusPic));
            profilePicCircle.setLayoutX(33);
            profilePicCircle.setLayoutY(32);
            profilePicCircle.setRadius(30);
            profilePicCircle.setStroke(Color.BLACK);
            profilePicCircle.setStrokeType(StrokeType.INSIDE);
            statusCircle.setLayoutX(53);
            statusCircle.setLayoutY(52);
            statusCircle.setRadius(10);
            statusCircle.setStroke(Color.BLACK);
            statusCircle.setStrokeType(StrokeType.INSIDE);

            //vbox1 childs
            Label nameLabel = new Label(name);
            Label statusLabel = new Label(status);
            nameLabel.setFont(new Font("System Bold", 18));
            nameLabel.setTextFill(Color.WHITE);
            statusLabel.setFont(new Font("System Italic", 14));
            statusLabel.setLayoutX(10);
            statusLabel.setLayoutY(10);
            statusLabel.setTextFill(Color.web("#58f287"));
            //done
            vbox1 = new VBox(nameLabel, statusLabel);
            vbox1.setAlignment(Pos.CENTER);
            pane1 = new Pane(profilePicCircle, statusCircle);
            pane1.setStyle("-fx-background-radius: 100;");
            hBox1 = new HBox(pane1, vbox1);
            hBox1.setAlignment(Pos.CENTER);
            hBox1.setLayoutX(14);
            hBox1.setLayoutY(3);
            hBox1.setSpacing(30);
            root = new Pane(hBox1, btn1, imageView1);
            root.setLayoutX(15);
            root.setLayoutY(246);
            root.setPrefHeight(82);
            root.setPrefWidth(829);
            root.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            root.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            root.setStyle("-fx-background-color: #2f3136; -fx-background-radius: 10;");
            target.getChildren().add(root);
        }

    }

    public void showSentFriendRequestsInFriendsList(ArrayList<MemberInfo> informations) {
        VBox target = pendingFriendsListVBox;
        for (MemberInfo information : informations) {
            String name = information.getUserNameWithToken();
            String status = information.getStatus();
            Image profilePic = new Image("file:Client\\profilePics\\" + information.getPhotoName());
            Image statusPic = new Image("file:Client\\files\\statuspics\\" + status + ".png");
            Pane root;


            //root childs
            HBox hBox1;
            ImageView imageView1 = new ImageView(new Image("file:Client\\src\\main\\resources\\assets\\icons\\close.png"));
            imageView1.setFitHeight(30);
            imageView1.setFitWidth(30);
            imageView1.setLayoutX(775);
            imageView1.setLayoutY(26);
            imageView1.setPickOnBounds(true);
            imageView1.setPreserveRatio(true);
            imageView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Client.cancelFriendRequest(information.getUserNameWithToken());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/friendsListPagePending.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Client.changeScene(new Scene(root));
                }
            });

            //hbox1 childs
            Pane pane1;
            VBox vbox1;

            //pane1 childs
            Circle profilePicCircle = new Circle();
            Circle statusCircle = new Circle();
            profilePicCircle.setFill(new ImagePattern(profilePic));
            statusCircle.setFill(new ImagePattern(statusPic));
            profilePicCircle.setLayoutX(33);
            profilePicCircle.setLayoutY(32);
            profilePicCircle.setRadius(30);
            profilePicCircle.setStroke(Color.BLACK);
            profilePicCircle.setStrokeType(StrokeType.INSIDE);
            statusCircle.setLayoutX(53);
            statusCircle.setLayoutY(52);
            statusCircle.setRadius(10);
            statusCircle.setStroke(Color.BLACK);
            statusCircle.setStrokeType(StrokeType.INSIDE);

            //vbox1 childs
            Label nameLabel = new Label(name);
            Label statusLabel = new Label(status);
            nameLabel.setFont(new Font("System Bold", 18));
            nameLabel.setTextFill(Color.WHITE);
            statusLabel.setFont(new Font("System Italic", 14));
            statusLabel.setLayoutX(10);
            statusLabel.setLayoutY(10);
            statusLabel.setTextFill(Color.web("#58f287"));
            //done
            vbox1 = new VBox(nameLabel, statusLabel);
            vbox1.setAlignment(Pos.CENTER);
            pane1 = new Pane(profilePicCircle, statusCircle);
            pane1.setStyle("-fx-background-radius: 100;");
            hBox1 = new HBox(pane1, vbox1);
            hBox1.setAlignment(Pos.CENTER);
            hBox1.setLayoutX(14);
            hBox1.setLayoutY(3);
            hBox1.setSpacing(30);
            root = new Pane(hBox1, imageView1);
            root.setLayoutX(15);
            root.setLayoutY(246);
            root.setPrefHeight(82);
            root.setPrefWidth(829);
            root.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            root.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            root.setStyle("-fx-background-color: #2f3136; -fx-background-radius: 10;");
            target.getChildren().add(root);
        }
    }

    public void showFriendRequestsInFriendsList(ArrayList<MemberInfo> informations) {
        VBox target = pendingFriendsListVBox;
        for (MemberInfo information : informations) {
            String name = information.getUserNameWithToken();
            String status = information.getStatus();
            Image profilePic = new Image("file:Client\\profilePics\\" + information.getPhotoName());
            Image statusPic = new Image("file:Client\\files\\statuspics\\" + status + ".png");
            Pane root;


            //root childs
            HBox hBox1;
            ImageView imageView2 = new ImageView(new Image("file:Client\\src\\main\\resources\\assets\\icons\\plus2.png"));
            imageView2.setFitHeight(30);
            imageView2.setFitWidth(30);
            imageView2.setLayoutX(730);
            imageView2.setLayoutY(26);
            imageView2.setPickOnBounds(true);
            imageView2.setPreserveRatio(true);
            imageView2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Client.acceptFriendRequest(information.getUserNameWithToken());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/friendsListPagePending.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Client.changeScene(new Scene(root));
                }
            });
            ImageView imageView1 = new ImageView(new Image("file:Client\\src\\main\\resources\\assets\\icons\\close.png"));
            imageView1.setFitHeight(30);
            imageView1.setFitWidth(30);
            imageView1.setLayoutX(775);
            imageView1.setLayoutY(26);
            imageView1.setPickOnBounds(true);
            imageView1.setPreserveRatio(true);
            imageView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Client.rejectFriendRequest(information.getUserNameWithToken());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/friendsListPagePending.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Client.changeScene(new Scene(root));
                }
            });

            //hbox1 childs
            Pane pane1;
            VBox vbox1;

            //pane1 childs
            Circle profilePicCircle = new Circle();
            Circle statusCircle = new Circle();
            profilePicCircle.setFill(new ImagePattern(profilePic));
            statusCircle.setFill(new ImagePattern(statusPic));
            profilePicCircle.setLayoutX(33);
            profilePicCircle.setLayoutY(32);
            profilePicCircle.setRadius(30);
            profilePicCircle.setStroke(Color.BLACK);
            profilePicCircle.setStrokeType(StrokeType.INSIDE);
            statusCircle.setLayoutX(53);
            statusCircle.setLayoutY(52);
            statusCircle.setRadius(10);
            statusCircle.setStroke(Color.BLACK);
            statusCircle.setStrokeType(StrokeType.INSIDE);

            //vbox1 childs
            Label nameLabel = new Label(name);
            Label statusLabel = new Label(status);
            nameLabel.setFont(new Font("System Bold", 18));
            nameLabel.setTextFill(Color.WHITE);
            statusLabel.setFont(new Font("System Italic", 14));
            statusLabel.setLayoutX(10);
            statusLabel.setLayoutY(10);
            statusLabel.setTextFill(Color.web("#58f287"));
            //done
            vbox1 = new VBox(nameLabel, statusLabel);
            vbox1.setAlignment(Pos.CENTER);
            pane1 = new Pane(profilePicCircle, statusCircle);
            pane1.setStyle("-fx-background-radius: 100;");
            hBox1 = new HBox(pane1, vbox1);
            hBox1.setAlignment(Pos.CENTER);
            hBox1.setLayoutX(14);
            hBox1.setLayoutY(3);
            hBox1.setSpacing(30);
            root = new Pane(hBox1, imageView2, imageView1);
            root.setLayoutX(15);
            root.setLayoutY(246);
            root.setPrefHeight(82);
            root.setPrefWidth(829);
            root.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            root.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            root.setStyle("-fx-background-color: #2f3136; -fx-background-radius: 10;");
            target.getChildren().add(root);
        }
    }

}
