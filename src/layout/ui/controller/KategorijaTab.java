package layout.ui.controller;

import com.google.common.eventbus.Subscribe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import layout.dialog.DodajKategorija;
import model.Kategorija;
import model.table.KategorijaColumn;
import util.AppObject;
import util.event.DataChange;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ilija.tomic on 6/29/2016.
 */
public class KategorijaTab implements Initializable {

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

        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                pretraga();
            }
        });
    }


    @Subscribe
    public void handleDataChange(DataChange dataChange) {
        if (DataChange.Type.KATEGORIJA.equals(dataChange.getType())) {
            search.clear();
            pretraga();
        }
    }

    public void dodaj_kategoriju() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../dialog/kategorija.fxml"));
        loader.load();
        DodajKategorija dodajKategorija = loader.getController();
        dodajKategorija.show(null);
    }

    private void pretraga() {
        if (search.getText().isEmpty()) {
            kategorijaObservableList.clear();
            for (Kategorija kategorija : AppObject.getInstance().getMojRestoran().getKategorijaArrayList()) {
                kategorijaObservableList.add(new KategorijaColumn(kategorija));
            }
            kategorija.refresh();
        } else {
            kategorijaObservableList.clear();
            for (Kategorija kategorija : AppObject.getInstance().getMojRestoran().getKategorijaArrayList()) {
                if (kategorija.getNaziv().toLowerCase().startsWith(search.getText().toLowerCase()))
                    kategorijaObservableList.add(new KategorijaColumn(kategorija));
            }
            kategorija.refresh();
        }
    }
}
