package layout.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import util.Validation;

import java.util.ArrayList;

/**
 * Created by ilija.tomic on 6/17/2016.
 */
public class Profil {

    @FXML
    private TextField profil_potvrdi;

    private ArrayList<TextField> textFields = new ArrayList<>();

    public void sacuvaj() {
        textFields.clear();
        textFields.add(profil_potvrdi);
        Validation.checkTextFields(textFields);
    }
}
