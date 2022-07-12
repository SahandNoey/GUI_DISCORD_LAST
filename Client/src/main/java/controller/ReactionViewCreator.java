package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ReactionViewCreator {

    @FXML
    private HBox reactHBox;

    @FXML
    private ImageView reactImgView;

    @FXML
    private Label reactLabel;

    private String type = "";

    @FXML
    void onReactionClicked(MouseEvent event) {
        update();
    }

    public void setImage(String type){
        reactImgView.setImage(new Image("/assets/icons/" + type + ".png"));
        this.type = type;
    }

    private void update(){
        String str = reactLabel.getText();
        int newVal = Integer.parseInt(str);
        newVal++;
        reactLabel.setText(String.valueOf(newVal));

        // maybe backend
    }


}

