package ece.iit.edu.sendrestrequest;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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
    static final String BASE_URL = "{ENTER_YOUR_URL}";
    static final String API_KEY = "11223344";
    static final String TOKEN_ID = "andfoidnnva7g7uad7gcuiasd0239u9";

    //Manage disposables
    CompositeDisposable compositeDisposable;

    //UI reference
    Activity activity;

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
    }

    public boolean getUserDebug(int user){
        //Rest request
        //Single observer:
        Single<UserCredentials> userReq = restAPI.getUserDebug(user, API_KEY);
        userReq
                .subscribeOn(Schedulers.io()) //do all work on bakcground (io)
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

    public boolean retrieveUserInServer(){
        Single<Boolean> userCredentials = restAPI.isUserSet(API_KEY);
        UserCredentials user = new UserCredentials();
        user.setTokenID(TOKEN_ID);

        //Call server API
        userCredentials.subscribeOn(Schedulers.io()) //do all work on bakcground (io)
                .observeOn(AndroidSchedulers.mainThread()) //onSuccess and onError are called on the main thread
                .subscribe(new SingleObserver<Boolean>(){
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Boolean userSet) {
                        Log.d(TAG, "Request success: "+userSet);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Request error: "+e);

                    }
                }) ;
        return true;
    }

    public void destroyDisposables(){
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
