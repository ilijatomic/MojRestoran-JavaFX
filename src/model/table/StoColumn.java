package model.table;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import layout.dialog.AlertDialog;
import layout.dialog.DodajSto;
import model.Kategorija;
import model.Sto;
import util.AppObject;
import util.event.DataChange;

import java.io.IOException;

/**
 * Created by Ilija on 7/1/2016.
 */
public class StoColumn {

    private String id;
    private int broj;

    private Button izmeni;
    private Button obrisi;

    public StoColumn(Sto sto) {
        id = sto.getId();
        broj = sto.getBroj();

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
                DodajSto dodajKategorija = loader.getController();
                dodajKategorija.show(id);
            }
        });
        obrisi = new Button("", new ImageView(new Image(getClass().getResourceAsStream("../../delete.png"))));
        obrisi.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (AlertDialog.show("Obrisi sto", "Da li ste sigurni da zelite da obriste sto?").get() == ButtonType.OK) {
                    for (Kategorija kategorija : AppObject.getInstance().getMojRestoran().getKategorijaArrayList()) {
                        if (kategorija.getId().equals(id)) {
                            AppObject.getInstance().getMojRestoran().getKategorijaArrayList().remove(kategorija);
                            AppObject.getInstance().updateDatabase();
                            AppObject.getInstance().getEventBus().post(new DataChange(DataChange.Type.STO));
                            break;
                        }
                    }
                }
            }
        });
    }

    public int getBroj() {
        return broj;
    }

    public Button getIzmeni() {
        return izmeni;
    }

    public Button getObrisi() {
        return obrisi;
    }
}
