package layout.dialog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Kategorija;
import util.AppObject;
import util.Validation;
import util.event.DataChange;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by ilija.tomic on 6/28/2016.
 */
public class DodajKategorija implements Initializable {

    @FXML
    private TextField naziv;
    @FXML
    private DialogPane dialogPane;

    private ArrayList<TextField> textFields = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("INIT");

        Dialog kategorija = new Dialog();
        kategorija.setDialogPane(dialogPane);
        kategorija.getDialogPane().getButtonTypes().add(new ButtonType("Odustani", ButtonBar.ButtonData.CANCEL_CLOSE));
        kategorija.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Optional result = kategorija.showAndWait();

        if (result.get() == ButtonType.OK) {
            textFields.clear();
            textFields.add(naziv);
            if (Validation.checkTextFields(textFields)) {
                String randomId = UUID.randomUUID().toString();
                String name = naziv.getText();
                AppObject.getInstance().getMojRestoran().getKategorijaArrayList().add(new Kategorija(randomId, name));
                AppObject.getInstance().updateDatabase();
                AppObject.getInstance().getEventBus().post(new DataChange(DataChange.Type.KATEGORIJA));
            }
        }
    }
}
