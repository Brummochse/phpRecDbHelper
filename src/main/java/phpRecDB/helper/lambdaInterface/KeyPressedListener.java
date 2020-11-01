package phpRecDB.helper.lambdaInterface;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface KeyPressedListener extends KeyListener {

    default void keyTyped(KeyEvent e) {
    }

    default void keyReleased(KeyEvent e) {
    }

}
