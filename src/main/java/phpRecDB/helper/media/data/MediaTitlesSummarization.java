package phpRecDB.helper.media.data;

import phpRecDB.helper.gui.MediaTitleTableModel;
import phpRecDB.helper.web.RecordInfo;

import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

public class MediaTitlesSummarization {


    private final MediaTitleTableModel mediaTitleTableModel;

    public MediaTitlesSummarization(MediaTitleTableModel mediaTitleTableModel) {
        this.mediaTitleTableModel = mediaTitleTableModel;
    }

    public RecordInfo getRecordInfo() {
        RecordInfo recordInfo = new RecordInfo();
        List<MediaTitle> selectedMediaTitles = mediaTitleTableModel.getSelectedMediaTitles();

        recordInfo.setLength(getLength(selectedMediaTitles));

        Set<String> aspectRatios = selectedMediaTitles.stream().map(e -> e.getMediaInfo().getAspectRatio()).collect(Collectors.toSet());
        if (aspectRatios.size() == 1) {
            recordInfo.setAspectRatio(aspectRatios.iterator().next());
        }

        Set<String> resolutions = selectedMediaTitles.stream().map(e -> e.getMediaInfo().getResolution()).collect(Collectors.toSet());
        if (resolutions.size() == 1) {
            String resolution = resolutions.iterator().next();
            String[] resolutionDimensions = resolution.split(MediaInfo.RESOLUTION_DIMENSIONS_SEPARATOR);
            recordInfo.setWidth(Integer.parseInt(resolutionDimensions[0]));
            recordInfo.setHeight(Integer.parseInt(resolutionDimensions[1]));

            recordInfo.setFormat(Format.evaluateFormat(resolution));
        }

        Set<String> mediaTypes = mediaTitleTableModel.getMediums().stream().map(e -> e.getType().getName()).collect(Collectors.toSet());
        if (mediaTypes.size() == 1) {
            recordInfo.setType(mediaTypes.iterator().next());
        }

        Set<Double> frameRates = selectedMediaTitles.stream().map(e -> e.getMediaInfo().getFrameRate()).collect(Collectors.toSet());
        if (frameRates.size() == 1) {
            recordInfo.setFrameRate(frameRates.iterator().next());
        }

        recordInfo.setSize(evaluateFileSize());

        Set<Medium> selectedMedia = selectedMediaTitles.stream().map(MediaTitle::getMedium).collect(Collectors.toSet());
        recordInfo.setMediaCount(selectedMedia.size());

        boolean hasMenu = mediaTitleTableModel.getMediaTitles().stream().anyMatch(e -> e.isMenu());
        recordInfo.setMenu(hasMenu);




        return recordInfo;

    }

    public long getLength(List<MediaTitle> selectedMediaTitles) {
        long length = 0;
        for (MediaTitle mediaTitle : selectedMediaTitles) {
            length += mediaTitle.getMediaInfo().getLength();
        }
        return length;
    }


    private long evaluateFileSize() {
        Vector<MediaTitle> mediaTitles = mediaTitleTableModel.getMediaTitles();
        boolean isAllMediaTitlesSelected = !mediaTitles.stream().anyMatch(e -> !e.isSelected() && !e.isMenu());
        if (isAllMediaTitlesSelected) {
            Set<Medium> mediums = mediaTitleTableModel.getMediums();

            return mediums.stream().collect(Collectors.summarizingLong(e -> e.getFileSystemSize())).getSum();
        }
        return 0;

    }
}
