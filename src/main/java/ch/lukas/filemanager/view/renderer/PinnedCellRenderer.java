package ch.lukas.filemanager.view.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ch.lukas.filemanager.model.Folder;
import ch.lukas.filemanager.view.Icons;

/**
 * Render the pinned folders in the pinned JList
 * @author lukas
 */
public class PinnedCellRenderer extends JLabel implements ListCellRenderer<Folder>  {

	@Override
	public Component getListCellRendererComponent(
			JList<? extends Folder> list,
			Folder folder,
			int index,
			boolean isSelected,
			boolean hasFocus
			) {
		
		String name = folder.getName();
		if (name.length() >= 14) {
			name = name.substring(0, 11) + "...";
		}
		
		JLabel result = new JLabel(name, Icons.getImageIcon(Icons.FOLDER), JLabel.LEFT);
		if (hasFocus || isSelected) {
			result.setBackground(Color.LIGHT_GRAY);
		}
		return result;
	}
}
