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
import layout.dialog.DodajPodkategorija;
import layout.dialog.ErrorDialog;
import model.Kategorija;
import model.Podkategorija;
import model.Stavka;
import util.AppObject;
import util.event.DataChange;

import java.io.IOException;

/**
 * Created by Ilija on 6/30/2016.
 */
public class PodkategorijaColumn {
    private String id;
    private String nazivkat;
    private String nazivpod;

    private Button izmeni;
    private Button obrisi;

    public PodkategorijaColumn(Podkategorija podkategorija) {
        id = podkategorija.getId();
        nazivpod = podkategorija.getNaziv();
        nazivkat = podkategorija.getKategorija().getNaziv();

        izmeni = new Button("", new ImageView(new Image(getClass().getResourceAsStream("../../edit.png"))));
        izmeni.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout/dialog/podkategorija.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DodajPodkategorija dodajPodkategorija = loader.getController();
                dodajPodkategorija.show(id);
            }
        });
        obrisi = new Button("", new ImageView(new Image(getClass().getResourceAsStream("../../delete.png"))));
        obrisi.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (Stavka stavka : AppObject.getInstance().getMojRestoran().getStavkaArrayList()) {
                    if (stavka.getPodkategorija().getId().equals(id)) {
                        ErrorDialog.show("Obrisi podategoriju", "DPodkategorija ne moze biti obrisana jer sadrzi stavke!");
                        return;
                    }
                }
                if (AlertDialog.show("Obrisi podkategoriju", "Da li ste sigurni da zelite da obriste podkategoriju?").get() == ButtonType.OK) {
                    for (Podkategorija podkategorija : AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList()) {
                        if (podkategorija.getId().equals(id)) {
                            AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList().remove(podkategorija);
                            AppObject.getInstance().updateDatabase();
                            AppObject.getInstance().getEventBus().post(new DataChange(DataChange.Type.PODKATEGORIJA));
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

    public Button getIzmeni() {
        return izmeni;
    }

    public Button getObrisi() {
        return obrisi;
    }
}
