package phpRecDB.helper.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ProgressBarDialog extends JDialog {

    public interface ProgressbarExecutable {
        void execute(ProgressBarDialog param);
    }

    private Runnable runnable = null;
    private JProgressBar progressBar;
    private final ProgressbarExecutable progressbarExecutable;

    public ProgressBarDialog(ProgressbarExecutable progressbarExecutable)
    {
        this.progressbarExecutable = progressbarExecutable;
        setModal(true);
        setupUi();
    }

    private void setupUi() {
        Container content = this.getContentPane();
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        Border border = BorderFactory.createTitledBorder("Reading...");
        progressBar.setBorder(border);
        content.add(progressBar, BorderLayout.NORTH);
        this.setSize(300, 100);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                executeWorker();
            }
        });
    }

    public void start() {
        setVisible(true);
    }

    private void executeWorker() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                executeBackgroundTask();
                return null;
            }

            @Override
            protected void done() {
                dispose();
            }
        }.execute();
    }

    private void executeBackgroundTask() {
        progressbarExecutable.execute(this);
    }

    public void updateValue(int value) {
        progressBar.setValue(value);
    }

}