package ece.iit.edu.sendrestrequest.JWT_REST_API;

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
    private String secretKey =  BuildConfig.apikey;
}
