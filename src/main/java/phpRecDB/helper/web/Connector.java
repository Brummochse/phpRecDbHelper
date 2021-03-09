package phpRecDB.helper.web;


import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import phpRecDB.helper.util.LogUtil;

import java.util.logging.Level;

public class Connector {

    private final Credential credential;

    public Connector(Credential credential) {
        this.credential=credential;
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
        return ClientBuilder.newBuilder().register(loggingFeature).register(authFeature).build();
    }

    public void addSnapshot(String recordUrl, Screenshot snapshot) {
        Invocation.Builder invocationBuilder = setupUpdateRecordInvocation(recordUrl);
        Response response = invocationBuilder.put(Entity.entity(snapshot, MediaType.APPLICATION_JSON));
    }
}
