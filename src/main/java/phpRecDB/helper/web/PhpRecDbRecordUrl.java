package phpRecDB.helper.web;

public class PhpRecDbRecordUrl {
    private int recordId;
    private String apiUrl;

    public PhpRecDbRecordUrl(String recordUrl) {
        parse(recordUrl);
    }

    private void parse(String recordUrl) {
        int lastSlash = recordUrl.lastIndexOf("/");
        String recordId = recordUrl.substring(lastSlash + 1);
        this.apiUrl = recordUrl.substring(0, lastSlash+1);

        this.recordId = Integer.parseInt(recordId);
    }

    public int getRecordId() {
        return recordId;
    }

    public String getApiUrl() {
        return apiUrl;
    }
}
