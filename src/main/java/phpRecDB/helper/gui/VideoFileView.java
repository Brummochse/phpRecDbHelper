package phpRecDB.helper.gui;

import phpRecDB.helper.media.types.Type;
import phpRecDB.helper.media.types.Types;
import phpRecDB.helper.util.MediaUtil;
import phpRecDB.helper.util.ResourceUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileView;

public class VideoFileView extends FileView {

	public Icon getIcon(File f) {
		Type type = Types.evaluateType(f);
		if (type.getResourceIcon()!=null) {
			return type.getResourceIcon();
		}
		return super.getIcon(f);
	}
}