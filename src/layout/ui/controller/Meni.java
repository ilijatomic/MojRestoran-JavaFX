package layout.ui.controller;

import com.google.common.eventbus.Subscribe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import layout.dialog.DodajKategorija;
import model.Kategorija;
import model.table.KategorijaColumn;
import util.AppObject;
import util.Validation;
import util.event.DataChange;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by ilija.tomic on 6/17/2016.
 */
public class Meni implements Initializable {

    @FXML
    private TextField search;

    @FXML
    private TableColumn<KategorijaColumn, String> naziv;
    @FXML
    private TableColumn<KategorijaColumn, String> izmeni;
    @FXML
    private TableColumn<KategorijaColumn, String> obrisi;
    @FXML
    private TableView<KategorijaColumn> kategorija;

    private ObservableList<KategorijaColumn> kategorijaObservableList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        naziv.setStyle("-fx-alignment: CENTER-LEFT;");
        naziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        izmeni.setStyle("-fx-alignment: CENTER;");
        izmeni.setCellValueFactory(new PropertyValueFactory<>("izmeni"));
        obrisi.setStyle("-fx-alignment: CENTER;");
        obrisi.setCellValueFactory(new PropertyValueFactory<>("obrisi"));
        kategorijaObservableList = FXCollections.observableArrayList();
        kategorijaObservableList.clear();
        for (Kategorija kategorija : AppObject.getInstance().getMojRestoran().getKategorijaArrayList()) {
            kategorijaObservableList.add(new KategorijaColumn(kategorija));
        }

        kategorija.setItems(kategorijaObservableList);

        AppObject.getInstance().getEventBus().register(this);

    }

    @Subscribe
    public void handleDataChange(DataChange dataChange) {
        if (DataChange.Type.KATEGORIJA.equals(dataChange.getType())) {
            kategorijaObservableList.clear();
            for (Kategorija kategorija : AppObject.getInstance().getMojRestoran().getKategorijaArrayList()) {
                kategorijaObservableList.add(new KategorijaColumn(kategorija));
            }
            kategorija.refresh();
        }
    }

    public void dodaj_kategoriju() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../dialog/kategorija.fxml")).load();
        DodajKategorija dodajKategorija = loader.getController();

    }

}
