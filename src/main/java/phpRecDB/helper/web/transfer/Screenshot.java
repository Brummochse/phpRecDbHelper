package phpRecDB.helper.web.transfer;

import phpRecDB.helper.media.data.SemioticSystem;

public class Screenshot {
    private String screenshot="";

    protected SemioticSystem semioticSystem= SemioticSystem.VIDEO;

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

