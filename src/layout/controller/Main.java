package layout.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Created by ilija.tomic on 6/17/2016.
 */
public class Main {

    @FXML
    private BorderPane main_bPane;

    public void showProfil() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../profile.fxml"));

        main_bPane.setCenter(root);

    }
}
