package layout.dialog;

import javafx.scene.control.Alert;

/**
 * Created by ilija.tomic on 6/27/2016.
 */
public class ErrorDialog {

    public static void show(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    ;
}
