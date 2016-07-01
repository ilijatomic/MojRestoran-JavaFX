package layout.ui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import util.AppObject;
import util.StageUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ilija.tomic on 6/17/2016.
 */
public class MainForm implements Initializable {

    private static final String[] menuList = {"Meni restorana", "Korisnici", "Stolovi", "Narudzbine"};

    @FXML
    private BorderPane main_bPane;
    @FXML
    private ListView<String> main_list;

    public void showProfil() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../profile.fxml"));
        main_bPane.setCenter(root);
    }

    public void logout(ActionEvent event) throws IOException {
        AppObject.getInstance().setUlogovanKorisnik(null);
        Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        StageUtils.getStageFromEvent(event).setScene(StageUtils.createMainScene(root));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        main_list.getItems().addAll(menuList);
        main_list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    showMenuItem(main_list.getSelectionModel().getSelectedIndex());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        // TODO show meni on init, once user loged in
    }

    private void showMenuItem(int item) throws IOException {
        Parent root;
        switch (item) {
            case 0:
                root = FXMLLoader.load(getClass().getResource("../meni.fxml"));
                main_bPane.setCenter(root);
                break;
            case 1:
                root = FXMLLoader.load(getClass().getResource("../korisnici.fxml"));
                main_bPane.setCenter(root);
                break;
            case 2:
                root = FXMLLoader.load(getClass().getResource("../sto.fxml"));
                main_bPane.setCenter(root);
                break;
            case 3:
                root = FXMLLoader.load(getClass().getResource("../narudzbine.fxml"));
                main_bPane.setCenter(root);
                break;
        }
    }
}
