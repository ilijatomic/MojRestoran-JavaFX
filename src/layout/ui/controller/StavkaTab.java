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
import layout.dialog.DodajStavka;
import model.Kategorija;
import model.Podkategorija;
import model.Stavka;
import model.table.StavkaColumn;
import util.AppObject;
import util.event.DataChange;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ilija.tomic on 6/29/2016.
 */
public class StavkaTab implements Initializable {

    @FXML
    private ChoiceBox<Kategorija> kategorija;
    private Kategorija selectedKategorija;
    @FXML
    private ChoiceBox<Podkategorija> podkategorija;
    private Podkategorija selectedPodkategorija;
    @FXML
    private TextField search;

    @FXML
    private TableColumn<StavkaColumn, String> nazivkat;
    @FXML
    private TableColumn<StavkaColumn, String> nazivpod;
    @FXML
    private TableColumn<StavkaColumn, String> nazivstavke;
    @FXML
    private TableColumn<StavkaColumn, String> cena;
    @FXML
    private TableColumn<StavkaColumn, Button> izmeni;
    @FXML
    private TableColumn<StavkaColumn, Button> obrisi;
    @FXML
    private TableView<StavkaColumn> stavka;

    private ObservableList<StavkaColumn> stavkaColumnObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        kategorija.getItems().add(new Kategorija(null, "kategorija"));
        for (Kategorija kat : AppObject.getInstance().getMojRestoran().getKategorijaArrayList()) {
            kategorija.getItems().add(kat);
        }
        kategorija.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Kategorija>() {
            @Override
            public void changed(ObservableValue<? extends Kategorija> observable, Kategorija oldValue, Kategorija newValue) {
                selectedKategorija = kategorija.getValue();
                podkategorija.getItems().clear();
                podkategorija.getItems().add(new Podkategorija(null, "podkategorija", null));
                if (selectedKategorija.getId() != null) {
                    for (Podkategorija podkat : AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList()) {
                        if (podkat.getKategorija().getId().equals(selectedKategorija.getId()))
                            podkategorija.getItems().add(podkat);
                    }
                } else {
                    for (Podkategorija podkat : AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList()) {
                        podkategorija.getItems().add(podkat);
                    }
                }
                podkategorija.getSelectionModel().selectFirst();
                pretraga();
            }
        });
        kategorija.getSelectionModel().selectFirst();

        podkategorija.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Podkategorija>() {
            @Override
            public void changed(ObservableValue<? extends Podkategorija> observable, Podkategorija oldValue, Podkategorija newValue) {
                selectedPodkategorija = podkategorija.getValue();
                // TODO try to implement this
                /*if (selectedPodkategorija.getId() != null) {
                    for (Kategorija kat : kategorija.getItems()) {
                        if (kat.getId() != null && selectedPodkategorija.getKategorija().getId().equals(kat.getId()))
                            kategorija.getSelectionModel().select(kat);
                    }
                } else {
                    kategorija.getSelectionModel().selectFirst();
                }*/
                pretraga();
            }
        });
        podkategorija.getSelectionModel().selectFirst();

        nazivkat.setStyle("-fx-alignment: CENTER-LEFT;");
        nazivkat.setCellValueFactory(new PropertyValueFactory<>("nazivkat"));
        nazivpod.setStyle("-fx-alignment: CENTER-LEFT;");
        nazivpod.setCellValueFactory(new PropertyValueFactory<>("nazivpod"));
        nazivstavke.setStyle("-fx-alignment: CENTER-LEFT;");
        nazivstavke.setCellValueFactory(new PropertyValueFactory<>("nazivstavke"));
        cena.setStyle("-fx-alignment: CENTER-LEFT;");
        cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        izmeni.setStyle("-fx-alignment: CENTER;");
        izmeni.setCellValueFactory(new PropertyValueFactory<>("izmeni"));
        obrisi.setStyle("-fx-alignment: CENTER;");
        obrisi.setCellValueFactory(new PropertyValueFactory<>("obrisi"));

        stavkaColumnObservableList.clear();
        for (Stavka stavka : AppObject.getInstance().getMojRestoran().getStavkaArrayList()) {
            stavkaColumnObservableList.add(new StavkaColumn(stavka));
        }

        stavka.setItems(stavkaColumnObservableList);
        stavka.setPlaceholder(new Label("Ne postoje stavke"));
        AppObject.getInstance().getEventBus().register(this);

        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                pretraga();
            }
        });
    }

    private void pretraga() {
        if (selectedKategorija == null || selectedPodkategorija == null)
            return;

        if (search.getText().isEmpty() && selectedKategorija.getId() == null && selectedPodkategorija.getId() == null) {
            stavkaColumnObservableList.clear();
            for (Stavka temp : AppObject.getInstance().getMojRestoran().getStavkaArrayList()) {
                stavkaColumnObservableList.add(new StavkaColumn(temp));
            }
            stavka.refresh();
        } else {
            stavkaColumnObservableList.clear();
            for (Stavka temp : AppObject.getInstance().getMojRestoran().getStavkaArrayList()) {
                if (!temp.getNaziv().toLowerCase().startsWith(search.getText().toLowerCase()))
                    continue;
                if (!temp.getPodkategorija().getKategorija().getId().equals(selectedKategorija.getId()) && selectedKategorija.getId() != null)
                    continue;
                if (!temp.getPodkategorija().getId().equals(selectedPodkategorija.getId()) && selectedPodkategorija.getId() != null)
                    continue;
                stavkaColumnObservableList.add(new StavkaColumn(temp));
            }
            stavka.refresh();
        }
    }

    public void dodaj_stavku() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../dialog/stavka.fxml"));
        loader.load();
        DodajStavka dodajPodkategorija = loader.getController();
        dodajPodkategorija.show(null);
    }

    @Subscribe
    public void handleDataChange(DataChange dataChange) {
        if (DataChange.Type.STAVKA.equals(dataChange.getType())) {
            search.clear();
            pretraga();
        }
        if (DataChange.Type.PODKATEGORIJA.equals(dataChange.getType())) {
            podkategorija.getItems().clear();
            podkategorija.getItems().add(new Podkategorija(null, "podkategorija", null));
            for (Podkategorija k : AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList()) {
                podkategorija.getItems().add(k);
            }
            podkategorija.getSelectionModel().selectFirst();
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
