package phpRecDB.helper.media.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Format {

    private static Map<String, List<String>> formatsWithResolutions = new HashMap<>();

    static {
        formatsWithResolutions.put("PAL", Arrays.asList(
                "352x288",
                "352x576",
                "480x576",
                "544x576",
                "704x576",
                "720x576",
                "768x576",
                "1024x576",
                "960x540"
        ));
        formatsWithResolutions.put("NTSC", Arrays.asList(
                "352x240",
                "352x480",
                "480x480",
                "544x480",
                "640x480",
                "704x480",
                "720x480"
        ));
        formatsWithResolutions.put("HDTV", Arrays.asList(
                "960x720",
                "1280x720",
                "1440x1080",
                "1440x1080"
        ));
        formatsWithResolutions.put("Full HD", Arrays.asList(
                "1920x1080"
        ));
        formatsWithResolutions.put("Ultra HD", Arrays.asList(
                "3840x2160",
                "7680x4320"
        ));
    }

    public static String evaluateFormat(String resolutionToLookFor) {
        Map<String, List<String>> formatsWithResolutions = Format.formatsWithResolutions;
        for (String format : formatsWithResolutions.keySet()) {
            List<String> resolutions = formatsWithResolutions.get(format);
            for (String resolution : resolutions) {
                if (resolution.equals(resolutionToLookFor)) {
                    return format;
                }
            }
        }
        return "";
    }


}
