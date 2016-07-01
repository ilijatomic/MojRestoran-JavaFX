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
import model.Stavka;
import util.AppObject;
import util.Validation;
import util.event.DataChange;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by Ilija on 6/30/2016.
 */
public class DodajStavka implements Initializable {

    @FXML
    private Label kategorija;
    private Kategorija selectedKat;
    @FXML
    private ChoiceBox<Podkategorija> podkategorija;
    private Podkategorija selectedPod;
    @FXML
    private TextField naziv;
    @FXML
    private TextField cena;
    @FXML
    private DialogPane dialogPane;

    private ArrayList<TextField> textFields = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        podkategorija.getItems().add(new Podkategorija("", "podkategorija", new Kategorija("", "kategorija")));
        for (Podkategorija podkat : AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList()) {
            podkategorija.getItems().add(podkat);
        }
        podkategorija.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Podkategorija>() {
            @Override
            public void changed(ObservableValue<? extends Podkategorija> observable, Podkategorija oldValue, Podkategorija newValue) {
                selectedPod = podkategorija.getValue();
                selectedKat = podkategorija.getValue().getKategorija();
                kategorija.setText(selectedKat.getNaziv());
            }
        });
        podkategorija.getSelectionModel().selectFirst();
    }

    public void show(String id) {
        Stavka stavka = null;
        if (id != null) {
            stavka = AppObject.getInstance().getStavkaById(id);
            naziv.setText(stavka.getNaziv());
            cena.setText(String.valueOf(stavka.getCena()));
            for (Podkategorija temp : podkategorija.getItems()) {
                if (temp.getId().equals(stavka.getPodkategorija().getId())) {
                    podkategorija.getSelectionModel().select(temp);
                }
            }
        }

        Dialog stavkaDialog = new Dialog();
        stavkaDialog.setDialogPane(dialogPane);
        stavkaDialog.setTitle(id != null ? "Izmeni stavku" : "Dodaj stavku");
        stavkaDialog.getDialogPane().getButtonTypes().clear();
        stavkaDialog.getDialogPane().getButtonTypes().add(new ButtonType("Odustani", ButtonBar.ButtonData.CANCEL_CLOSE));
        stavkaDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        stavkaDialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textFields.clear();
                textFields.add(naziv);
                textFields.add(cena);
                if (!Validation.checkTextFields(textFields)) {
                    event.consume();
                }
                if (podkategorija.getValue().getId().equals("")) {
                    podkategorija.setStyle("-fx-border-color: red");
                    event.consume();
                }
                try {
                    Double.parseDouble(cena.getText());
                } catch (NumberFormatException e) {
                    cena.setStyle("-fx-border-color: red");
                    event.consume();
                }
                if (AppObject.getInstance().checkIfStavkaExists(naziv.getText())) {
                    event.consume();
                    ErrorDialog.show("Stavka", "Stavka sa unetim imenom vec postoji!");
                }
            }
        });

        Optional result = stavkaDialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (id == null) {
                String uuid = UUID.randomUUID().toString();
                String nazivstavke = naziv.getText();
                Double cenastavke = Double.parseDouble(cena.getText());
                AppObject.getInstance().getMojRestoran().getStavkaArrayList().add(new Stavka(uuid, nazivstavke, cenastavke, selectedPod));
                AppObject.getInstance().updateDatabase();
            } else {
                stavka.setNaziv(naziv.getText());
                stavka.setPodkategorija(selectedPod);
                stavka.setCena(Double.parseDouble(cena.getText()));
                AppObject.getInstance().updateDatabase();
            }
            AppObject.getInstance().getEventBus().post(new DataChange(DataChange.Type.STAVKA));
        }
    }
}
