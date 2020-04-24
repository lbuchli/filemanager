package ch.lukas.filemanager.view;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * A context menu to pop up when right-clicking on the file list
 * @author lukas
 */
public class FileContextMenu extends JPopupMenu {

	private static final long serialVersionUID = -290954529869128284L;
	
	private JMenuItem rename = new JMenuItem("Rename");
	private JMenuItem pin = new JMenuItem("Pin");
	
	public FileContextMenu(ActionListener buttonListener) {
		add(rename);
		add(pin);
		
		rename.addActionListener(buttonListener);
		pin.addActionListener(buttonListener);
	}
	
	public void setPinEnabled(boolean enabled) {
		pin.setEnabled(enabled);
	}
}
