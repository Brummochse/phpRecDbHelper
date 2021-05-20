package phpRecDB.helper.gui;

import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.media.data.Medium;
import phpRecDB.helper.media.data.SemioticSystem;
import phpRecDB.helper.util.ResourceUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

public class MediaTitleTableModel extends AbstractTableModel {

    private static ImageIcon menuIcon = ResourceUtil.getInstance().getResourceIcon("menu.png");
    private static ImageIcon mediaIcon = ResourceUtil.getInstance().getResourceIcon("media.png");

    public static int COL_SELECT_CHECKBOX = 0;
    public static int COL_ICON = 1;

    private Vector<MediaTitle> mediaTitles = new Vector<>();

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
        MediaTitle mediaTitle = mediaTitles.get(rowIndex);
        return columnIndex == COL_SELECT_CHECKBOX && !mediaTitle.isMenu();
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

    public Set<Medium> getMediums() {
        return mediaTitles.stream().map(MediaTitle::getMedium).collect(Collectors.toSet());
    }

    public List<MediaTitle> getSelectedMediaTitles() {
        return mediaTitles.stream().filter(MediaTitle::isSelected).collect(Collectors.toList());
    }

    public List<MediaTitle> getMenuTitles() {
        return mediaTitles.stream().filter(MediaTitle::isMenu).collect(Collectors.toList());
    }

    public SemioticSystem getSemioticSystem() {
        Set<SemioticSystem> semioticSystems = getSelectedMediaTitles().stream().map(e -> e.getMediaInfo().getSemioticSystem()).collect(Collectors.toSet());
        if (semioticSystems.size()==1) {
            return semioticSystems.iterator().next();
        }
        return SemioticSystem.UNDEFINED;
    }

}
