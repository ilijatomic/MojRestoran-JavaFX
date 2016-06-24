package util;

import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Created by ilija.tomic on 6/24/2016.
 */
public class Validation {

    public static boolean checkTextFields(ArrayList<TextField> listaPolja) {
        boolean success = true;
        for (TextField textField : listaPolja) {
            if (textField.getText().isEmpty()) {
                textField.setStyle("-fx-text-box-border: red");
                success = false;
            }
        }
        return success;
    }
}
