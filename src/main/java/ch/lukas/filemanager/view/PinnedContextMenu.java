package ch.lukas.filemanager.view;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * A context menu for the pinned folders, providing the functionality to unpin them
 * @author lukas
 */
public class PinnedContextMenu extends JPopupMenu {

	private static final long serialVersionUID = -3865641810623203306L;
	
	private JMenuItem unpin = new JMenuItem("Unpin");
	
	public PinnedContextMenu(ActionListener buttonListener) {
		add(unpin);
		unpin.addActionListener(buttonListener);
	}
}
