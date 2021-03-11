package phpRecDB.helper.media;

import phpRecDB.helper.VlcPlayer;
import phpRecDB.helper.gui.ProgressBarDialog;
import phpRecDB.helper.media.data.MediaInfo;
import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.media.data.Medium;
import phpRecDB.helper.media.data.types.FileInputHandler;
import phpRecDB.helper.media.data.types.FileInputHandlers;
import phpRecDB.helper.util.LogUtil;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.TitleDescription;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MediaPathParser {

    public Vector<MediaTitle> getTitles(String[] paths) {

        Vector<MediaTitle> titles = new Vector<>();
        new ProgressBarDialog((e) -> {
            MediaInfoParser mediaInfoParser = new MediaInfoParser();

            for (int j = 0; j < paths.length; j++) {
                String path = paths[j];
                Medium medium = new Medium(path);
                FileInputHandler fileInputType = FileInputHandlers.evaluateType(new File(path));
                String vlcInputPath=fileInputType.getVlcInputString(path);

                LogUtil.logger.info("start loading medium: "+ vlcInputPath);
                List<TitleDescription> titleDescriptions = getTitleDescriptions(vlcInputPath);

                if (titleDescriptions.size() == 0) { //medium without title separation -> only one media title
                    MediaTitle title = new MediaTitle();
                    title.setTitleId(-1);
                    title.setMedium(medium);
                    title.setMediaInfo(mediaInfoParser.parseMediaInfo(title));
                    titles.add(title);
                    int progress = (int) ((1. + j) / paths.length * 100);
                    e.updateValue(progress);
                } else { //medium with title separation -> parse all media titles
                    List<MediaTitle> titlesToParseMediaInfo = new ArrayList<>();
                    for (int i = 0; i < titleDescriptions.size(); i++) {
                        TitleDescription titleDescription = titleDescriptions.get(i);
                        MediaTitle mediaTitle = new MediaTitle();
                        mediaTitle.setMenu(titleDescription.isMenu());
                        mediaTitle.setTitleId(i);
                        mediaTitle.setMedium(medium);
                        mediaTitle.setName(titleDescription.name());
                        if (!titleDescription.isMenu()) {
                            // in tests some (blu ray) menus caused problems
                            // for this reason only no-menu titles are remembered for further media info analysis
                            titlesToParseMediaInfo.add(mediaTitle);
                        } else {
                            mediaTitle.setMediaInfo(new MediaInfo());
                        }
                        titles.add(mediaTitle);
                    }
                    for (int i = 0; i < titlesToParseMediaInfo.size(); i++) {
                        MediaTitle mediaTitle = titlesToParseMediaInfo.get(i);
                        mediaTitle.setMediaInfo(mediaInfoParser.parseMediaInfo(mediaTitle));
                        int progress = (int) (((1. + i) / titlesToParseMediaInfo.size() + j) * (100 / paths.length));
                        e.updateValue(progress);
                    }
                }
                String typeName = fileInputType.evaluateMediaType(titles.lastElement());

                medium.setType(typeName);

            }
        }).start();

        return titles;
    }

    private List<TitleDescription> getTitleDescriptions(String path) {
        MediaPlayer mediaPlayer = VlcPlayer.getInstance().getNewMediaPlayerAccess();
        mediaPlayer.media().start(path);
        List<TitleDescription> titleDescriptions = mediaPlayer.titles().titleDescriptions();
        VlcPlayer.getInstance().release();
        return titleDescriptions;
    }


}
