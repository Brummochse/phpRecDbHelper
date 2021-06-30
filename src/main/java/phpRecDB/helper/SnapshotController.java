package phpRecDB.helper;

import org.apache.commons.io.FileUtils;
import phpRecDB.helper.gui.ProgressBarDialog;
import phpRecDB.helper.gui.SnapshotThumbnail;
import phpRecDB.helper.gui.ThumbnailListRenderer;
import phpRecDB.helper.lambdaInterface.KeyPressedListener;
import phpRecDB.helper.lambdaInterface.MouseDoubleClickedListener;
import phpRecDB.helper.media.SnapshotMaker;
import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.web.transfer.Screenshot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SnapshotController {
    private DefaultListModel<SnapshotThumbnail> snapshotThumbnailListModel = new DefaultListModel<>();
    private JList<SnapshotThumbnail> snapshotThumbnailList;

    public SnapshotController(JList<SnapshotThumbnail> snapshotThumbnailList) {
        this.snapshotThumbnailList = snapshotThumbnailList;
        initView();
        initFolderObserver();
    }

    private void initFolderObserver() {
        Runnable helloRunnable = this::synchronizeWithFolder;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.SECONDS);
    }

    public void clear() {
        snapshotThumbnailListModel.clear();
    }

    public void createSnapshots(List<MediaTitle> selectedMediaTitles, int count, long delay) {
        if (SnapshotMaker.getSnapshotFolder() == null) {
            SnapshotMaker.createNewSnapshotFolder();
        }

        for (MediaTitle mediaTitle : selectedMediaTitles) {
            if (mediaTitle.isMenu()) {
                SnapshotMaker.snapshot(mediaTitle, 1, 0);
            } else {
                SnapshotMaker.snapshot(mediaTitle, count, delay);
            }
        }
        loadSnapshotThumbnailsAction();
    }

    public Vector<Screenshot> getSnapshots() {
        Vector<Screenshot> screenshots = new Vector<>();
        for (SnapshotThumbnail snapshot : Collections.list(snapshotThumbnailListModel.elements())) {
            try {
                File imgFile = snapshot.getImgFile();
                byte[] fileContent = FileUtils.readFileToByteArray(imgFile);
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                screenshots.add(new Screenshot(encodedString));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return screenshots;
    }

    private void initView() {
        snapshotThumbnailList.setModel(snapshotThumbnailListModel);
        snapshotThumbnailList.setCellRenderer(new ThumbnailListRenderer());
        snapshotThumbnailList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        snapshotThumbnailList.setVisibleRowCount(1);

        snapshotThumbnailList.addKeyListener((KeyPressedListener) this::thumbnailKeyPressed);
        snapshotThumbnailList.addMouseListener((MouseDoubleClickedListener) this::thumbnailDoubleClickedAction);
    }

    private void thumbnailKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            int selectedIndex = snapshotThumbnailList.getSelectedIndex();
            removeSnapshot(selectedIndex);
        }
    }

    private void removeSnapshot(int selectedIndex) {
        if (selectedIndex >= 0) {
            SwingUtilities.invokeLater(() -> {
                SnapshotThumbnail snapshotThumbnail = snapshotThumbnailListModel.get(selectedIndex);
                snapshotThumbnail.deleteSourceFile();
                snapshotThumbnailListModel.remove(selectedIndex);
                snapshotThumbnailList.revalidate();
                snapshotThumbnailList.repaint();
            });

        }
    }

    /**
     * removes all snapshots thumbnails which does not exist as file in the snaphot folder
     * -> this allows deletion of files outside of the tool
     *
     * attention: new files in the snapshot folder (e.g. files copied inside the folder) get not recognized
     */
    public void synchronizeWithFolder() {
        File snapshotFolder = SnapshotMaker.getSnapshotFolder();
        if (snapshotFolder == null) {
            return;
        }
        List<String> s1 = getImgFiles(snapshotFolder);
        List<String> s2 = Collections.list(snapshotThumbnailListModel.elements()).stream().map(e -> e.getImgFile().toString()).collect(Collectors.toList());
        LinkedList<Integer> toRemoveIds = new LinkedList<>();
        for (int i = s2.size() - 1; i >= 0; i--) {
            if (!s1.contains(s2.get(i))) {
                toRemoveIds.add(i);
            }
        }
        for (Integer id : toRemoveIds) {
            removeSnapshot(id);
        }
    }

    private void thumbnailDoubleClickedAction(MouseEvent e) {
        int index = snapshotThumbnailList.locationToIndex(e.getPoint());
        if (index >= 0) {
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
            List<String> result = getImgFiles(path);
            for (int i = 0; i < result.size(); i++) {
                File file = new File(result.get(i));
                snapshotThumbnailListModel.addElement(new SnapshotThumbnail(file));
                int progress = (int) ((1. + i) / result.size() * 100);
                e.updateValue(progress);

            }
        }).start();
    }


    private List<String> getImgFiles(File folder) {
        List<String> result = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(folder.toPath())) {
            result = walk.map(x -> x.toString()).filter(f -> f.endsWith(".png") || f.endsWith(".jpg")).collect(Collectors.toList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }


}


