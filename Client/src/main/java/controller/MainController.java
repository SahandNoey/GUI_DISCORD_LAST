package controller;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.client.Client;
import model.client.ClientIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.other.MemberInfo;
import model.other.Message;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ImageView profilePicInMenu;
    @FXML
    private Circle profilePicCircle;

    @FXML
    void friendRequestsButtonClicked(MouseEvent event) {

    }

    @FXML
    void friendsButtonClicked(MouseEvent event) throws IOException {
        Client.friendsController = new FriendsController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("friendsMenu.fxml"));
        FriendsController friendsController = new FriendsController();
        loader.setController(friendsController);
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void logoutButtonClicked(MouseEvent event) {

    }

    @FXML
    void profileButtonClicked(MouseEvent event) {

    }

    @FXML
    void serversButtonClicked(MouseEvent event) {

    }

    @FXML
    void settingButtonClicked(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MemberInfo me = Client.getMyMemberInfo();
        Client.downloadProfilePicIfDontHave(me.getPhotoName());
        setProfilePic(me.getPhotoName());

    }

    public void setProfilePic(String fileName){
        profilePicCircle.setFill(new ImagePattern(new Image("file:Client\\profilePics\\" + fileName)));
    }


}
