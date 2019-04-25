package ece.iit.edu.sendrestrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCredentials {
    @Expose
    @SerializedName("token_id")
    private String tokenID;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("user_id")
    private String userID;

    public void setTokenID(String tokenID) { this.tokenID = tokenID; }
    public void setEmail(String email){this.email=email;}
    public void setUserID(String userID){this.userID=userID;}

    public String getTokenID(){return tokenID;}
    public String getEmail(){return email;}
    public String getUserID(){return userID;}
}
