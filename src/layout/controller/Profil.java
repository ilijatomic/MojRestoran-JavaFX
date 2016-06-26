package layout.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Korisnik;
import util.AppObject;
import util.Validation;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by ilija.tomic on 6/17/2016.
 */
public class Profil implements Initializable {

    @FXML
    private Label profile_email;
    @FXML
    private TextField profil_ime;
    @FXML
    private TextField profil_prezime;
    @FXML
    private TextField profil_tel;
    @FXML
    private TextField profil_sifra;
    @FXML
    private TextField profil_potvrdi;
    @FXML
    private Label profile_obavezna;

    private ArrayList<TextField> textFields = new ArrayList<>();

    public void sacuvaj() {
        textFields.clear();
        textFields.add(profil_ime);
        textFields.add(profil_prezime);
        if (!Validation.checkTextFields(textFields)) {
            profile_obavezna.setText("Obavezna polja!");
            profile_obavezna.setVisible(true);
            return;
        }
        if (!profil_sifra.getText().isEmpty()) {
            if (!profil_sifra.getText().equals(profil_potvrdi.getText())) {
                profile_obavezna.setText("Nova sifra se ne poklapa!");
                profile_obavezna.setVisible(true);
                return;
            } else {
                AppObject.getInstance().getUlogovanKorisnik().setPassword(profil_sifra.getText());
            }
        }

        AppObject.getInstance().getUlogovanKorisnik().setIme(profil_ime.getText());
        AppObject.getInstance().getUlogovanKorisnik().setPrezime(profil_prezime.getText());
        AppObject.getInstance().getUlogovanKorisnik().setBrTel(profil_tel.getText());

        AppObject.getInstance().updateDatabase();

        profile_obavezna.setText("Podaci uspesno promenjeni");
        profile_obavezna.setStyle("-fx-text-fill: green");
        profile_obavezna.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Korisnik ulogovan = AppObject.getInstance().getUlogovanKorisnik();
        profile_email.setText(ulogovan.getEmail());
        profil_ime.setText(ulogovan.getIme());
        profil_prezime.setText(ulogovan.getPrezime());
        profil_tel.setText(ulogovan.getBrTel());

    }
}
