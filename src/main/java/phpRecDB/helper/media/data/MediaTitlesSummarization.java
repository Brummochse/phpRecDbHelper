package phpRecDB.helper.media.data;

import phpRecDB.helper.gui.MediaTitleTableModel;
import phpRecDB.helper.util.TimeUtil;

import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MediaTitlesSummarization {

    private final MediaTitleTableModel mediaTitleTableModel;

    public MediaTitlesSummarization(MediaTitleTableModel mediaTitleTableModel) {
        this.mediaTitleTableModel = mediaTitleTableModel;
    }

    private String getSimilarValueString(String name, Stream<String> stream) {
        Set<String> aspectRatios = stream.collect(Collectors.toSet());
        if (aspectRatios.size() == 1) {
            return "<br>" + name + ": " + aspectRatios.iterator().next();
        }
        return "";
    }

    public String createSummary() {
        String s = "";
        List<MediaTitle> selectedMediaTitles = mediaTitleTableModel.getSelectedMediaTitles();

        long length = 0;
        for (MediaTitle mediaTitle : selectedMediaTitles) {
            length += mediaTitle.getMediaInfo().getLength();
        }
        s += "Length: " + TimeUtil.convertMillisecondsToTimeStr(length);

        s+=getSimilarValueString("Aspect Ratio", selectedMediaTitles.stream().map(e -> e.getMediaInfo().getAspectRatio()));
        s+=getSimilarValueString("Resolution", selectedMediaTitles.stream().map(e -> e.getMediaInfo().getResolution()));
        s+=getSimilarValueString("Type", mediaTitleTableModel.getMediums().stream().map(e -> e.getType().getName()));

        long sizeInBytes = evaluateFileSize();
        if (sizeInBytes > 0) {
            long sizeinKb = sizeInBytes / 1024;
            long sizeInMb = sizeinKb / 1024;
            s += "<br>File Size: " + sizeInMb + " MB";
        }
        return "<html>" + s + "</html>";
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
