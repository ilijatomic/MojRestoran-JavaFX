package util;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by ilija.tomic on 6/9/2016.
 */
public class StageUtils {
    public static Scene createMainScene(Parent root) {
        return new Scene(root, 1024, 768);
    }

    public static Stage getStageFromEvent(Event event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
}
