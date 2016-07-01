package model.table;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import layout.dialog.DodajKategorija;
import layout.dialog.NarudzbinaDetlaji;
import model.Narudzbina;
import model.Racun;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ilija on 7/1/2016.
 */
public class NarudzbinaColumn {

    private int broj;
    private String id;
    private String ime;
    private String prezime;
    private String email;
    private String datum;
    private Date date;
    private String brtel;
    private Double ukupno;

    private Button detalji;

    private ArrayList<Racun> racunArrayList;

    public NarudzbinaColumn(Narudzbina narudzbina) {
        id = narudzbina.getId();
        broj = narudzbina.getSto().getBroj();
        ime = narudzbina.getKorisnik().getIme();
        prezime = narudzbina.getKorisnik().getPrezime();
        email = narudzbina.getKorisnik().getEmail();
        brtel = narudzbina.getKorisnik().getBrTel();
        date = new Date(narudzbina.getDatum());
        datum = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
        racunArrayList = narudzbina.getRacunArrayList();

        izracunajUkupno();

        detalji = new Button("", new ImageView(new Image(getClass().getResourceAsStream("../../edit.png"))));
        detalji.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout/dialog/narudzbina_detalji.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                NarudzbinaDetlaji detalji = loader.getController();
                detalji.show(id);
            }
        });
    }

    private void izracunajUkupno() {
        ukupno = 0.0;

        for (Racun temp : racunArrayList) {
            ukupno += temp.getCena();
        }
    }

    public int getBroj() {
        return broj;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getEmail() {
        return email;
    }

    public String getDatum() {
        return datum;
    }

    public String getBrtel() {
        return brtel;
    }

    public Double getUkupno() {
        return ukupno;
    }

    public Button getDetalji() {
        return detalji;
    }

    public ArrayList<Racun> getRacunArrayList() {
        return racunArrayList;
    }
}
