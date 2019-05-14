package ece.iit.edu.sendrestrequest.REST_API;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import ece.iit.edu.sendrestrequest.MainActivity;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Sample RESTful API client to send request and receive the corresponding responses
 */
public class RestClient {
    private static String TAG = "REST_CLIENT";

    //Retrofit fields
    Retrofit retrofit;
    ApiInterface restAPI;
    static final String BASE_URL = "http://google.com"; //TODO add your url
    static final String API_KEY = "11223344";
    public static final String TOKEN_ID="aadsfaewubfuadiubfasdufbaudsfgbadusfybudfgbv";
    //Manage disposables
    CompositeDisposable compositeDisposable;

    //UI reference
    Activity activity;

    //Encrypt: JWT;
    JWTEncoder jwtEncoder;

    public RestClient(Activity activity){
        //UI reference
        this.activity = activity;
        //Initialize retrofit
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //API Interface
        restAPI = retrofit.create(ApiInterface.class);
        compositeDisposable = new CompositeDisposable();

        //JWT
        jwtEncoder = new JWTEncoder();
    }

    public boolean getUserDebug(int user){
        //Rest request
        //Single observer:
        Single<UserCredentials> userReq = restAPI.getUserDebug(user, API_KEY);
            //Call REST API
            userReq.subscribeOn(Schedulers.io()) //do all work on background (io)
                .observeOn(AndroidSchedulers.mainThread()) //onSuccess and onError are called on the main thread
                .subscribe(new SingleObserver<UserCredentials>(){
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(UserCredentials user) {
                        Log.d(TAG, "Request success: "+user.getUserID());
                        final UserCredentials successfulUser = user;
                        //Update UI
                        activity.runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                MainActivity.user0ID.setText(successfulUser.getUserID());
                            }
                        });

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Request error: "+e);

                    }
                }) ;


        return true;
    }

    public boolean requestUserInServer(String tokenID){
        UserCredentialsRequest request = new UserCredentialsRequest(tokenID);
        Single<UserCredentialsResponse> userCredentials = restAPI.requestUser(API_KEY, request);
        UserCredentials user = new UserCredentials();

        //Call server API
        userCredentials.subscribeOn(Schedulers.io()) //do all work on background (io)
            .observeOn(AndroidSchedulers.mainThread()) //onSuccess and onError are called on the main thread
            .subscribe(new SingleObserver<UserCredentialsResponse>(){
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onSuccess(UserCredentialsResponse user) {
                    Log.d(TAG, "Request success: "+user.getResponse());
                    if (user.getResponse()){
                        //There is a user in the system! we can get the data now

                    } else{
                        //There is no existent User, we will have to send the user credentials
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, "Request error: "+e);

                }
            }) ;

        return true;
    }

    public void testJWT(){
        String encoded = jwtEncoder.createASignedToken("1234", "user@emai.com", "{data1:22, data3:3}");
        String decoded = jwtEncoder.verifyAToken(encoded);
    }

    public void destroyDisposables(){
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
