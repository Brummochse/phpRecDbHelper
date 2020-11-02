package phpRecDB.helper;

import phpRecDB.helper.gui.SnapshotOptionDialog;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        SnapshotOptionDialog dialog = new SnapshotOptionDialog();
        int result = JOptionPane.showConfirmDialog(null, dialog.getPnlContent(),
                "Snapshot Options", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println("x value: " + dialog.getTfDelay().getText());
            System.out.println("y value: " + dialog.getTfCount().getText());
        }
    }
}
