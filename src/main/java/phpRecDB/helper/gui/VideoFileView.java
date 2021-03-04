package phpRecDB.helper.gui;

import phpRecDB.helper.media.data.types.Type;
import phpRecDB.helper.media.data.types.Types;

import java.io.File;

import javax.swing.Icon;
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