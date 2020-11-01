package phpRecDB.helper.lambdaInterface;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public interface MouseDoubleClickedListener extends MouseListener {


    default void mousePressed(MouseEvent e){};

    default void mouseReleased(MouseEvent e){};

    default void mouseEntered(MouseEvent e){};

    default void mouseExited(MouseEvent e){};

    @Override
    default void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            mouseDoubleClicked(e);
        }
    }

    void mouseDoubleClicked(MouseEvent e) ;

}
