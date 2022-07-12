package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.client.Client;
import model.other.MemberInfo;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MessageViewCreator implements Initializable {

    private MemberInfo senderInfo;
    private ArrayList<FXMLLoader> reactionsFxmlLoaders;
    private ReactionViewCreator reactionViewCreator;

    @FXML
    private VBox msgVBox;

    @FXML
    private HBox msgHBox;

    @FXML
    private Circle channelMsgSenderProfileCircle;

    @FXML
    private Label msgContentLabel;

    @FXML
    private HBox msgReactionsHBox;

    @FXML
    void onMsgClicked(MouseEvent event) {

    }

    public void setInfo(MemberInfo memberInfo, String msgContent){
        this.senderInfo = memberInfo;
        msgContentLabel.setText(msgContent);
        channelMsgSenderProfileCircle.setFill(new ImagePattern(new Image("file:Client\\profilePics\\" + senderInfo.getPhotoName())));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reactionsFxmlLoaders = new ArrayList<>();
        // backned here for getting sendername
        try {
            for (int i = 0 ; i < 3 ;i++)
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ReactionViewCreator.class.getResource("/fxml/reactionMsgHBox.fxml")); // for like , dislike and laugh
                loader.load();
                reactionsFxmlLoaders.add(loader);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
