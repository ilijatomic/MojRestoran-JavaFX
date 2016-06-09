package ui;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.MojRestoran;
import util.Constants;
import util.StageUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../layout/login.fxml"));
        primaryStage.setTitle(Constants.APP_TITLE);
        primaryStage.setScene(StageUtils.createMainScene(root));
        primaryStage.show();

        getFirebase();
    }

    private void getFirebase() throws FileNotFoundException, InterruptedException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(new FileInputStream("MojRestoran-858c9197063f.json"))
                .setDatabaseUrl("https://project-3585542348729097062.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(options);

        final MojRestoran[] mojRestoran = new MojRestoran[1];

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mojRestoran[0] = dataSnapshot.getValue(MojRestoran.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Thread.sleep(5000);
        mojRestoran[0].getKorisnikArrayList().get(0).setBrTel("qwerty");


        dbRef.setValue(mojRestoran[0]);
    }

}
