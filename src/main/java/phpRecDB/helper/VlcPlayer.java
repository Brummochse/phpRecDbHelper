package phpRecDB.helper;

import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;

public class VlcPlayer {

    private static VlcPlayer instance;

    private JPanel vlcPanel;

    private EmbeddedMediaPlayerComponent mpc;

    private VlcPlayer() {
    }

    public static VlcPlayer getInstance() {
        if (instance == null) {
            VlcPlayer.instance = new VlcPlayer();
        }
        return instance;
    }

    public void setVlcPanel(JPanel vlcPanel) {
        this.vlcPanel = vlcPanel;
    }

    public MediaPlayer getNewMediaPlayerAccess() {
        initMPC();
        return mpc.mediaPlayer();
    }

    public void release() {
        if (mpc != null) {
            mpc.release();
            mpc = null;
        }
    }

    private void initMPC() {
        release();
        mpc = new EmbeddedMediaPlayerComponent();
        if (vlcPanel != null) {
            vlcPanel.removeAll();
            vlcPanel.add(mpc);
        }
    }

}
