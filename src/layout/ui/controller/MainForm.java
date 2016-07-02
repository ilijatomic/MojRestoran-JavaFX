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

    public void showProfil() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../profile.fxml"));
        main_bPane.setCenter(root);
    }

    public void logout(ActionEvent event) throws IOException {
        AppObject.getInstance().setUlogovanKorisnik(null);
        Parent root = FXMLLoader.load(getClass().getResource("../login.fxml"));
        StageUtils.getStageFromEvent(event).setScene(StageUtils.createMainScene(root));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showMeni();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMeni() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../meni.fxml"));
        main_bPane.setCenter(root);
    }
    public void showKorisnici() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../korisnici.fxml"));
        main_bPane.setCenter(root);
    }
    public void showStolovi() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../sto.fxml"));
        main_bPane.setCenter(root);
    }
    public void showNarudzbine() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../narudzbine.fxml"));
        main_bPane.setCenter(root);
    }
}
