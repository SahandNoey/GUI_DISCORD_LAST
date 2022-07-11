package starter;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Popup;
import model.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientStarter extends Application {

    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        ClientStarter.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ClientStarter.class.getResource("/fxml/connecting.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Discord");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/assets/icons/discord-logo.png"));
        stage.show();
        Client client = new Client();
        client.start(stage);

    }

    public static void main(String[] args) {
        launch();
    }
}