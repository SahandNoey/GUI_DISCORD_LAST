package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.client.Client;
import starter.ClientStarter;

import java.io.File;
import java.io.IOException;

public class SignupProfilePicController {

    @FXML
    private TextField pathToPicTxtFld;

    @FXML
    private Button doneBtn;

    @FXML
    private Button skipBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Label invalidCredentialsTxtFld;

    @FXML
    private Button chooseFileBtn;

    @FXML
    void backClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signupPhoneConfirm.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }
    @FXML
    void chooseButtonClicked(MouseEvent event){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(ClientStarter.stage);
        if(file != null){
            pathToPicTxtFld.setText(file.getAbsolutePath());
        }
    }

    @FXML
    void doneClicked(MouseEvent event) throws IOException {
        if(pathToPicTxtFld.getText() != null) {
            if(!pathToPicTxtFld.getText().equals("")) {
                if(Client.setUserProfilePic(pathToPicTxtFld.getText())){
                    profilePicAccepted();
                }
                else{
                    profilePicNotAccepted();
                }
            }
        }
    }

    public void profilePicAccepted() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signIn.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }
    public void profilePicNotAccepted(){
        invalidCredentialsTxtFld.setOpacity(1);
    }

    @FXML
    void skipClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signIn.fxml"));
        Client.skip();
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }



}
