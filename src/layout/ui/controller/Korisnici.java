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
import layout.dialog.DodajKorisnika;
import model.Korisnik;
import model.table.KorisnikColum;
import util.AppObject;
import util.event.DataChange;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ikac on 6/30/16.
 */
public class Korisnici implements Initializable, ChangeListener<String> {

    private static final String[] tips = {"tip", "admin", "konobar"};

    @FXML
    private ChoiceBox<String> stip;
    @FXML
    private TextField sime;
    @FXML
    private TextField sprezime;
    @FXML
    private TextField semail;
    @FXML
    private TextField sbrtel;

    @FXML
    private TableColumn<KorisnikColum, String> ime;
    @FXML
    private TableColumn<KorisnikColum, String> prezime;
    @FXML
    private TableColumn<KorisnikColum, String> email;
    @FXML
    private TableColumn<KorisnikColum, String> brtel;
    @FXML
    private TableColumn<KorisnikColum, String> tip;
    @FXML
    private TableColumn<KorisnikColum, Button> izmeni;
    @FXML
    private TableColumn<KorisnikColum, String> obrisi;
    @FXML
    private TableView<KorisnikColum> korisnici;

    private ObservableList<KorisnikColum> korisnikColumObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stip.getItems().addAll(tips);
        stip.getSelectionModel().selectedItemProperty().addListener(this);
        stip.getSelectionModel().selectFirst();

        ime.setStyle("-fx-alignment: CENTER-LEFT;");
        ime.setCellValueFactory(new PropertyValueFactory<>("ime"));
        prezime.setStyle("-fx-alignment: CENTER-LEFT;");
        prezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        email.setStyle("-fx-alignment: CENTER-LEFT;");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        brtel.setStyle("-fx-alignment: CENTER-LEFT;");
        brtel.setCellValueFactory(new PropertyValueFactory<>("brtel"));
        tip.setStyle("-fx-alignment: CENTER-LEFT;");
        tip.setCellValueFactory(new PropertyValueFactory<>("tip"));
        izmeni.setStyle("-fx-alignment: CENTER;");
        izmeni.setCellValueFactory(new PropertyValueFactory<>("izmeni"));
        obrisi.setStyle("-fx-alignment: CENTER;");
        obrisi.setCellValueFactory(new PropertyValueFactory<>("obrisi"));

        korisnikColumObservableList.clear();
        for (Korisnik temp : AppObject.getInstance().getMojRestoran().getKorisnikArrayList()) {
            korisnikColumObservableList.add(new KorisnikColum(temp));
        }
        korisnici.setItems(korisnikColumObservableList);
        korisnici.setPlaceholder(new Label("Ne postoje korisnici"));

        AppObject.getInstance().getEventBus().register(this);

        sime.textProperty().addListener(this);
        sprezime.textProperty().addListener(this);
        semail.textProperty().addListener(this);
        sbrtel.textProperty().addListener(this);
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        pretraga();
    }

    @Subscribe
    public void handleDataChange(DataChange dataChange) {
        if (DataChange.Type.KORISNIK.equals(dataChange.getType())) {
            sime.clear();
            sprezime.clear();
            semail.clear();
            sbrtel.clear();
            stip.getSelectionModel().selectFirst();
            pretraga();
        }
    }

    public void dodaj_korisnika() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../dialog/korisnik.fxml"));
        loader.load();
        DodajKorisnika dialog = loader.getController();
        dialog.show(null);
    }

    private void pretraga() {
        korisnikColumObservableList.clear();
        for (Korisnik temp : AppObject.getInstance().getMojRestoran().getKorisnikArrayList()) {
            if (!stip.getValue().equals(temp.getTip()) && !stip.getValue().equals("tip"))
                continue;
            if (!temp.getIme().toLowerCase().startsWith(sime.getText().toLowerCase()))
                continue;
            if (!temp.getPrezime().toLowerCase().startsWith(sprezime.getText().toLowerCase()))
                continue;
            if (!temp.getEmail().toLowerCase().startsWith(semail.getText().toLowerCase()))
                continue;
            if (!temp.getBrTel().toLowerCase().startsWith(sbrtel.getText().toLowerCase()))
                continue;

            korisnikColumObservableList.add(new KorisnikColum(temp));
        }

        korisnici.refresh();
    }
}
