package model.table;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import layout.dialog.AlertDialog;
import layout.dialog.DodajStavka;
import model.Stavka;
import util.AppObject;
import util.event.DataChange;

import java.io.IOException;

/**
 * Created by Ilija on 6/30/2016.
 */
public class StavkaColumn {

    private String id;
    private String nazivkat;
    private String nazivpod;
    private String nazivstavke;
    private Double cena;

    private Button izmeni;
    private Button obrisi;

    public StavkaColumn(Stavka stavka) {
        id = stavka.getId();
        nazivstavke = stavka.getNaziv();
        cena = stavka.getCena();
        nazivkat = stavka.getPodkategorija().getKategorija().getNaziv();
        nazivpod = stavka.getPodkategorija().getNaziv();

        izmeni = new Button("", new ImageView(new Image(getClass().getResourceAsStream("../../edit.png"))));
        izmeni.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout/dialog/stavka.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DodajStavka dodajStavka = loader.getController();
                dodajStavka.show(id);
            }
        });
        obrisi = new Button("", new ImageView(new Image(getClass().getResourceAsStream("../../delete.png"))));
        obrisi.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (AlertDialog.show("Obrisi stavku", "Da li ste sigurni da zelite da obriste stavku?").get() == ButtonType.OK) {
                    for (Stavka stavka : AppObject.getInstance().getMojRestoran().getStavkaArrayList()) {
                        if (stavka.getId().equals(id)) {
                            AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList().remove(stavka);
                            AppObject.getInstance().updateDatabase();
                            AppObject.getInstance().getEventBus().post(new DataChange(DataChange.Type.STAVKA));
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

    public String getNazivkat() {
        return nazivkat;
    }

    public String getNazivpod() {
        return nazivpod;
    }

    public String getNazivstavke() {
        return nazivstavke;
    }

    public Double getCena() {
        return cena;
    }

    public Button getIzmeni() {
        return izmeni;
    }

    public Button getObrisi() {
        return obrisi;
    }
}
