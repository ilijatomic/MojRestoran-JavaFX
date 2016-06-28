package layout.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Created by ilija.tomic on 6/27/2016.
 */
public class AlertDialog {

    public static Optional<ButtonType> show(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getButtonTypes().add(new ButtonType("Zatvori", ButtonBar.ButtonData.CANCEL_CLOSE));
        return alert.showAndWait();
    };
}
