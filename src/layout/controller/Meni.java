package layout.controller;

import com.google.common.eventbus.Subscribe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Kategorija;
import model.table.KategorijaColumn;
import util.AppObject;
import util.event.DataChange;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ilija.tomic on 6/17/2016.
 */
public class Meni implements Initializable {

    @FXML
    private Button dodaj;
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

}
