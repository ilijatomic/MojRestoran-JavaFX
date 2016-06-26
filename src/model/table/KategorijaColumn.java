package model.table;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Kategorija;
import model.Podkategorija;
import util.AppObject;
import util.event.DataChange;

/**
 * Created by Ilija on 6/26/2016.
 */
public class KategorijaColumn {

    private String id;
    private String naziv;

    private Button izmeni;
    private Button obrisi;

    public KategorijaColumn(Kategorija kategorija) {
        this.id = kategorija.getId();
        this.naziv = kategorija.getNaziv();

        izmeni = new Button("", new ImageView(new Image(getClass().getResourceAsStream("../../edit.png"))));
        izmeni.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO edit dialog
            }
        });
        obrisi = new Button("", new ImageView(new Image(getClass().getResourceAsStream("../../delete.png"))));
        obrisi.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.showAndWait();
                for (Podkategorija podkategorija : AppObject.getInstance().getMojRestoran().getPodkategorijaArrayList()) {
                    if (podkategorija.getKategorija().getId().equals(id)) {

                        return;
                    }
                }
                for (Kategorija kategorija : AppObject.getInstance().getMojRestoran().getKategorijaArrayList()) {
                    if (kategorija.getId().equals(id)) {
                        //AppObject.getInstance().getMojRestoran().getKategorijaArrayList().remove(kategorija);
                        AppObject.getInstance().updateDatabase();
                        AppObject.getInstance().getEventBus().post(new DataChange(DataChange.Type.KATEGORIJA));
                        break;
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
