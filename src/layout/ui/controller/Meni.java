package layout.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ilija.tomic on 6/17/2016.
 */
public class Meni implements Initializable {

    @FXML
    private TabPane meni_tabpane;
    @FXML
    private Tab kategorijaTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../kategorija_tab.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        kategorijaTab.setContent(root);
    }
}
