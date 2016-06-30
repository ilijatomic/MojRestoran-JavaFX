package layout.dialog;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Kategorija;
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
 * Created by ilija.tomic on 6/29/2016.
 */
public class DodajPodkategorija implements Initializable {

    @FXML
    private ChoiceBox<Kategorija> kategorija;
    private Kategorija selectedKat;
    @FXML
    private TextField naziv;
    @FXML
    private DialogPane dialogPane;

    private ArrayList<TextField> textFields = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        kategorija.getItems().add(new Kategorija("", "kategorija"));
        for (Kategorija k : AppObject.getInstance().getMojRestoran().getKategorijaArrayList()) {
            kategorija.getItems().add(k);
        }
        kategorija.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Kategorija>() {
            @Override
            public void changed(ObservableValue<? extends Kategorija> observable, Kategorija oldValue, Kategorija newValue) {
                selectedKat = kategorija.getValue();
            }
        });
        kategorija.getSelectionModel().selectFirst();
    }

    public void show(String id) {
        Podkategorija podkategorija = null;
        if (id != null) {
            podkategorija = AppObject.getInstance().getPodkategorijaById(id);
            naziv.setText(podkategorija.getNaziv());
            for (Kategorija kat : kategorija.getItems()) {
                if (kat.getId().equals(podkategorija.getKategorija().getId()))
                    kategorija.getSelectionModel().select(kat);
            }
        }

        Dialog podkategorijaDialog = new Dialog();
        podkategorijaDialog.setDialogPane(dialogPane);
        podkategorijaDialog.getDialogPane().getButtonTypes().clear();
        podkategorijaDialog.getDialogPane().getButtonTypes().add(new ButtonType("Odustani", ButtonBar.ButtonData.CANCEL_CLOSE));
        podkategorijaDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        podkategorijaDialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textFields.clear();
                textFields.add(naziv);
                if (!Validation.checkTextFields(textFields)) {
                    event.consume();
                }
                if (selectedKat.getId() == null) {
                    kategorija.setStyle("-fx-border-color: red");
                    event.consume();
                }
            }
        });

        Optional result = podkategorijaDialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (id == null) {
                String uuid = UUID.randomUUID().toString();
                String nazivKategorije = naziv.getText();
                AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList().add(new Podkategorija(uuid, nazivKategorije, selectedKat));
                AppObject.getInstance().updateDatabase();
            } else {
                podkategorija.setNaziv(naziv.getText());
                podkategorija.setKategorija(selectedKat);
                AppObject.getInstance().updateDatabase();
            }
            AppObject.getInstance().getEventBus().post(new DataChange(DataChange.Type.PODKATEGORIJA));
        }
    }
}
