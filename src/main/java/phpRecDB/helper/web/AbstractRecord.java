package phpRecDB.helper.web;

import phpRecDB.helper.gui.MediaTitleTableModel;
import phpRecDB.helper.media.data.*;
import phpRecDB.helper.util.TimeUtil;

import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

public abstract class AbstractRecord {
    protected long length = 0;
    //in bytes
    protected long size = 0;
    protected int mediaCount = 0;
    protected SemioticSystem semioticSystem;

    public static AbstractRecord createRecord(MediaTitleTableModel mediaTitleTableModel) {
        System.out.println(mediaTitleTableModel.getSemioticSystem());
        return switch (mediaTitleTableModel.getSemioticSystem()) {
            case VIDEO -> new RecordVideo(mediaTitleTableModel);
            case AUDIO -> new RecordAudio(mediaTitleTableModel);
            default -> null;
        };
    }

    protected void initialiseBaseAttributes(MediaTitleTableModel mediaTitleTableModel) {
        List<MediaTitle> selectedMediaTitles = mediaTitleTableModel.getSelectedMediaTitles();
        this.setLength(evaluateLength(selectedMediaTitles));
        this.setSize(evaluateFileSize(mediaTitleTableModel));
        Set<Medium> selectedMedia = selectedMediaTitles.stream().map(MediaTitle::getMedium).collect(Collectors.toSet());
        this.setMediaCount(selectedMedia.size());
    }

    private long evaluateFileSize(MediaTitleTableModel mediaTitleTableModel) {
        Vector<MediaTitle> mediaTitles = mediaTitleTableModel.getMediaTitles();
        boolean isAllMediaTitlesSelected = !mediaTitles.stream().anyMatch(e -> !e.isSelected() && !e.isMenu());
        if (isAllMediaTitlesSelected) {
            Set<Medium> mediums = mediaTitleTableModel.getMediums();

            return mediums.stream().collect(Collectors.summarizingLong(e -> e.getFileSystemSize())).getSum();
        }
        return 0;
    }

    public long evaluateLength(List<MediaTitle> selectedMediaTitles) {
        long length = 0;
        for (MediaTitle mediaTitle : selectedMediaTitles) {
            length += mediaTitle.getMediaInfo().getLength();
        }
        return length;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getLength() {
        return length;
    }

    public int getMediaCount() {
        return mediaCount;
    }

    public void setMediaCount(int mediaCount) {
        this.mediaCount = mediaCount;
    }

    public abstract Vector<String> getToStringComponents();
        @Override
    public String toString() {
        Vector<String> components = new Vector<>();
            components.add("Semiotic System: " + semioticSystem.name());
        components.add("Length: " + TimeUtil.convertMillisecondsToTimeStr(length));
        if (getMediaCount() > 0) {
            components.add("media count: " + mediaCount);
        }
        if (size > 0) {
            long sizeinKb = size / 1024;
            long sizeInMb = sizeinKb / 1024;
            components.add("File Size: " + sizeInMb + " MB");
        }
        components.addAll(getToStringComponents());

        return "<html>" + String.join("<br>", components) + "</html>";
    }
}
