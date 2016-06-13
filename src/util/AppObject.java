package util;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import model.Korisnik;
import model.MojRestoran;

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
}
