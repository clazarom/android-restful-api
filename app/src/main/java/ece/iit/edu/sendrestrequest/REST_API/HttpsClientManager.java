package ece.iit.edu.sendrestrequest.REST_API;

import android.util.Log;

import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Class to manage https and ssl connections - deal with servers certificates
 *
 * Retrofit tutorial to add ssl certificates: https://proandroiddev.com/configuring-retrofit-2-client-in-android-130455eaccbd
 */
public class HttpsClientManager {

    private static final String TAG = "HTTPS_CLIENT";
    private OkHttpClient okHttpClient;
    private SSLContext sslContext;
    private SSLSocketFactory sslSocketFactory;
    private static final String SSL_ALGORITHM = "TLS";

    public HttpsClientManager(){
        if (initializeTrustManager())
            initializeHttpsClient();

    }
    /**
     * Initialize the SSL Context and SSL Socket factory, to establish the trus manager of the class
     * @return success
     */
    private boolean initializeTrustManager(){
        try {
            // Install the trust manager we defined in the class
            sslContext = SSLContext.getInstance(SSL_ALGORITHM);
            sslContext.init(null, new TrustManager[]{allowAllTrustCertificate},
                    new java.security.SecureRandom());
            // Create an ssl socket factory
            sslSocketFactory = sslContext.getSocketFactory();
            return true;
        }catch (NoSuchAlgorithmException  e) {
            Log.e(TAG, "Error initializing SSL trust manager: "+e);
        }catch (KeyManagementException e) {
            Log.e(TAG, "Error initializing SSL trust manager: "+e);
        }
        return false;
    }

    private void initializeHttpsClient(){
        okHttpClient = new OkHttpClient();
        okHttpClient = okHttpClient.newBuilder()
                .sslSocketFactory(sslSocketFactory, allowAllTrustCertificate)
                .hostnameVerifier(hostnameVerifier).build();

    }

    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }

    //Host name verifier
    private final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            Log.d(TAG, "Host name verfier: "+ hostname +" - "+session.getProtocol());
            boolean value = true;
            //TODO:Some logic to verify your host and set value
            return value;
        }
    };

    //Trust Manager
    private final X509TrustManager  allowAllTrustCertificate = new X509TrustManager() {
        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] chain,
                String authType) throws CertificateException {
            //TODO
            Log.d(TAG, "Check client trusted ("+authType+"): "+ chain.length);
            for (int i = 0; i < chain.length; i++) {
                Log.d(TAG, "Certificate: "+ chain[i].getType());
            }

        }

        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] chain,
                String authType) throws CertificateException {
            Log.d(TAG, "Check server trusted ("+authType+"): "+ chain.length);
            for (java.security.cert.X509Certificate certificate: chain) {
                Log.d(TAG, "Certificate: "+ certificate.getType());
            }

        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }
    };
}
