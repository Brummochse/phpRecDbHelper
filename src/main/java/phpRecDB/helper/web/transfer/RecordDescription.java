package phpRecDB.helper.web.transfer;

public class RecordDescription {
    private String recordDescription;
    private String concertDescription;
    private String artist;
    private String semioticSystem;

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

    public String getSemioticSystem() {
        return semioticSystem;
    }

    public void setSemioticSystem(String semioticSystem) {
        this.semioticSystem = semioticSystem;
    }

    @Override
    public String toString() {
        return "<html>"+artist+"<br>"+concertDescription+"<br>"+recordDescription+"<br>"+semioticSystem.toUpperCase()+"</html>";
    }
}
