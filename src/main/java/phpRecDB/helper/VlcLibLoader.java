package phpRecDB.helper;

import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.RuntimeUtil;

import javax.swing.*;
import java.io.File;
import java.util.prefs.Preferences;

public class VlcLibLoader {

    String prefs_vlc_path = "vlc_path";

    /*
     * when the user addresses a wrong VLC folder at the first time, the application prohibits
     * to select a second time by purpose, because tests have shown, that the "isVlcLibWorking" does
     * not work properly in some situation when it was used several times in a row
     */
    public boolean isVlcLibAvailable() {
        if (isVlcLibWorking()) {
            return true; //VLC is installed properly
        }
        Preferences prefs = Preferences.userNodeForPackage(VlcLibLoader.class);
        String vlcPath = prefs.get(prefs_vlc_path, null);
        if (vlcPath == null) {
            String architectureType = System.getProperty("sun.arch.data.model");
            JOptionPane.showMessageDialog(null, "This application requires a VLC 3.X.X (" + architectureType + " bit) instance. Please select a suitable VLC folder.", "Select VLC instance ...", JOptionPane.INFORMATION_MESSAGE);
            vlcPath = openVlcDirectoryChooser();
        }
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcPath);
        if (isVlcLibWorking()) {
            prefs.put(prefs_vlc_path, vlcPath);
            return true; //path contains a valid VLC instance
        } else {
            prefs.remove(prefs_vlc_path);
        }
        return false;
    }

    private String openVlcDirectoryChooser() {
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            return selectedFile.getPath();
        }
        return "";
    }

    public boolean isVlcLibWorking() {
        try {
            NativeLibrary.getInstance(RuntimeUtil.getLibVlcLibraryName());
            LibVlc.libvlc_get_version();
        } catch (UnsatisfiedLinkError e) {
            return false;
        } catch (NoClassDefFoundError e) {
            return false;
        }
        return true;
    }
}
