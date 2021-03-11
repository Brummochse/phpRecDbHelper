package phpRecDB.helper.gui;

import phpRecDB.helper.media.data.types.FileInputHandler;
import phpRecDB.helper.media.data.types.FileInputHandlers;

import javax.swing.*;
import javax.swing.filechooser.FileView;
import java.io.File;

public class VideoFileView extends FileView {

	public Icon getIcon(File f) {
		FileInputHandler type = FileInputHandlers.evaluateType(f);
		ImageIcon resourceIcon = type.getResourceIcon(f);
		if (resourceIcon !=null) {
			return resourceIcon;
		}
		return super.getIcon(f);
	}
}