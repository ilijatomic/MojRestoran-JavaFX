package layout.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.AppObject;
import util.StageUtils;

import java.io.IOException;

/**
 * Created by ilija.tomic on 6/8/2016.
 */
public class Login {

    @FXML
    private TextField login_email;
    @FXML
    private PasswordField login_password;
    @FXML
    private Label login_error;

    public void login(ActionEvent event) throws IOException {
        String email = login_email.getText();
        String password = login_password.getText();

        if (AppObject.getInstance().checkLogin(email, password)) {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/main_form.fxml"));
            StageUtils.getStageFromEvent(event).setScene(StageUtils.createMainScene(root));
        } else {
            login_error.setVisible(true);
        }

    }
}
