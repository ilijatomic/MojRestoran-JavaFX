package layout.ui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Kategorija;
import model.Narudzbina;
import model.table.NarudzbinaColumn;
import util.AppObject;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Ilija on 7/1/2016.
 */
public class Narudzbine implements Initializable {

    @FXML
    private TextField sime;
    @FXML
    private TextField sprezime;
    @FXML
    private TextField semail;
    @FXML
    private TextField sbrtel;
    @FXML
    private ChoiceBox<Kategorija> skategorija;
    @FXML
    private ChoiceBox<Kategorija> spokategorija;
    @FXML
    private ChoiceBox<Kategorija> sstavka;
    @FXML
    private ChoiceBox<Kategorija> ssto;
    @FXML
    private DatePicker sdatumod;
    @FXML
    private DatePicker sdatumdo;

    @FXML
    private TableColumn<NarudzbinaColumn, String> sto;
    @FXML
    private TableColumn<NarudzbinaColumn, String> ime;
    @FXML
    private TableColumn<NarudzbinaColumn, String> prezime;
    @FXML
    private TableColumn<NarudzbinaColumn, String> email;
    @FXML
    private TableColumn<NarudzbinaColumn, String> datum;
    @FXML
    private TableColumn<NarudzbinaColumn, String> ukupno;
    @FXML
    private TableColumn<NarudzbinaColumn, Button> detalji;
    @FXML
    private TableView<NarudzbinaColumn> narudzbine;

    private ObservableList<NarudzbinaColumn> narudzbinaColumnObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sto.setStyle("-fx-alignment: CENTER-LEFT;");
        sto.setCellValueFactory(new PropertyValueFactory<>("broj"));
        ime.setStyle("-fx-alignment: CENTER-LEFT;");
        ime.setCellValueFactory(new PropertyValueFactory<>("ime"));
        prezime.setStyle("-fx-alignment: CENTER-LEFT;");
        prezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        email.setStyle("-fx-alignment: CENTER-LEFT;");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        datum.setStyle("-fx-alignment: CENTER-LEFT;");
        datum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        ukupno.setStyle("-fx-alignment: CENTER-LEFT;");
        ukupno.setCellValueFactory(new PropertyValueFactory<>("ukupno"));
        detalji.setStyle("-fx-alignment: CENTER;");
        detalji.setCellValueFactory(new PropertyValueFactory<>("detalji"));

        narudzbinaColumnObservableList.clear();
        for (Narudzbina temp : AppObject.getInstance().getMojRestoran().getNarudzbinaArrayList()) {
            narudzbinaColumnObservableList.add(new NarudzbinaColumn(temp));
        }
        narudzbine.setItems(narudzbinaColumnObservableList);

        sdatumod.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                System.out.println(sdatumod.getValue());
            }
        });

    }

    public void ponisti() {

    }
}
