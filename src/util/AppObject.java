package util;

import com.google.common.eventbus.EventBus;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by ilija.tomic on 6/13/2016.
 */
public class AppObject {

    private static AppObject appObject;

    private DatabaseReference dbRef;

    private MojRestoran mojRestoran = null;
    private Korisnik ulogovanKorisnik;

    private EventBus eventBus;

    public static AppObject getInstance() {
        if (appObject == null)
            appObject = new AppObject();

        return appObject;
    }

    public MojRestoran getMojRestoran() {
        return mojRestoran;
    }

    public void initDatabase() throws FileNotFoundException {

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(new FileInputStream("MojRestoran-858c9197063f.json"))
                .setDatabaseUrl("https://project-3585542348729097062.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(options);

        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mojRestoran = dataSnapshot.getValue(MojRestoran.class);
                System.out.println("FIREBASE_DATABASE_DOWNLOAD");
                checkLogin("ilija@email.com", "qwe");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mojRestoran = null;
            }
        });
    }

    public void updateDatabase() {
        dbRef.setValue(mojRestoran);
    }

    public boolean checkLogin(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            for (Korisnik korisnik : mojRestoran.getKorisnikArrayList()) {
                if (korisnik.getEmail().equals(email) && korisnik.getPassword().equals(password)) {
                    setUlogovanKorisnik(korisnik);
                    return true;
                }
            }
        }
        return false;
    }

    public void setUlogovanKorisnik(Korisnik ulogovanKorisnik) {
        this.ulogovanKorisnik = ulogovanKorisnik;
    }

    public Korisnik getUlogovanKorisnik() {
        return ulogovanKorisnik;
    }


    public EventBus getEventBus() {
        if (eventBus == null)
            eventBus = new EventBus();
        return eventBus;
    }

    public Korisnik getKorisnikById(String idKorisnik) {
        for (Korisnik korisnik : mojRestoran.getKorisnikArrayList())
            if (korisnik.getId().equals(idKorisnik))
                return korisnik;
        return null;
    }

    public Sto getStoById(String idSto) {
        for (Sto sto : mojRestoran.getStoArrayList())
            if (sto.getId().equals(idSto))
                return sto;
        return null;
    }

    public Kategorija getKategorijaById(String idKategorija) {
        for (Kategorija kategorija : mojRestoran.getKategorijaArrayList())
            if (kategorija.getId().equals(idKategorija))
                return kategorija;
        return null;
    }

    public Podkategorija getPodkategorijaById(String idPodkategorija) {
        for (Podkategorija podkategorija : mojRestoran.getPodkategorijaArrayList())
            if (podkategorija.getId().equals(idPodkategorija))
                return podkategorija;
        return null;
    }

    public Stavka getStavkaById(String idStavka) {
        for (Stavka stavka : mojRestoran.getStavkaArrayList())
            if (stavka.getId().equals(idStavka))
                return stavka;
        return null;
    }

    public boolean checkIfUserExists(String email) {
        for (Korisnik korisnik : mojRestoran.getKorisnikArrayList())
            if (korisnik.getEmail().equals(email))
                return true;
        return false;
    }

    public boolean checkIfOtherUserExist(String email, String id) {
        for (Korisnik korisnik : getMojRestoran().getKorisnikArrayList())
            if (korisnik.getEmail().equals(email) && !korisnik.getId().equals(id))
                return true;
        return false;
    }

    public boolean checkIfKategorijaExists(String naziv) {
        for (Kategorija kategorija : mojRestoran.getKategorijaArrayList())
            if (kategorija.getNaziv().equals(naziv))
                return true;
        return false;
    }

    public boolean checkIfPodkategorijaExists(String naziv) {
        for (Podkategorija podkategorija : mojRestoran.getPodkategorijaArrayList())
            if (podkategorija.getNaziv().equals(naziv))
                return true;
        return false;
    }

    public boolean checkIfStavkaExists(String naziv) {
        for (Stavka stavka : mojRestoran.getStavkaArrayList())
            if (stavka.getNaziv().equals(naziv))
                return true;
        return false;
    }

    public boolean checkIfStoExists(String broj) {
        for (Sto sto : mojRestoran.getStoArrayList())
            if (sto.getBroj() == Integer.parseInt(broj))
                return true;
        return false;
    }
}
