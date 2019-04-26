package ece.iit.edu.sendrestrequest;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    //BASE_URL/user/{n}?api_key=<<api_key>>&language=en-US
    @GET("user/{user_n}")
    Single<UserCredentials> getUserDebug(
            @Path("user_n") int n,
            @Query("api_key") String apiKey);

    //Check if user in in the server
    @GET("user_credentials")
    Single<Boolean> isUserSet(
            @Query("api_key") String apiKey);

}
