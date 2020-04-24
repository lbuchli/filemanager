package ch.lukas.filemanager.controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JTable;

import ch.lukas.filemanager.model.Folder;
import ch.lukas.filemanager.model.Node;
import ch.lukas.filemanager.model.CurrentPath;
import ch.lukas.filemanager.model.PinnedFolders;
import ch.lukas.filemanager.view.FileContextMenu;

/**
 * Controls the file list, changing folders at mouse press and offering a context menu
 * @author lukas
 */
public class FileListController extends MouseAdapter implements ActionListener {
	
	private FileContextMenu menu;
	private MouseEvent lastEvent;
	
	public FileListController() {
		menu = new FileContextMenu(this);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		lastEvent = e;
		if (e.getButton() == 1) {
			JTable table = (JTable) e.getComponent(); 
			int row = table.rowAtPoint(e.getPoint());
			if (table.isRowSelected(row)) {
				Node chosen = CurrentPath.getInstance().getNodeAtRow(row);
				if (chosen.isFolder()) {
					CurrentPath.getInstance().setCurrentFolder((Folder) chosen);
				} else {
					try {
						Desktop.getDesktop().open(new File(chosen.getPath()));
					} catch (IOException e1) {}
				}
			}
		} else if (e.getButton() == 3) {
			int row = ((JTable)e.getComponent()).rowAtPoint(e.getPoint());
	    	   if (CurrentPath.getInstance().getNodeAtRow(row).isFolder()) {
	    		   menu.setPinEnabled(true);
	    	   } else {
	    		   menu.setPinEnabled(false);
	    	   }
	    	   
	    	   menu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

    @Override
	public void actionPerformed(ActionEvent e) {
    	JTable table = (JTable) lastEvent.getComponent();
    	int row = table.rowAtPoint(lastEvent.getPoint());
		Node selected = CurrentPath.getInstance().getNodeAtRow(row);
		switch (e.getActionCommand()) {
		case "Rename":
			table.changeSelection(row, 0, false, false);
			CurrentPath.getInstance().requestEdit(row);
			table.editCellAt(row, 0);
			break;
		case "Pin":
			if (selected.isFolder()) {
				PinnedFolders.getInstance().addElement((Folder) selected);
			}
			break;
		}
	}
}
