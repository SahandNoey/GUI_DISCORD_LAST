package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopupLoader {
    private Object controller;
    private String resourceStr;
    private FXMLLoader loader;
    private Stage stage;

    public void popup(MouseEvent event) throws Exception {
        stage = new Stage(StageStyle.UNDECORATED);
        stage.setX(event.getScreenX());
        stage.setY(event.getScreenY());
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public void close(){
        stage.close();
    }

    public PopupLoader(Object controller, String resourceStr){
        this.controller = controller;
        this.resourceStr = resourceStr;
        loader = new FXMLLoader();
        loader.setController(controller);
        loader.setLocation(controller.getClass().getResource(resourceStr));
    }
}

