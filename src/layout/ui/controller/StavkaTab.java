package layout.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.Kategorija;
import model.Podkategorija;
import util.AppObject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ilija.tomic on 6/29/2016.
 */
public class StavkaTab implements Initializable {

    @FXML
    private ChoiceBox<Kategorija> kategorija;
    @FXML
    private ChoiceBox<Podkategorija> podkategorija;
    @FXML
    private TextField serach;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        kategorija.getItems().add(new Kategorija(null, "kategorija"));
        for (Kategorija kat : AppObject.getInstance().getMojRestoran().getKategorijaArrayList()) {
            kategorija.getItems().add(kat);
        }
        kategorija.getSelectionModel().selectFirst();

        podkategorija.getItems().add(new Podkategorija(null, "podkategorija", null));
        for (Podkategorija podkat : AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList()) {
            podkategorija.getItems().add(podkat);
        }
        podkategorija.getSelectionModel().selectFirst();
    }

    public void dodaj_stavku() {

    }
}
