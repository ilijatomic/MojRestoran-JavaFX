package layout.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Narudzbina;
import model.Racun;
import util.AppObject;
import util.Validation;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ilija on 7/1/2016.
 */
public class NarudzbinaDetlaji {

    @FXML
    private Label narudzbinasto;
    @FXML
    private Label datum;
    @FXML
    private Label konobar;
    @FXML
    private ListView<Racun> detalji;
    @FXML
    private DialogPane dialogPane;

    private String id;
    private Narudzbina narudzbina;


    public void show(String id) {
        this.id = id;
        narudzbina = AppObject.getInstance().getNarudzbinaById(this.id);

        Dialog stavkaDialog = new Dialog();
        stavkaDialog.setDialogPane(dialogPane);
        stavkaDialog.setTitle("Detalji narudzbine");
        stavkaDialog.getDialogPane().getButtonTypes().clear();
        stavkaDialog.getDialogPane().getButtonTypes().add(new ButtonType("Zatvori", ButtonBar.ButtonData.CANCEL_CLOSE));
        stavkaDialog.show();
        String datum = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(narudzbina.getDatum()));
        narudzbinasto.setText("Narudzbina za stolom " + narudzbina.getSto().getBroj() + "\nVreme: " + datum);
        double ukupno = 0;
        for (Racun temp : narudzbina.getRacunArrayList()) {
            ukupno += temp.getCena();
        }
        this.datum.setText("Ukupno: " + ukupno);
        konobar.setText(narudzbina.getKorisnik().getIme() + " " + narudzbina.getKorisnik().getPrezime() + " " + narudzbina.getKorisnik().getBrTel() + "\n" + narudzbina.getKorisnik().getEmail());
        detalji.getItems().addAll(narudzbina.getRacunArrayList());
        detalji.refresh();
    }
}
