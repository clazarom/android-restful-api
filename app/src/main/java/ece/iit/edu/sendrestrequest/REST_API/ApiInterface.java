package ece.iit.edu.sendrestrequest.REST_API;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit API Interface to interact with the server REST API's
 * Tutorial on Retrofit and type of calls:
 * https://futurestud.io/tutorials/retrofit-synchronous-and-asynchronous-requests
 *
 * @author Caterina Lazaro
 * @version 1.1 April 2019
 */
public interface ApiInterface {

    //SYNCHRONOUS CALLS:
    //{{base_urle}}/user/{n}?api_key=<<api_key>>&language=en-US
    @GET("user/{user_n}")
    Single<UserCredentials> getUserDebug(
            @Path("user_n") int n,
            @Query("api_key") String apiKey);

    //Check if user in in the server
    //{{base_urle}}/?api_key=<<api_key>>&language=en-US
    @GET("user_credentials")
    Call<Single<UserCredentialsResponse>> requestUser(
            @Query("api_key") String apiKey,
            @Body UserCredentialsRequest userRequest);
}
