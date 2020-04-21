package ch.lukas.filemanager.view;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class PinnedContextMenu extends JPopupMenu {

	private JMenuItem unpin = new JMenuItem("Unpin");
	
	public PinnedContextMenu(ActionListener buttonListener) {
		add(unpin);
		unpin.addActionListener(buttonListener);
	}
}
