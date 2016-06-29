package layout.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.Kategorija;
import util.AppObject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ilija.tomic on 6/29/2016.
 */
public class PodkategorijaTab implements Initializable {

    @FXML
    private ChoiceBox<Kategorija> kategorija;
    @FXML
    private TextField search;

    public void dodaj_podkategoriju() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        kategorija.getItems().add(new Kategorija(null, "kategorija"));
        for (Kategorija k : AppObject.getInstance().getMojRestoran().getKategorijaArrayList()) {
            kategorija.getItems().add(k);
        }
        kategorija.getSelectionModel().selectFirst();
    }
}
