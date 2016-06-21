package layout.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import util.StageUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ilija.tomic on 6/17/2016.
 */
public class MainForm implements Initializable{

    @FXML
    private BorderPane main_bPane;
    @FXML
    private ListView<String> main_list;

    public void showProfil() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../profile.fxml"));
        main_bPane.setCenter(root);
    }

    public void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../login.fxml"));
        StageUtils.getStageFromEvent(event).setScene(StageUtils.createMainScene(root));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        main_list.getItems().addAll("Meni restorana", "Korisnici", "Stolovi", "Narudzbine");
        main_list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String test = main_list.getSelectionModel().getSelectedItem();
            }
        });
    }
}
