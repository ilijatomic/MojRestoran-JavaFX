package layout.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Sto;
import util.AppObject;
import util.Validation;
import util.event.DataChange;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Ilija on 7/1/2016.
 */
public class DodajSto {

    @FXML
    private TextField broj;
    @FXML
    private DialogPane dialogPane;

    private ArrayList<TextField> textFields = new ArrayList<>();

    public void show(String id) {
        Sto sto = null;
        if (id != null) {
            sto = AppObject.getInstance().getStoById(id);
            broj.setText(String.valueOf(sto.getBroj()));
        }
        Dialog stoDialog = new Dialog();
        stoDialog.setDialogPane(dialogPane);
        stoDialog.setTitle(id != null ? "Izmeni sto" : "Dodaj sto");
        stoDialog.getDialogPane().getButtonTypes().clear();
        stoDialog.getDialogPane().getButtonTypes().add(new ButtonType("Odustani", ButtonBar.ButtonData.CANCEL_CLOSE));
        stoDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        stoDialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textFields.clear();
                textFields.add(broj);
                if (!Validation.checkTextFields(textFields)) {
                    event.consume();
                }
                if (AppObject.getInstance().checkIfStoExists(broj.getText())) {
                    event.consume();
                    ErrorDialog.show("Sto", "Sto sa unetim brojem vec postoji!");
                }
                try {
                    Double.parseDouble(broj.getText());
                } catch (NumberFormatException e) {
                    broj.setStyle("-fx-border-color: red");
                    event.consume();
                }
            }
        });

        Optional result = stoDialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (id == null) {
                String uuid = UUID.randomUUID().toString();
                AppObject.getInstance().getMojRestoran().getStoArrayList().add(new Sto(uuid, Integer.parseInt(broj.getText())));
                AppObject.getInstance().updateDatabase();
            } else {
                sto.setBroj(Integer.parseInt(broj.getText()));
                AppObject.getInstance().updateDatabase();
            }
            AppObject.getInstance().getEventBus().post(new DataChange(DataChange.Type.STO));
        }
    }

}
