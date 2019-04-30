package ece.iit.edu.sendrestrequest.REST_API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * User Credentials Request Body
 *
 * @author Caterina Lazaro
 * @version 1.0
 */
public class UserCredentialsRequest {

    @Expose
    @SerializedName("token_id")
    private String token_id;

    public UserCredentialsRequest(String token_id){
        this.token_id = token_id;
    }
}
