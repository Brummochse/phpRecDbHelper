package phpRecDB.helper.media;

import phpRecDB.helper.gui.ProgressBarDialog;
import phpRecDB.helper.media.data.MediaInfo;
import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.media.data.Medium;
import phpRecDB.helper.media.data.types.FileInputHandler;
import phpRecDB.helper.media.data.types.FileInputHandlers;
import phpRecDB.helper.util.LogUtil;
import uk.co.caprica.vlcj.media.AudioTrackInfo;
import uk.co.caprica.vlcj.media.VideoTrackInfo;
import uk.co.caprica.vlcj.player.base.TitleDescription;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MediaParser {

    public Vector<MediaTitle> getTitles(String[] paths) {

        Vector<MediaTitle> titles = new Vector<>();
        new ProgressBarDialog((e) -> {

            for (int j = 0; j < paths.length; j++) {
                String path = paths[j];
                Medium medium = new Medium(path);
                FileInputHandler fileInputType = FileInputHandlers.evaluateType(new File(path));


                LogUtil.logger.info("start loading medium: "+ path);
                List<TitleDescription> titleDescriptions = getTitleDescriptions(fileInputType,path);

                if (titleDescriptions.size() == 0) { //medium without title separation -> only one media title
                    MediaTitle title = new MediaTitle();
                    title.setTitleId(-1);
                    title.setMedium(medium);
                    title.setMediaInfo(parseMediaInfo(title));
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
                        mediaTitle.setMediaInfo(parseMediaInfo(mediaTitle));
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

    private MediaInfo parseMediaInfo(MediaTitle mediaTitle) {
        MediaPlayerLoader<MediaInfo> mediaPlayerThread = new MediaPlayerLoader<>() {

            @Override
            protected MediaInfo readMediaInfo() {
                MediaInfo mediaInfoReturnValue = new MediaInfo();
                mediaInfoReturnValue.setVideoTrackInfos(mediaPlayer.media().info().videoTracks());
                mediaInfoReturnValue.setAudioTrackInfos(mediaPlayer.media().info().audioTracks());
                mediaInfoReturnValue.setChapterCount(mediaPlayer.chapters().count());
                mediaInfoReturnValue.setLength(mediaPlayer.status().length());
                return mediaInfoReturnValue;
            }

            @Override
            protected boolean isMediaInfoReady() {
                List<VideoTrackInfo> videoTrackInfos = mediaPlayer.media().info().videoTracks();
                List<AudioTrackInfo> audioTrackInfos = mediaPlayer.media().info().audioTracks();

                if (videoTrackInfos.size() > 0) {
                    //for some unknown reasons sometimes it can happen that there exist multiple VideoTrackInfo and some of them have no data set
                    for (VideoTrackInfo videoTrackInfo : videoTrackInfos) {
                        if (videoTrackInfo.width() > 0) {
                            return true;
                        }
                    }
                    return false;
                }
                return audioTrackInfos.size() > 0;
            }

        };

        return mediaPlayerThread.execute(mediaTitle.getMedium().getVlcInputString(),mediaTitle.getTitleId());
    }

    private List<TitleDescription> getTitleDescriptions(FileInputHandler fileInputHandler, String path) {
        String vlcInputPath=fileInputHandler.getVlcInputString(path);
        MediaPlayerLoader<List<TitleDescription>> mediaPlayerThread = new MediaPlayerLoader<>() {

            @Override
            protected List<TitleDescription> readMediaInfo() {
                return mediaPlayer.titles().titleDescriptions();
            }

            @Override
            protected boolean isMediaInfoReady() {
                return mediaPlayer.titles().titleDescriptions().size() +
                        mediaPlayer.media().info().videoTracks().size() +
                        mediaPlayer.media().info().audioTracks().size() > 0;
            }
        };

        List<TitleDescription> titleDescriptions = mediaPlayerThread.execute(vlcInputPath);
        fileInputHandler.postProcessReadTitles(titleDescriptions,path);

        return titleDescriptions;
    }


}
