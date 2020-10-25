package phpRecDB.helper.gui;

import phpRecDB.helper.media.data.AbstractMediaTitle;
import phpRecDB.helper.util.ResourceUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TitleListCellRenderer extends DefaultListCellRenderer {

//    DefaultListCellRenderer df;
//    private Color textSelectionColor = Color.BLACK;
//    private Color backgroundSelectionColor = Color.CYAN;
//    private Color textNonSelectionColor = Color.BLACK;
//    private Color backgroundNonSelectionColor = Color.WHITE;
//
//    @Override
//    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//        JLabel label = new JLabel();
////        label.setIcon(fileSystemView.getSystemIcon(file));
//        label.setText("test");
//        label.setBackground(backgroundSelectionColor);
//
//
////        if (isSelected) {
////            label.setBackground(backgroundSelectionColor);
////            label.setForeground(textSelectionColor);
////        } else {
////            label.setBackground(backgroundNonSelectionColor);
////            label.setForeground(textNonSelectionColor);
////        }
//
//        return label;
//    }


    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {


        AbstractMediaTitle mediaTitle = (AbstractMediaTitle) value;

        DefaultListCellRenderer label = (DefaultListCellRenderer) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        label.setIcon(mediaIcon);
        if (mediaTitle.isMenu()) {
            label.setIcon(menuIcon);
        }
        if (!mediaTitle.isVisible()) {
            label.setIcon(disabledIcon);
            label.setEnabled(false);
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(label,BorderLayout.CENTER);

        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(mediaTitle.isVisible());
        panel.add(checkBox,BorderLayout.WEST);

        return panel;
    }

    private static ImageIcon menuIcon = ResourceUtil.getInstance().getResourceIcon("menu.png");
    private static ImageIcon disabledIcon = ResourceUtil.getInstance().getResourceIcon("disabled.png");
    private static ImageIcon mediaIcon = ResourceUtil.getInstance().getResourceIcon("media.png");
}

