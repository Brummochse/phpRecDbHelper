package phpRecDB.helper;

import phpRecDB.helper.gui.ProgressBarDialog;
import phpRecDB.helper.gui.SnapshotThumbnail;
import phpRecDB.helper.gui.ThumbnailListRenderer;
import phpRecDB.helper.lambdaInterface.KeyPressedListener;
import phpRecDB.helper.media.SnapshotMaker;
import phpRecDB.helper.lambdaInterface.MouseDoubleClickedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SnapshotController {
    private DefaultListModel<SnapshotThumbnail> snapshotThumbnailListModel = new DefaultListModel<>();
    private JList<SnapshotThumbnail> snapshotThumbnailList;

    public SnapshotController(JList<SnapshotThumbnail> snapshotThumbnailList) {
        this.snapshotThumbnailList = snapshotThumbnailList;
        initView();
    }

    private void initView() {
        snapshotThumbnailList.setModel(snapshotThumbnailListModel);
        snapshotThumbnailList.setCellRenderer(new ThumbnailListRenderer());
        snapshotThumbnailList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        snapshotThumbnailList.setVisibleRowCount(1);

        snapshotThumbnailList.addKeyListener((KeyPressedListener)(e) -> thumbnailKeyPressed(e));
        snapshotThumbnailList.addMouseListener((MouseDoubleClickedListener) (e) -> thumbnailDoubleClickedAction(e));
    }

    private void thumbnailKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            int selectedIndex = snapshotThumbnailList.getSelectedIndex();
            if (selectedIndex >= 0) {
                SnapshotThumbnail snapshotThumbnail = snapshotThumbnailListModel.get(selectedIndex);
                snapshotThumbnail.deleteSourceFile();
                snapshotThumbnailListModel.remove(selectedIndex);
                snapshotThumbnailList.revalidate();
                snapshotThumbnailList.repaint();
            }
        }
    }

    private void thumbnailDoubleClickedAction(MouseEvent e) {
        int index = snapshotThumbnailList.locationToIndex(e.getPoint());
        if (index>=0) {
            SnapshotThumbnail snapshotThumbnail = snapshotThumbnailList.getModel().getElementAt(index);
            File thumbnailSourceFile = snapshotThumbnail.getImgFile();
            Desktop dt = Desktop.getDesktop();
            try {
                dt.open(thumbnailSourceFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void loadSnapshotThumbnailsAction() {
        loadFromFolder(SnapshotMaker.getSnapshotFolder());
    }

    private void loadFromFolder(File path) {
        new ProgressBarDialog((e) -> {
            snapshotThumbnailListModel.clear();
            List<String> result = new ArrayList<>();
            try (Stream<Path> walk = Files.walk(path.toPath())) {
                result = walk.map(x -> x.toString()).filter(f -> f.endsWith(".png") || f.endsWith(".jpg")).collect(Collectors.toList());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            for (int i = 0; i < result.size(); i++) {
                File file = new File(result.get(i));
                snapshotThumbnailListModel.addElement(new SnapshotThumbnail(file));
                int progress = (int) ((1. + i) / result.size() * 100);
                e.updateValue(progress);

            }
        }).start();
    }

}


