package phpRecDB.helper.web;

public class RecordDescription {
    private String recordDescription;
    private String concertDescription;
    private String artist;

    public String getConcertDescription() {
        return concertDescription;
    }

    public void setConcertDescription(String concertDescription) {
        this.concertDescription = concertDescription;
    }

    public String getRecordDescription() {
        return recordDescription;
    }

    public void setRecordDescription(String recordDescription) {
        this.recordDescription = recordDescription;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


    @Override
    public String toString() {
        return "<html>"+artist+"<br>"+concertDescription+"<br>"+recordDescription+"</html>";
    }
}
