package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.client.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    @FXML
    private TextField EmailTxtFld;

    @FXML
    private CheckBox showPassCheckBox;

    @FXML
    private TextField passwordTxtFld;
    @FXML
    private PasswordField passwordPassFld;

    @FXML
    private Button signInBtn;

    @FXML
    private Label createAccntTxtFld;

    @FXML
    private Label invalidCredentialsTxtFld;

    @FXML
    void goToSignUp(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signUp.fxml"));
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }

    @FXML
    void signInButtonClicked(MouseEvent event) throws IOException {
        if(EmailTxtFld.getText() != null && passwordTxtFld.getText() != null) {
            if(!EmailTxtFld.getText().equals("") && !passwordTxtFld.getText().equals("")) {
                if(Client.checkUserSignIn(EmailTxtFld.getText(), passwordTxtFld.getText())){
                    signInAccepted();
                }
                else {
                    signInNotAccepted();
                }
            }
        }
    }

    public void signInAccepted() throws IOException {
        goToMain();
    }

    public void signInNotAccepted(){
        invalidCredentialsTxtFld.setVisible(true);
    }

    public void goToMain() throws IOException {
        Client.mainController = new MainController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
        loader.setController(Client.mainController);
        Parent root = loader.load();
        Client.changeScene(new Scene(root));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passwordTxtFld.managedProperty().bind(showPassCheckBox.selectedProperty());
        passwordTxtFld.visibleProperty().bind(showPassCheckBox.selectedProperty());

        passwordPassFld.managedProperty().bind(showPassCheckBox.selectedProperty().not());
        passwordPassFld.visibleProperty().bind(showPassCheckBox.selectedProperty().not());

        passwordTxtFld.textProperty().bindBidirectional(passwordPassFld.textProperty());
    }
}
