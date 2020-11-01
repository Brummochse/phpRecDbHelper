package phpRecDB.helper.gui;

import phpRecDB.helper.gui.SnapshotThumbnail;

import javax.swing.*;
import java.awt.*;

public class ThumbnailListRenderer extends DefaultListCellRenderer {


    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof SnapshotThumbnail) {
            SnapshotThumbnail snapshotThumbnail = (SnapshotThumbnail) value;
            label.setIcon(snapshotThumbnail.getImageIcon());
            label.setText("");
        }
        return label;
    }
}
