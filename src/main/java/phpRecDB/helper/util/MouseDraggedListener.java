package phpRecDB.helper.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public interface MouseDraggedListener extends MouseMotionListener {

    @Override
    public default void mouseMoved(MouseEvent e) {
    }
}
