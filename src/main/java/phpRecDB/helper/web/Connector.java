package phpRecDB.helper.web;


import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.logging.LoggingFeature;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Connector {


    public void get() {

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost/test/phpRecDB/index.php/api/info");


        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        Info response = invocationBuilder.get(Info.class);
        System.out.println("Response body is " + response.getLength());

    }

    public void create() {


        Logger logger = Logger.getLogger(getClass().getName());

        Feature feature = new LoggingFeature(logger, Level.INFO, null, null);

        Client client = ClientBuilder.newBuilder()
                .register(feature)
                .build();


        WebTarget webTarget = client.target("http://localhost/phpRecDB/index.php/api").path("records");


        Info info = new Info();
        info.setLength(6);


        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(info, MediaType.APPLICATION_JSON));
    }


    public void update(VideoRecord videoRecord) {


//        videoRecord.setSnapShots(snapshots);


        Logger logger = Logger.getLogger(getClass().getName());

        Feature feature = new LoggingFeature(logger, Level.INFO, null, null);

        Client client = ClientBuilder.newBuilder()
                .register(feature)
                .build();

        WebTarget webTarget = client.target("http://localhost/test/phpRecDB/index.php/api/records/{id}")
                .resolveTemplate("id", "493");


        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.put(Entity.entity(videoRecord, MediaType.APPLICATION_JSON));
    }

    public void getRecordDescription(int recordId) {
        Client client = ClientBuilder.newClient();

        WebTarget webTarget = client.target("http://localhost/test/phpRecDB/index.php/api/record/{id}")
                .resolveTemplate("id", "493");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        RecordDescription response = invocationBuilder.get(RecordDescription.class);
        System.out.println("concert: " + response.getConcertDescription());
        System.out.println("record: " + response.getRecordDescription());
    }

    public RecordDescription getRecordInfo(String recordUrl) {
        PhpRecDbRecordUrl phpRecDbRecordUrl = new PhpRecDbRecordUrl(recordUrl);

        Client client = ClientBuilder.newClient();

        WebTarget webTarget = client.target(phpRecDbRecordUrl.getApiUrl() + "{id}")
                .resolveTemplate("id", phpRecDbRecordUrl.getRecordId());

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        RecordDescription response = invocationBuilder.get(RecordDescription.class);

        return response;

    }

    public void updateRecord(String recordUrl, RecordInfo recordInfo) {
        Invocation.Builder invocationBuilder = setupUpdateRecodInvocation(recordUrl);
        Response response = invocationBuilder.put(Entity.entity(recordInfo, MediaType.APPLICATION_JSON));
    }

    public Invocation.Builder setupUpdateRecodInvocation(String recordUrl) {
        PhpRecDbRecordUrl phpRecDbRecordUrl = new PhpRecDbRecordUrl(recordUrl);
        Logger logger = Logger.getLogger(getClass().getName());
        Feature feature = new LoggingFeature(logger, Level.INFO, null, null);
        Client client = ClientBuilder.newBuilder().register(feature).build();
        WebTarget webTarget = client.target(phpRecDbRecordUrl.getApiUrl() + "{id}")
                .resolveTemplate("id", phpRecDbRecordUrl.getRecordId());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        return invocationBuilder;
    }

    public void addSnapshot(String recordUrl, Screenshot snapshot) {
        Invocation.Builder invocationBuilder = setupUpdateRecodInvocation(recordUrl);
        Response response = invocationBuilder.put(Entity.entity(snapshot, MediaType.APPLICATION_JSON));
    }
}
