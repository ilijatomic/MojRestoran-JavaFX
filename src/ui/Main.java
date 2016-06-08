package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Constants;

public class Main extends Application {

    private Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;

        showLogin();

    }

    private void showLogin() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../layout/login.fxml"));
        window.setTitle(Constants.APP_TITLE);
        window.setScene(new Scene(root, 1024, 768));
        window.show();
    }

}
