package ece.iit.edu.sendrestrequest.REST_API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCredentialsResponse {
    @Expose
    @SerializedName("response")
    private boolean response;

    @Expose
    @SerializedName("user_credentials")
    private UserCredentials userCredentials;

    public void setResponse(boolean response){this.response = response;}
    public void setUserCredentials(UserCredentials userCredentials){this.userCredentials = userCredentials;}

    public boolean getResponse(){return  response;}
    public UserCredentials getUserCredentials(){return userCredentials;}
}
