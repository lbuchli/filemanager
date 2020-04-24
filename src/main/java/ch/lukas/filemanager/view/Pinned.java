package ch.lukas.filemanager.view;

import java.awt.Color;

import javax.swing.JList;
import javax.swing.JPanel;

import ch.lukas.filemanager.controller.PinnedController;
import ch.lukas.filemanager.model.Folder;
import ch.lukas.filemanager.model.PinnedFolders;
import ch.lukas.filemanager.view.renderer.PinnedCellRenderer;

/**
 * A list of the currently pinned folders
 * @author lukas
 */
public class Pinned extends JPanel {
	
	private static final long serialVersionUID = -8335436404111454893L;
	private static final int ROW_HEIGHT = 22;
	
	private JList<Folder> list;
	
	public Pinned() {
		list = new JList<Folder>(PinnedFolders.getInstance());
		list.setCellRenderer(new PinnedCellRenderer());
		list.addMouseListener(new PinnedController());
		list.setFixedCellHeight(ROW_HEIGHT);
		list.setToolTipText("Pinned Folders");
		
		add(list);
		
		setBackground(Color.WHITE);
	}
}
