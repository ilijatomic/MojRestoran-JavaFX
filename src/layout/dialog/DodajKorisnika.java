package layout.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Kategorija;
import model.Korisnik;
import model.Podkategorija;
import util.AppObject;
import util.Validation;
import util.event.DataChange;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by ikac on 6/30/16.
 */
public class DodajKorisnika implements Initializable {

    private static final String[] tips = {"tip", "admin", "konobar"};

    @FXML
    private ChoiceBox<String> tip;
    @FXML
    private TextField ime;
    @FXML
    private TextField prezime;
    @FXML
    private TextField email;
    @FXML
    private TextField brtel;
    @FXML
    private PasswordField sifra;
    @FXML
    private DialogPane dialogPane;

    private ArrayList<TextField> textFields = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tip.getItems().addAll(tips);
        tip.getSelectionModel().selectFirst();
    }

    public void show(String id) {
        Korisnik korisnik = null;
        if (id != null) {
            korisnik = AppObject.getInstance().getKorisnikById(id);
            ime.setText(korisnik.getIme());
            prezime.setText(korisnik.getPrezime());
            email.setText(korisnik.getEmail());
            brtel.setText(korisnik.getBrTel());
            sifra.setText(korisnik.getPassword());
            for (String temp : tip.getItems()) {
                if (temp.equals(korisnik.getTip()))
                    tip.getSelectionModel().select(temp);
            }
        }

        Dialog korisnikDialog = new Dialog();
        korisnikDialog.setDialogPane(dialogPane);
        korisnikDialog.setTitle(id != null ? "Izmeni korisnika" : "Dodaj korisnika");
        korisnikDialog.getDialogPane().getButtonTypes().clear();
        korisnikDialog.getDialogPane().getButtonTypes().add(new ButtonType("Odustani", ButtonBar.ButtonData.CANCEL_CLOSE));
        korisnikDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        korisnikDialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textFields.clear();
                textFields.add(email);
                textFields.add(sifra);
                if (!Validation.checkTextFields(textFields)) {
                    event.consume();
                }
                if (tip.getValue().equals("tip")) {
                    tip.setStyle("-fx-border-color: red");
                    event.consume();
                }
            }
        });

        Optional result = korisnikDialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (id == null) {
                String uuid = UUID.randomUUID().toString();
                AppObject.getInstance().getMojRestoran().getKorisnikArrayList().add(new Korisnik(uuid, ime.getText(), prezime.getText(), brtel.getText(), email.getText(), sifra.getText(), tip.getValue()));
                AppObject.getInstance().updateDatabase();
            } else {
                korisnik.setIme(ime.getText());
                korisnik.setPassword(sifra.getText());
                korisnik.setPrezime(prezime.getText());
                korisnik.setEmail(email.getText());
                korisnik.setBrTel(brtel.getText());
                korisnik.setTip(tip.getValue());
                AppObject.getInstance().updateDatabase();
            }
            AppObject.getInstance().getEventBus().post(new DataChange(DataChange.Type.KORISNIK));
        }
    }
}
