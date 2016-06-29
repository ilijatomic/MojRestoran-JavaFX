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
import layout.dialog.ErrorDialog;
import model.Kategorija;
import model.Podkategorija;
import util.AppObject;
import util.event.DataChange;

import java.io.IOException;

/**
 * Created by Ilija on 6/26/2016.
 */
public class KategorijaColumn {

    private String id;
    private String naziv;

    private Button izmeni;
    private Button obrisi;

    public KategorijaColumn(Kategorija kategorija) {
        id = kategorija.getId();
        naziv = kategorija.getNaziv();

        izmeni = new Button("", new ImageView(new Image(getClass().getResourceAsStream("../../edit.png"))));
        izmeni.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout/dialog/kategorija.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DodajKategorija dodajKategorija = loader.getController();
                dodajKategorija.show(id);
            }
        });
        obrisi = new Button("", new ImageView(new Image(getClass().getResourceAsStream("../../delete.png"))));
        obrisi.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (Podkategorija podkategorija : AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList()) {
                    if (podkategorija.getKategorija().getId().equals(id)) {
                        ErrorDialog.show("DodajKategorija!", "DodajKategorija ne moze biti obrisana jer sadrzi podkategorije!");
                        return;
                    }
                }
                if (AlertDialog.show("Obrisi kategoriju", "Da li ste sigurni da zelite da obriste kategoriju?").get() == ButtonType.OK) {
                    for (Kategorija kategorija : AppObject.getInstance().getMojRestoran().getKategorijaArrayList()) {
                        if (kategorija.getId().equals(id)) {
                            AppObject.getInstance().getMojRestoran().getKategorijaArrayList().remove(kategorija);
                            AppObject.getInstance().updateDatabase();
                            AppObject.getInstance().getEventBus().post(new DataChange(DataChange.Type.KATEGORIJA));
                            break;
                        }
                    }
                }
            }
        });
    }

    public String getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public Button getIzmeni() {
        return izmeni;
    }

    public Button getObrisi() {
        return obrisi;
    }
}
