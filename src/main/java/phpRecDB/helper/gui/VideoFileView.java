package phpRecDB.helper.gui;

import phpRecDB.helper.util.MediaUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileView;

public class VideoFileView extends FileView {

	private static List<String> videoFileEndings = Arrays.asList(new String[] { "mpg", "avi", "mkv", "mp4", "vob", "ts", "dv","mov","m4a","3gp","3g2","mj2","mts" });

	private ImageIcon iconDvd= new ImageIcon(getClass().getResource("/dvd.png"));
	private ImageIcon iconBR= new ImageIcon(getClass().getResource("/bluray.png"));
	private ImageIcon iconVideo= new ImageIcon(getClass().getResource("/video.png"));


	public Icon getIcon(File f) {
		// Do display custom icons

		// If dir
		if (f.isDirectory()) {
			if (MediaUtil.isDVDFolder(f)) {
				return iconDvd;
			}
			if (MediaUtil.isBRFolder(f)) {
				return iconBR;
			}
		}
		if (f.isFile()) {
			
			String ext = f.getName().substring(f.getName().lastIndexOf('.') + 1);
			if (videoFileEndings.contains(ext)) {
				return iconVideo;
			}
		}

		return super.getIcon(f);
	}



}