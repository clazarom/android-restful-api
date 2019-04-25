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

    @Expose
    @SerializedName("first_name")
    private String firstName;

    @Expose
    @SerializedName("last_name")
    private String lastName;

    @Expose
    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @Expose
    @SerializedName("gender")
    private String gender;

    public void setTokenID(String tokenID) { this.tokenID = tokenID; }
    public void setEmail(String email){this.email=email;}
    public void setUserID(String userID){this.userID=userID;}
    public void setFirstName(String firstName){this.firstName=firstName;}
    public void setLastName(String lastName){this.lastName=lastName;}
    public void setDateOfBirth(String date){this.dateOfBirth=dateOfBirth;}
    public void setGender(String gender){this.gender=gender;}

    public String getTokenID(){return tokenID;}
    public String getEmail(){return email;}
    public String getUserID(){return userID;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getDateOfBirth(){return dateOfBirth;}
    public String getGender(){return gender;}

    @Override
    public String toString(){
        return "UserCredentials:{user= "+userID+", email="+email+"}";
    }
}
