package phpRecDB.helper.media.data.types;

import com.mscg.DvdParser;
import phpRecDB.helper.Constants;
import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.util.LogUtil;
import phpRecDB.helper.util.MediaUtil;
import uk.co.caprica.vlcj.player.base.TitleDescription;

import java.io.File;
import java.util.*;

public class DvdInputHandler extends FileInputHandler {

    @Override
    public String getResourceIconIdentifier() {
        return "dvd.png";
    }

    @Override
    public boolean checkFile(File file) {
        return file.isDirectory() && MediaUtil.isDVDFolder(file);
    }

    @Override
    public String evaluateMediaType(MediaTitle mediaTitle) {
        if (MediaUtil.getFileSystemSize(mediaTitle.getMedium().getPath()) > Constants.singleDvdMaxSize) {
            return "DVD-DL";
        } else {
            return "DVD";
        }
    }

    @Override
    public void postProcessReadTitles(List<TitleDescription> titlesVlc, String path) {
        removeCorruptedTitles(titlesVlc, path);
    }

    /**
     * it seems that some standalone DVD Records create corrupted DVD title structures
     * These problematic DVDs define multiple Titles starting at the same startSector.
     * This results in multiple title selections which all playing the same video.
     *
     * This method checks is some of these titles exist with same start sector and keeps
     * only the title with the longest length and remove all other redundant title definitions
     *
     * @param titlesVlc
     * @param path
     */
    public void removeCorruptedTitles(List<TitleDescription> titlesVlc, String path) {
        long titlesVlcWithoutMenuCount = titlesVlc.stream().filter(e -> !e.isMenu()).count();
        List<DvdParser.Title> titlesIfo = getTitlesFromIfo(path);

        if (titlesVlcWithoutMenuCount == titlesIfo.size())
        {
            //group titles by same start sector
            HashMap<Long, LinkedList<DvdParser.Title>> dvdIfoTitlesByStartSector = new HashMap<>();
            for (DvdParser.Title dvdTitle : titlesIfo) {
                long vtsStartSector = dvdTitle.getTitleInfo().getVtsStartSector();
                if (!dvdIfoTitlesByStartSector.containsKey(vtsStartSector)) {
                    dvdIfoTitlesByStartSector.put(vtsStartSector, new LinkedList<>());
                }
                dvdIfoTitlesByStartSector.get(vtsStartSector).add(dvdTitle);
            }
            //collect list with corrupted titles to remove
            List<Byte> corruptTitlesToRemoveIds=new LinkedList<>();
            for (long startSector : dvdIfoTitlesByStartSector.keySet()) {
                LinkedList<DvdParser.Title> titlesPerStartSector = dvdIfoTitlesByStartSector.get(startSector);
                if (titlesPerStartSector.size() <= 1) {
                    continue;
                }
                LogUtil.logger.warning("corrupt dvd structure detected. found "+titlesPerStartSector.size()+ " titles at start sector "+ startSector);
                titlesPerStartSector.sort(Comparator.comparing(DvdParser.Title::getPlaybackTime));
                while (titlesPerStartSector.size() > 1) {
                    DvdParser.Title titleToRemove = titlesPerStartSector.removeFirst();
                    corruptTitlesToRemoveIds.add(titleToRemove.getTitleInfo().getTitleNumberWithinVTS());
                }
            }
            //sort in descending order
            corruptTitlesToRemoveIds.sort(Byte::compare);
            Collections.reverse(corruptTitlesToRemoveIds);
            // remove corrupted vlc titles
            int titlesVlcCount = titlesVlc.size();
            for (Byte corruptTitleId : corruptTitlesToRemoveIds) {
                LogUtil.logger.warning("remove corrupted title with id "+corruptTitleId);
                int vlcjTitleId= titlesVlcCount -titlesIfo.size()+corruptTitleId -1;
                titlesVlc.remove(vlcjTitleId);
            }
        }
    }

    public List<DvdParser.Title> getTitlesFromIfo(String path) {
        try {
            return DvdParser.getDvdTitles(new File(path));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }
}
