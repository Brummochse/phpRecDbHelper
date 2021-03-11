package phpRecDB.helper.web.transfer;

import phpRecDB.helper.gui.MediaTitleTableModel;
import phpRecDB.helper.media.data.*;

import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

public class RecordVideo extends AbstractRecord {
    private String aspectRatio = "";
    private int width = 0;
    private int height = 0;
    private double frameRate =0;
    private String format ="";
    //like boolean: 0=false, 1=true
    private int chapters = 0;
    //like boolean: 0=false, 1=true
    private int menu = 0;


    public RecordVideo(MediaTitleTableModel mediaTitleTableModel) {
        this.semioticSystem= SemioticSystem.VIDEO;
        initialiseBaseAttributes(mediaTitleTableModel);
        initialiseSemioticSpecificAttributes(mediaTitleTableModel);
    }

    private void initialiseSemioticSpecificAttributes(MediaTitleTableModel mediaTitleTableModel) {
        List<MediaTitle> selectedMediaTitles = mediaTitleTableModel.getSelectedMediaTitles();

        Set<String> aspectRatios = selectedMediaTitles.stream().map(e -> e.getMediaInfo().getAspectRatio()).collect(Collectors.toSet());
        if (aspectRatios.size() == 1) {
            this.setAspectRatio(aspectRatios.iterator().next());
        }

        Set<String> resolutions = selectedMediaTitles.stream().map(e -> e.getMediaInfo().getResolution()).collect(Collectors.toSet());
        if (resolutions.size() == 1) {
            String resolution = resolutions.iterator().next();
            String[] resolutionDimensions = resolution.split(MediaInfo.RESOLUTION_DIMENSIONS_SEPARATOR);
            this.setWidth(Integer.parseInt(resolutionDimensions[0]));
            this.setHeight(Integer.parseInt(resolutionDimensions[1]));

            this.setFormat(Format.evaluateFormat(resolution));
        }

        Set<Double> frameRates = selectedMediaTitles.stream().map(e -> e.getMediaInfo().getFrameRate()).collect(Collectors.toSet());
        if (frameRates.size() == 1) {
            this.setFrameRate(frameRates.iterator().next());
        }

        boolean hasMenu = mediaTitleTableModel.getMediaTitles().stream().anyMatch(e -> e.isMenu());
        this.setMenu(hasMenu);

        boolean hasChapters = selectedMediaTitles.stream().anyMatch(e -> e.getMediaInfo().hasChapters());
        this.setChapters(hasChapters);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setMenu(int menu) {
        this.menu = menu;
    }

    public int getChapters() {
        return chapters;
    }

    public double getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(double frameRate) {
        this.frameRate = frameRate;
    }

    public void setChapters(boolean chapters) {
        this.chapters = chapters ? 1 : 0;
    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(boolean menu) {
        this.menu = menu ? 1 : 0;
    }

    public Vector<String> getToStringComponents() {
        Vector<String> components = new Vector<>();

        if (aspectRatio.length() > 0) {
            components.add("Aspect Ratio: " + aspectRatio);
        }
        if (width > 0) {
            components.add("Resolution: " + width + MediaInfo.RESOLUTION_DIMENSIONS_SEPARATOR + height);
        }
        if (format.length()>0) {
            components.add("Format: " + format);
        }
        if (frameRate > 0) {
            components.add("Frame Rate: " + frameRate);
        }

        components.add("menu: " + (menu==1?"yes":"no"));
        components.add("chapters: " + (chapters==1?"yes":"no"));

        return components;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }



}
