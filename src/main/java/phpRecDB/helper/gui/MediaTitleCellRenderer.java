package phpRecDB.helper.gui;

import phpRecDB.helper.media.data.MediaTitle;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MediaTitleCellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
        if (value instanceof MediaTitle) {
            MediaTitle mediaTitle=(MediaTitle)value;
            if (!mediaTitle.isVisible()) {
                c.setForeground(Color.LIGHT_GRAY);
            } else {
                c.setForeground(Color.BLACK);
            }
        }
        return c;
    }
}
