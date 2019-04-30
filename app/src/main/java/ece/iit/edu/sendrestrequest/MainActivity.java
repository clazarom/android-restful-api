package ece.iit.edu.sendrestrequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ece.iit.edu.sendrestrequest.REST_API.RestClient;

//Retrofit example project:
// https://square.github.io/retrofit/
// https://android.jlelse.eu/rest-api-on-android-made-simple-or-how-i-learned-to-stop-worrying-and-love-the-rxjava-b3c2c949cad4
public class MainActivity extends AppCompatActivity {

    RestClient client;

    //Results
    public static TextView user0ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new RestClient(this);

        //Test user 0
        user0ID= findViewById(R.id.user0_userid);
        findViewById(R.id.testButton).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                client.getUserDebug(0);
            }
        });

        //Get UserCredentials button
        findViewById(R.id.retrieveUserButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){client.requestUserInServer(RestClient.TOKEN_ID);}
        });

    }

    @Override
    protected void onDestroy(){
        client.destroyDisposables();

        super.onDestroy();
    }
}
