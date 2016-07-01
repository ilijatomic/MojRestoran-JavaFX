package layout.ui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import model.table.NarudzbinaColumn;
import util.AppObject;

import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
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
    private ChoiceBox<Podkategorija> spodkategorija;
    @FXML
    private ChoiceBox<Stavka> sstavka;
    @FXML
    private ChoiceBox<Sto> ssto;
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
        narudzbine.setPlaceholder(new Label("Ne postoje narudzbine"));

        skategorija.getItems().add(new Kategorija(null, "kategorija"));
        for (Kategorija kat : AppObject.getInstance().getMojRestoran().getKategorijaArrayList()) {
            skategorija.getItems().add(kat);
        }
        skategorija.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Kategorija>() {
            @Override
            public void changed(ObservableValue<? extends Kategorija> observable, Kategorija oldValue, Kategorija newValue) {
                pretraga();
            }
        });
        skategorija.getSelectionModel().selectFirst();

        spodkategorija.getItems().add(new Podkategorija(null, "podkategorija", null));
        for (Podkategorija temp : AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList()) {
            spodkategorija.getItems().add(temp);
        }
        spodkategorija.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Podkategorija>() {
            @Override
            public void changed(ObservableValue<? extends Podkategorija> observable, Podkategorija oldValue, Podkategorija newValue) {
                pretraga();
            }
        });
        spodkategorija.getSelectionModel().selectFirst();

        sstavka.getItems().add(new Stavka(null, "stavka", 0, null));
        for (Stavka temp : AppObject.getInstance().getMojRestoran().getStavkaArrayList()) {
            sstavka.getItems().add(temp);
        }
        sstavka.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Stavka>() {
            @Override
            public void changed(ObservableValue<? extends Stavka> observable, Stavka oldValue, Stavka newValue) {
                pretraga();
            }
        });
        sstavka.getSelectionModel().selectFirst();

        ssto.getItems().add(new Sto(null, 0));
        for (Sto temp : AppObject.getInstance().getMojRestoran().getStoArrayList()) {
            ssto.getItems().add(temp);
        }
        ssto.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Sto>() {
            @Override
            public void changed(ObservableValue<? extends Sto> observable, Sto oldValue, Sto newValue) {
                pretraga();
            }
        });
        ssto.getSelectionModel().selectFirst();

        sdatumod.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                pretraga();
            }
        });

        sdatumdo.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                pretraga();
            }
        });
    }

    private void pretraga() {
        if (skategorija.getValue() == null || spodkategorija.getValue() == null || sstavka.getValue() == null || ssto.getValue() == null)
            return;

        narudzbinaColumnObservableList.clear();
        for (Narudzbina temp : AppObject.getInstance().getMojRestoran().getNarudzbinaArrayList()) {
            if (!sime.getText().isEmpty() && !temp.getKorisnik().getIme().toLowerCase().startsWith(sime.getText().toLowerCase()))
                continue;
            if (!sprezime.getText().isEmpty() && !temp.getKorisnik().getPrezime().toLowerCase().startsWith(sprezime.getText().toLowerCase()))
                continue;
            if (!semail.getText().isEmpty() && !temp.getKorisnik().getEmail().toLowerCase().startsWith(semail.getText().toLowerCase()))
                continue;
            if (!sbrtel.getText().isEmpty() && !temp.getKorisnik().getBrTel().toLowerCase().startsWith(sbrtel.getText().toLowerCase()))
                continue;
            if (ssto.getValue().getId() != null && temp.getSto().getBroj() != ssto.getValue().getBroj())
                continue;
            if (skategorija.getValue().getId() != null && !checkNarudzbinaKategorija(temp))
                continue;
            if (spodkategorija.getValue().getId() != null && !checkNarudzbinaPodkategorija(temp))
                continue;
            if (sstavka.getValue().getId() != null && !checkNarudzbinaStavka(temp))
                continue;
            if (sdatumod.getValue() != null && new Date(temp.getDatum()).before(java.sql.Date.valueOf(sdatumod.getValue().atTime(0, 0).toLocalDate())))
                continue;
            if (sdatumdo.getValue() != null && new Date(temp.getDatum()).after(java.sql.Date.valueOf(sdatumdo.getValue().plusDays(1))))
                continue;

            narudzbinaColumnObservableList.add(new NarudzbinaColumn(temp));
        }
        narudzbine.refresh();
    }

    public void ponisti() {
        sime.clear();
        sprezime.clear();
        semail.clear();
        sbrtel.clear();
        skategorija.getSelectionModel().selectFirst();
        spodkategorija.getSelectionModel().selectFirst();
        sstavka.getSelectionModel().selectFirst();
        ssto.getSelectionModel().selectFirst();
        sdatumod.setValue(null);
        sdatumdo.setValue(null);

        pretraga();
    }

    private boolean checkNarudzbinaKategorija(Narudzbina narudzbina) {
        for (Racun racun : narudzbina.getRacunArrayList())
            for (NaruceneStavke naruceneStavke : racun.getNaplaceneStavke())
                if (naruceneStavke.getStavka().getPodkategorija().getKategorija().getId().equals(skategorija.getValue().getId()))
                    return true;
        return false;
    }

    private boolean checkNarudzbinaPodkategorija(Narudzbina narudzbina) {
        for (Racun racun : narudzbina.getRacunArrayList())
            for (NaruceneStavke naruceneStavke : racun.getNaplaceneStavke())
                if (naruceneStavke.getStavka().getPodkategorija().getId().equals(spodkategorija.getValue().getId()))
                    return true;
        return false;
    }

    private boolean checkNarudzbinaStavka(Narudzbina narudzbina) {
        for (Racun racun : narudzbina.getRacunArrayList())
            for (NaruceneStavke naruceneStavke : racun.getNaplaceneStavke())
                if (naruceneStavke.getStavka().getId().equals(sstavka.getValue().getId()))
                    return true;
        return false;
    }
}
