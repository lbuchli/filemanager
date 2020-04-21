package ch.lukas.filemanager.view;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class FileContextMenu extends JPopupMenu {

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
