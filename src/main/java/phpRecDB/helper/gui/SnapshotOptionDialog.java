package phpRecDB.helper.gui;

import phpRecDB.helper.Constants;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SnapshotOptionDialog {
    private JTextField tfCount;
    private JTextField tfDelay;
    private JPanel pnlContent;
    private JLabel lblHelpCount;
    private JLabel lblHelpDelay;

    public SnapshotOptionDialog() {
        tfDelay.setText(""+Constants.snapshotAfterSkipDelay);
        tfCount.setText(""+Constants.snapshotsPerTitleCount);
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

    private class InstantTooltipMouseAdapter extends MouseAdapter {
        final int defaultTimeout = ToolTipManager.sharedInstance().getInitialDelay();

        @Override
        public void mouseEntered(MouseEvent e) {
            ToolTipManager.sharedInstance().setInitialDelay(0);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ToolTipManager.sharedInstance().setInitialDelay(defaultTimeout);
        }
    };

}
