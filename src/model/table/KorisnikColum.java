package model.table;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import layout.dialog.AlertDialog;
import layout.dialog.DodajKategorija;
import layout.dialog.DodajKorisnika;
import layout.dialog.ErrorDialog;
import model.Kategorija;
import model.Korisnik;
import model.Podkategorija;
import util.AppObject;
import util.event.DataChange;

import java.io.IOException;

/**
 * Created by ikac on 6/30/16.
 */
public class KorisnikColum {

    private String id;
    private String ime;
    private String prezime;
    private String email;
    private String brtel;
    private String tip;

    private Button izmeni;
    private Button obrisi;

    public KorisnikColum(Korisnik korisnik) {
        id = korisnik.getId();
        ime = korisnik.getIme();
        prezime = korisnik.getPrezime();
        email = korisnik.getEmail();
        brtel = korisnik.getBrTel();
        tip = korisnik.getTip();

        izmeni = new Button("", new ImageView(new Image(getClass().getResourceAsStream("../../edit.png"))));
        izmeni.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout/dialog/korisnik.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DodajKorisnika dialog = loader.getController();
                dialog.show(id);
            }
        });
        obrisi = new Button("", new ImageView(new Image(getClass().getResourceAsStream("../../delete.png"))));
        obrisi.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (AlertDialog.show("Obrisi korisnika", "Da li ste sigurni da zelite da obriste korisnika?").get() == ButtonType.OK) {
                    for (Korisnik temp : AppObject.getInstance().getMojRestoran().getKorisnikArrayList()) {
                        if (temp.getId().equals(id)) {
                            AppObject.getInstance().getMojRestoran().getKorisnikArrayList().remove(temp);
                            AppObject.getInstance().updateDatabase();
                            AppObject.getInstance().getEventBus().post(new DataChange(DataChange.Type.KORISNIK));
                            break;
                        }
                    }
                }
            }
        });
    }
}
