package ece.iit.edu.sendrestrequest.JWT_REST_API;

import android.util.Log;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import ece.iit.edu.sendrestrequest.BuildConfig;

/**
 * Secure Rest API client which encrypts every message using JWT
 * https://jwt.io/
 * https://github.com/auth0/java-jwt
 * Hidding your api keys: https://medium.com/code-better/hiding-api-keys-from-your-android-repository-b23f5598b906
 *
 * @author Caterina Lazaro
 * @version 1.0 May 2019
 */
public class SecureRestClient {
    private static final String TAG = "JWT_CLIENT";
    private String secretKey =  BuildConfig.apikey;

    //Claims in the JWT messages
    private static final String ID_TOKEN = "idtoken";
    private static final String EMAIL = "email";
    private static final String DATA = "data";

    //Algorithm: HMAC256 - Jwt HS256,	HMAC with SHA-256HMAC
    private Algorithm algorithmHS;

    public SecureRestClient(){
        //Create an algorithm instance with the secretKey
        algorithmHS = Algorithm.HMAC256(secretKey);
    }

    /**
     * Create a signed tokent with the class algorithm
     * @param idToken idToken
     * @param email email
     * @param data additionalData
     * @return signedToken
     */
    public String createASignedToken(String idToken, String email, String data){
        String signedToken ="";
        try {
            signedToken = JWT.create()
                    .withIssuer("auth0")
                    .withClaim(ID_TOKEN, idToken)
                    .withClaim(EMAIL, email)
                    .withClaim(DATA, data)
                    .sign(algorithmHS);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            Log.d(TAG, "Exception when creating a sign token: "+exception);
        }
        return signedToken;
    }

    /**
     * Decode the JWT signed object
     * @param token encodedToken
     * @return decodedToken
     */
    public String verifyAToken(String token){
        String decodedPayload = "";
        try {
            JWTVerifier verifier = JWT.require(algorithmHS)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            jwt.getHeader();
            decodedPayload = jwt.getPayload();
            jwt.getSignature();
            //Get claims
            jwt.getClaim(ID_TOKEN);
            jwt.getClaim(EMAIL);
            jwt.getClaim(DATA);
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            Log.d(TAG, "Invalid signature/claims: "+exception);

        }
        return decodedPayload;
    }
}
