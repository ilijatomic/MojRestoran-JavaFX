package layout.ui.controller;

import com.google.common.eventbus.Subscribe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import layout.dialog.DodajKategorija;
import layout.dialog.DodajPodkategorija;
import model.Kategorija;
import model.Podkategorija;
import model.table.KategorijaColumn;
import model.table.PodkategorijaColumn;
import util.AppObject;
import util.event.DataChange;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ilija.tomic on 6/29/2016.
 */
public class PodkategorijaTab implements Initializable {

    @FXML
    private ChoiceBox<Kategorija> kategorija;
    private Kategorija selectedKategorija;
    @FXML
    private TextField search;

    @FXML
    private TableColumn<PodkategorijaColumn, String> nazivkat;
    @FXML
    private TableColumn<PodkategorijaColumn, String> nazivpod;
    @FXML
    private TableColumn<PodkategorijaColumn, Button> izmeni;
    @FXML
    private TableColumn<PodkategorijaColumn, Button> obrisi;
    @FXML
    private TableView<PodkategorijaColumn> podkategorija;

    private ObservableList<PodkategorijaColumn> podkategorijaObservableList = FXCollections.observableArrayList();

    public void dodaj_podkategoriju() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../dialog/podkategorija.fxml"));
        loader.load();
        DodajPodkategorija dodajPodkategorija = loader.getController();
        dodajPodkategorija.show(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        kategorija.getItems().add(new Kategorija(null, "kategorija"));
        for (Kategorija k : AppObject.getInstance().getMojRestoran().getKategorijaArrayList()) {
            kategorija.getItems().add(k);
        }
        kategorija.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Kategorija>() {
            @Override
            public void changed(ObservableValue<? extends Kategorija> observable, Kategorija oldValue, Kategorija newValue) {
                selectedKategorija = kategorija.getValue();
            }
        });
        kategorija.getSelectionModel().selectFirst();

        nazivkat.setStyle("-fx-alignment: CENTER-LEFT;");
        nazivkat.setCellValueFactory(new PropertyValueFactory<>("nazivkat"));
        nazivpod.setStyle("-fx-alignment: CENTER-LEFT;");
        nazivpod.setCellValueFactory(new PropertyValueFactory<>("nazivpod"));
        izmeni.setStyle("-fx-alignment: CENTER;");
        izmeni.setCellValueFactory(new PropertyValueFactory<>("izmeni"));
        obrisi.setStyle("-fx-alignment: CENTER;");
        obrisi.setCellValueFactory(new PropertyValueFactory<>("obrisi"));

        podkategorijaObservableList.clear();
        for (Podkategorija podkategorija : AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList()) {
            podkategorijaObservableList.add(new PodkategorijaColumn(podkategorija));
        }

        podkategorija.setItems(podkategorijaObservableList);
        podkategorija.setPlaceholder(new Label("Ne postoje podkategorije"));
        AppObject.getInstance().getEventBus().register(this);

        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                pretraga();
            }
        });
    }

    private void pretraga() {
        if (search.getText().isEmpty() && selectedKategorija.getId() == null) {
            podkategorijaObservableList.clear();
            for (Podkategorija podkategorija : AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList()) {
                podkategorijaObservableList.add(new PodkategorijaColumn(podkategorija));
            }
            podkategorija.refresh();
        } else {
            podkategorijaObservableList.clear();
            for (Podkategorija podkategorija : AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList()) {
                if (podkategorija.getNaziv().toLowerCase().startsWith(search.getText().toLowerCase()))
                    podkategorijaObservableList.add(new PodkategorijaColumn(podkategorija));
            }
            podkategorija.refresh();
        }
    }

    @Subscribe
    public void handleDataChange(DataChange dataChange) {
        if (DataChange.Type.PODKATEGORIJA.equals(dataChange.getType())) {
            search.clear();
            pretraga();
        }
        if (DataChange.Type.KATEGORIJA.equals(dataChange.getType())) {
            kategorija.getItems().clear();
            kategorija.getItems().add(new Kategorija(null, "kategorija"));
            for (Kategorija k : AppObject.getInstance().getMojRestoran().getKategorijaArrayList()) {
                kategorija.getItems().add(k);
            }
            kategorija.getSelectionModel().selectFirst();
        }
    }
}
