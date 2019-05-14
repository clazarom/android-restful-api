package ece.iit.edu.sendrestrequest.REST_API;

import android.util.Log;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ece.iit.edu.sendrestrequest.BuildConfig;

/**
 * Secure Rest API client which encrypts every message using JWT
 * https://jwt.io/
 * https://java.jsonwebtoken.io/
 * Hidding your api keys: https://medium.com/code-better/hiding-api-keys-from-your-android-repository-b23f5598b906
 *
 * @author Caterina Lazaro
 * @version 1.0 May 2019
 */
public class JWTEncoder {
    private static final String TAG = "JWT_CLIENT";
    private String secretKeyValue =  BuildConfig.apikey;
    private SecretKey secretKey;
    //Claims in the JWT messages
    private static final String ID_TOKEN = "idtoken";
    private static final String EMAIL = "email";
    private static final String DATA = "data";

    //Algorithm: HMAC256 - Jwt HS256,	HMAC with SHA-256HMAC
    private String algorithmType = "HmacSHA256";
    private SignatureAlgorithm algorithmHS = SignatureAlgorithm.HS256;
    private String header;

    public JWTEncoder(){
        //Create an algorithm instance with the secretKey
        header = "{ \"alg\": \""+algorithmHS.toString()+"\" }";
        //TODO generate key
        //secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Log.d(TAG, "SecretKey: "+ secretKeyValue);

        byte[] decodedKey = Base64.getDecoder().decode(secretKeyValue);
         secretKey =  new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithmType);
        Log.d(TAG, "SecretKey: "+ Base64.getEncoder().encodeToString(secretKey.getEncoded()));
//        Base64.getDecoder().decode(Base64.getEncoder().encodeToString(secretKey.getEncoded()));

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
            signedToken = Jwts.builder()
                    .signWith(secretKey)
                    .claim(ID_TOKEN, idToken)
                    .claim(EMAIL, email)
                    .claim(DATA, data)
                    .compact();

        } catch (Exception exception){
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

        if (!token.isEmpty()) {
            try {
                Jws<Claims> decoded = Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(token);
                //Get id, email and data
                String id = decoded.getBody().get(ID_TOKEN).toString();
                String email = decoded.getBody().get(EMAIL).toString();
                String data = decoded.getBody().get(DATA).toString();
                Log.d(TAG, "Decoded successfully: " + id + " , " + email + ", " + data);

            } catch (JwtException ex) {

                // we *cannot* use the JWT as intended by its creator
            }

        }
        return decodedPayload;

    }
}
