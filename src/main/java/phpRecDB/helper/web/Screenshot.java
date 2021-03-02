package phpRecDB.helper.web;

public class Screenshot {
    private String screenshot="";

    public Screenshot(String base64imgData) {
        screenshot=base64imgData;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }
}
