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

    public void popup(MouseEvent event){
        stage = new Stage(StageStyle.UNDECORATED);
        stage.setX(event.getScreenX());
        stage.setY(event.getScreenY());
        try {
            stage.setScene(new Scene(loader.load()));
        }catch (Exception e){
            e.printStackTrace();
        }
        stage.show();
    }

    public void popup(){
        stage = new Stage(StageStyle.UNDECORATED);
        try {
            stage.setScene(new Scene(loader.load()));
        }catch (Exception e){
            e.printStackTrace();
        }
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

