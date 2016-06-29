package layout.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ilija.tomic on 6/17/2016.
 */
public class Meni implements Initializable {

    @FXML
    private Tab kategorijaTab;
    @FXML
    private Tab podkategorijaTab;
    @FXML
    private Tab stavkeTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../kategorija_tab.fxml"));
            kategorijaTab.setContent(root);
            root = FXMLLoader.load(getClass().getResource("../podkategorija_tab.fxml"));
            podkategorijaTab.setContent(root);
            root = FXMLLoader.load(getClass().getResource("../stavka_tab.fxml"));
            stavkeTab.setContent(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
