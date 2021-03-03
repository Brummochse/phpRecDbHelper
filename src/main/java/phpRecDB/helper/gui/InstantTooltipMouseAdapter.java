package phpRecDB.helper.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class InstantTooltipMouseAdapter extends MouseAdapter {
    final int defaultTimeout = ToolTipManager.sharedInstance().getInitialDelay();

    @Override
    public void mouseEntered(MouseEvent e) {
        ToolTipManager.sharedInstance().setInitialDelay(0);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ToolTipManager.sharedInstance().setInitialDelay(defaultTimeout);
    }
}
