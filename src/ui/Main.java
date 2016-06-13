package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import util.AppObject;
import util.Constants;
import util.StageUtils;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../layout/login.fxml"));
        primaryStage.setTitle(Constants.APP_TITLE);
        primaryStage.setScene(StageUtils.createMainScene(root));
        primaryStage.show();

        AppObject.getInstance().initDatabase();
    }
}
