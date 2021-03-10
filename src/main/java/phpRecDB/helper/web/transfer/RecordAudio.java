package phpRecDB.helper.web.transfer;

import phpRecDB.helper.gui.MediaTitleTableModel;
import phpRecDB.helper.media.data.SemioticSystem;

import java.util.Vector;

public class RecordAudio extends AbstractRecord {
    public RecordAudio(MediaTitleTableModel mediaTitleTableModel) {
        this.semioticSystem= SemioticSystem.AUDIO;
        super.initialiseBaseAttributes(mediaTitleTableModel);
    }

    @Override
    public Vector<String> getToStringComponents() {
        return new Vector<>();
    }


}
