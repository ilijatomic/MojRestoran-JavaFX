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
import layout.dialog.DodajSto;
import model.Kategorija;
import model.Sto;
import model.table.KategorijaColumn;
import model.table.StoColumn;
import util.AppObject;
import util.event.DataChange;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ilija on 7/1/2016.
 */
public class Stolovi implements Initializable {

    @FXML
    private TextField search;

    @FXML
    private TableColumn<StoColumn, String> broj;
    @FXML
    private TableColumn<StoColumn, Button> izmeni;
    @FXML
    private TableColumn<StoColumn, Button> obrisi;
    @FXML
    private TableView<StoColumn> stolovi;

    private ObservableList<StoColumn> stoColumnObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        broj.setStyle("-fx-alignment: CENTER-LEFT;");
        broj.setCellValueFactory(new PropertyValueFactory<>("broj"));
        izmeni.setStyle("-fx-alignment: CENTER;");
        izmeni.setCellValueFactory(new PropertyValueFactory<>("izmeni"));
        obrisi.setStyle("-fx-alignment: CENTER;");
        obrisi.setCellValueFactory(new PropertyValueFactory<>("obrisi"));
        stoColumnObservableList.clear();
        for (Sto temp : AppObject.getInstance().getMojRestoran().getStoArrayList()) {
            stoColumnObservableList.add(new StoColumn(temp));
        }

        stolovi.setItems(stoColumnObservableList);
        stolovi.setPlaceholder(new Label("Ne postoje stolovi"));

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
        if (DataChange.Type.STO.equals(dataChange.getType())) {
            search.clear();
            pretraga();
        }
    }

    public void dodaj_sto() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../dialog/sto.fxml"));
        loader.load();
        DodajSto dodajDialog = loader.getController();
        dodajDialog.show(null);
    }

    private void pretraga() {
        if (search.getText().isEmpty()) {
            stoColumnObservableList.clear();
            for (Sto temp : AppObject.getInstance().getMojRestoran().getStoArrayList()) {
                stoColumnObservableList.add(new StoColumn(temp));
            }
            stolovi.refresh();
        } else {
            stoColumnObservableList.clear();
            for (Sto temp : AppObject.getInstance().getMojRestoran().getStoArrayList()) {
                if (String.valueOf(temp.getBroj()).startsWith(search.getText()))
                    stoColumnObservableList.add(new StoColumn(temp));
            }
            stolovi.refresh();
        }
    }
}
