package model;

import java.util.ArrayList;

/**
 * Created by Ilija on 1/2/2016.
 */
public class Racun {

    private String id;
    private Long datum;
    private double cena;
    private ArrayList<NaruceneStavke> naplaceneStavke;

    public Racun() {
    }

    public Racun(String id, Long datum, double cena, ArrayList<NaruceneStavke> naplaceneStavke) {
        this.id = id;
        this.datum = datum;
        this.cena = cena;
        this.naplaceneStavke = naplaceneStavke;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDatum() {
        return datum;
    }

    public void setDatum(Long datum) {
        this.datum = datum;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public ArrayList<NaruceneStavke> getNaplaceneStavke() {
        if (naplaceneStavke == null)
            naplaceneStavke = new ArrayList<>();
        return naplaceneStavke;
    }

    public void setNaplaceneStavke(ArrayList<NaruceneStavke> naplaceneStavke) {
        this.naplaceneStavke = naplaceneStavke;
    }

    @Override
    public String toString() {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer
                .append("Ukupno racun: ")
                .append(cena)
                .append("\n");
        for (NaruceneStavke temp : naplaceneStavke) {
            stringBuffer
                    .append("\t")
                    .append(temp.getKolicina())
                    .append("x ")
                    .append(temp.getStavka().getNaziv())
                    .append("\n");
        }

        return stringBuffer.toString();
    }
}
