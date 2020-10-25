package phpRecDB.helper.media;

import phpRecDB.helper.media.data.AbstractMediaTitle;
import phpRecDB.helper.media.data.Medium;
import phpRecDB.helper.util.MediaUtil;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.TitleDescription;

import java.io.File;
import java.util.List;
import java.util.Vector;

public class Parser {

    public Vector getTitles(String[] paths) {
        Vector titles = new Vector();
        for (String currentPath : paths) {

            currentPath = getVlcInputString(currentPath);

            Medium medium= new Medium();
            medium.setPath(currentPath);
            List<TitleDescription> titleDescriptions = getTitleDescriptions(currentPath);
            if (titleDescriptions.size() == 0) {
                AbstractMediaTitle title = new AbstractMediaTitle();
                title.setTitleId(-1);
                title.setMedium(medium);
                titles.add(title);
            }

            for (int i = 0; i < titleDescriptions.size(); i++) {
                TitleDescription titleDescription = titleDescriptions.get(i);
                AbstractMediaTitle title = new AbstractMediaTitle();
                title.setMenu(titleDescription.isMenu());
                title.setTitleId(i);
                title.setMedium(medium);
                title.setName(titleDescription.name());
                titles.add(title);
            }
        }
        return titles;
    }


    private  String getVlcInputString(String path) {
        File file = new File(path);
        if (file.isDirectory() && MediaUtil.isBRFolder(file)) {
            return "bluray:///" + path;
        }

        return path;
    }

    private List<TitleDescription> getTitleDescriptions(String path) {
        MediaPlayerFactory factory = new MediaPlayerFactory();
        MediaPlayer mediaPlayer = factory.mediaPlayers().newMediaPlayer();
        mediaPlayer.media().start(path);
        List<TitleDescription> titleDescriptions = mediaPlayer.titles().titleDescriptions();
        mediaPlayer.release();
        return titleDescriptions;
    }
}
