package phpRecDB.helper.util;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public interface SingleListSelectionEvent extends ListSelectionListener {

    @Override
    default  void valueChanged(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) {//This line prevents double events
            valueChangedOnce(event);
        }
    }

   void valueChangedOnce(ListSelectionEvent event) ;
}
