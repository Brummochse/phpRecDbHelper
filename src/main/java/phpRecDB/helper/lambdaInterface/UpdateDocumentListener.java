package phpRecDB.helper.lambdaInterface;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface UpdateDocumentListener extends DocumentListener {

    @Override
    default void insertUpdate(DocumentEvent e){
        update();
    }

    @Override
    default  void removeUpdate(DocumentEvent e){
        update();
    }

    @Override
    default void changedUpdate(DocumentEvent e){
        update();
    }

    void update();
}
