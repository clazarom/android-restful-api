package ece.iit.edu.sendrestrequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//Retrofit example project:
// https://square.github.io/retrofit/
// https://android.jlelse.eu/rest-api-on-android-made-simple-or-how-i-learned-to-stop-worrying-and-love-the-rxjava-b3c2c949cad4
public class MainActivity extends AppCompatActivity {

    RestClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new RestClient();

        //Test button
        findViewById(R.id.testButton).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                client.getUserDebug(0);
            }
        });

        //Get UserCredentials button
        findViewById(R.id.retrieveUserButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){client.retrieveUserInServer();}
        });

    }

    @Override
    protected void onDestroy(){
        client.destroyDisposables();

        super.onDestroy();
    }
}
