package phpRecDB.helper.web;


import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import phpRecDB.helper.util.LogUtil;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;

public class Connector {

    private final Credential credential;

    public Connector(Credential credential) {
        this.credential = credential;
    }


//    public void get() {
//
//        Client client = ClientBuilder.newClient();
//        WebTarget webTarget = client.target("http://localhost/test/phpRecDB/index.php/api/info");
//
//
//        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
//
//        Info response = invocationBuilder.get(Info.class);
//        System.out.println("Response body is " + response.getLength());
//
//    }

    public RecordDescription getRecordInfo(String recordUrl) {
        PhpRecDbRecordUrl phpRecDbRecordUrl = new PhpRecDbRecordUrl(recordUrl);

        WebTarget webTarget = getClient().target(phpRecDbRecordUrl.getApiUrl() + "{id}")
                .resolveTemplate("id", phpRecDbRecordUrl.getRecordId());

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        return invocationBuilder.get(RecordDescription.class);
    }

    public void updateRecord(String recordUrl, AbstractRecord recordInfo) {
        Invocation.Builder invocationBuilder = setupUpdateRecordInvocation(recordUrl);
        Response response = invocationBuilder.put(Entity.entity(recordInfo, MediaType.APPLICATION_JSON));
    }

    public Invocation.Builder setupUpdateRecordInvocation(String recordUrl) {
        PhpRecDbRecordUrl phpRecDbRecordUrl = new PhpRecDbRecordUrl(recordUrl);

        WebTarget webTarget = getClient().target(phpRecDbRecordUrl.getApiUrl() + "{id}")
                .resolveTemplate("id", phpRecDbRecordUrl.getRecordId());
        return webTarget.request(MediaType.APPLICATION_JSON);
    }

    public Client getClient() {

        HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic(credential.getUsername(), credential.getPassword());
        Feature loggingFeature = new LoggingFeature(LogUtil.logger, Level.INFO, null, null);
        ClientBuilder clientBuilder = ClientBuilder.newBuilder().register(loggingFeature).register(authFeature);

        if (credential.isIgnoreSsl()) {
            try {
                SSLContext sslcontext = SSLContext.getInstance("TLS");

                sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
                    }

                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }}, new java.security.SecureRandom());

                clientBuilder = clientBuilder.sslContext(sslcontext).hostnameVerifier((s1, s2) -> true);
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                // init failed, what a pity. so stay with normal ssl behaviour
            }
        }
        return clientBuilder.build();
    }

    public void addSnapshot(String recordUrl, Screenshot snapshot) {
        Invocation.Builder invocationBuilder = setupUpdateRecordInvocation(recordUrl);
        Response response = invocationBuilder.put(Entity.entity(snapshot, MediaType.APPLICATION_JSON));
    }
}
