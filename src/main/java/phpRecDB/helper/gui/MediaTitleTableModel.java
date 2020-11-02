package phpRecDB.helper.gui;

import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.util.ResourceUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class MediaTitleTableModel extends AbstractTableModel {

    private static ImageIcon menuIcon = ResourceUtil.getInstance().getResourceIcon("menu.png");
    private static ImageIcon mediaIcon = ResourceUtil.getInstance().getResourceIcon("media.png");

    public static int COL_SELECT_CHECKBOX = 0;
    public static int COL_ICON = 1;

    private Vector<MediaTitle> mediaTitles = new Vector();

    public List<MediaTitle> getSelectedMediaTitles() {
        return mediaTitles.stream().filter(e->e.isSelected()).collect(Collectors.toList());
    }

    @Override
    public int getRowCount() {
        return mediaTitles.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == COL_SELECT_CHECKBOX) {
            return Boolean.class;
        }
        if (columnIndex == COL_ICON) {
            return Icon.class;
        }
        return MediaTitle.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == COL_SELECT_CHECKBOX;
    }


    @Override
    public Object getValueAt(int row, int col) {
        MediaTitle mediaTitle = mediaTitles.get(row);
        if (col == COL_SELECT_CHECKBOX) {
            return mediaTitle.isSelected();
        }
        if (col == COL_ICON) {
            if (mediaTitle.isMenu()) {
                return menuIcon;
            }
            return mediaIcon;
        }
        return mediaTitle;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == COL_SELECT_CHECKBOX) {
            boolean isVisible = (boolean) aValue;
            mediaTitles.get(rowIndex).setSelected(isVisible);
            fireTableDataChanged();
        }
    }

    public void setMediaTitles(Vector<MediaTitle> mediaTitles) {
        this.mediaTitles = mediaTitles;
        fireTableDataChanged();
    }

    public Vector<MediaTitle> getMediaTitles() {
        return mediaTitles;
    }
}
