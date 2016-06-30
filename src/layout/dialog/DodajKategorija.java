package layout.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Kategorija;
import util.AppObject;
import util.Validation;
import util.event.DataChange;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by ilija.tomic on 6/28/2016.
 */
public class DodajKategorija {

    @FXML
    private TextField naziv;
    @FXML
    private DialogPane dialogPane;

    private ArrayList<TextField> textFields = new ArrayList<>();

    public void show(String id) {
        Kategorija kategorija = null;
        if (id != null) {
            kategorija = AppObject.getInstance().getKategorijaById(id);
            naziv.setText(kategorija.getNaziv());
        }
        Dialog kategorijaDialog = new Dialog();
        kategorijaDialog.setDialogPane(dialogPane);
        kategorijaDialog.setTitle(id != null ? "Izmeni kategoriju" : "Dodaj kategoriju");
        kategorijaDialog.getDialogPane().getButtonTypes().clear();
        kategorijaDialog.getDialogPane().getButtonTypes().add(new ButtonType("Odustani", ButtonBar.ButtonData.CANCEL_CLOSE));
        kategorijaDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        kategorijaDialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textFields.clear();
                textFields.add(naziv);
                if (!Validation.checkTextFields(textFields)) {
                    event.consume();
                }
            }
        });

        Optional result = kategorijaDialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (id == null) {
                String uuid = UUID.randomUUID().toString();
                String nazivKategorije = naziv.getText();
                AppObject.getInstance().getMojRestoran().getKategorijaArrayList().add(new Kategorija(uuid, nazivKategorije));
                AppObject.getInstance().updateDatabase();
            } else {
                kategorija.setNaziv(naziv.getText());
                AppObject.getInstance().updateDatabase();
            }
            AppObject.getInstance().getEventBus().post(new DataChange(DataChange.Type.KATEGORIJA));
        }
    }
}
