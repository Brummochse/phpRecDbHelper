package phpRecDB.helper.gui;

import phpRecDB.helper.Constants;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class SnapshotOptionDialog {
    private JTextField tfCount;
    private JTextField tfDelay;
    private JPanel pnlContent;
    private JLabel lblHelpCount;
    private JLabel lblHelpDelay;

    public SnapshotOptionDialog() {
        tfDelay.setText("" + Constants.snapshotAfterSkipDelay);
        tfCount.setText("" + Constants.snapshotsPerTitleCount);
        ((PlainDocument) tfDelay.getDocument()).setDocumentFilter(new IntegerDocumentFilter());
        ((PlainDocument) tfCount.getDocument()).setDocumentFilter(new IntegerDocumentFilter());

        lblHelpCount.addMouseListener(new InstantTooltipMouseAdapter());
        lblHelpDelay.addMouseListener(new InstantTooltipMouseAdapter());
        lblHelpCount.setToolTipText("<html>How many snapshots per video title?<br> Menus get snapshoted only once, despite what is written here.</html>");
        lblHelpDelay.setToolTipText("<html>Milliseconds to wait before snapshotting, after jumping to the next position.<br>If your snapshots are pixelated, increase this value, to let VLC some time<br>to adjust the image after jumping to the new position.</html>");
    }

    public JTextField getTfCount() {
        return tfCount;
    }

    public JTextField getTfDelay() {
        return tfDelay;
    }

    public int getDelay() {
        return Integer.parseInt(tfDelay.getText());
    }

    public int getCount() {
        return Integer.parseInt(tfCount.getText());
    }

    public JPanel getPnlContent() {
        return pnlContent;
    }

    private void createUIComponents() {
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        pnlContent = new JPanel();
        pnlContent.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("<html>\nSnapshots (per video title)\n</html>");
        pnlContent.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        pnlContent.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tfCount = new JTextField();
        tfCount.setText("");
        pnlContent.add(tfCount, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("<html>\nDelay (in milliseconds)\n</html>");
        pnlContent.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfDelay = new JTextField();
        tfDelay.setText("");
        pnlContent.add(tfDelay, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblHelpCount = new JLabel();
        Font lblHelpCountFont = this.$$$getFont$$$(null, Font.BOLD, -1, lblHelpCount.getFont());
        if (lblHelpCountFont != null) lblHelpCount.setFont(lblHelpCountFont);
        lblHelpCount.setText("-?-");
        lblHelpCount.setToolTipText("");
        pnlContent.add(lblHelpCount, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblHelpDelay = new JLabel();
        Font lblHelpDelayFont = this.$$$getFont$$$(null, Font.BOLD, -1, lblHelpDelay.getFont());
        if (lblHelpDelayFont != null) lblHelpDelay.setFont(lblHelpDelayFont);
        lblHelpDelay.setText("-?-");
        pnlContent.add(lblHelpDelay, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return pnlContent;
    }
}
