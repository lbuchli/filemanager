package ch.lukas.filemanager.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import ch.lukas.filemanager.model.Folder;
import ch.lukas.filemanager.model.Node;
import ch.lukas.filemanager.model.Path;
import ch.lukas.filemanager.model.PinnedFolders;
import ch.lukas.filemanager.view.FileContextMenu;
import ch.lukas.filemanager.view.FileRenameEditor;

public class FileContextMenuController extends MouseAdapter implements ActionListener {

	private FileContextMenu menu;
	private MouseEvent lastEvent;
	
	public FileContextMenuController() {
		menu = new FileContextMenu(this);
	}
	
    @Override
    public void mousePressed(MouseEvent e) {
    	lastEvent = e;
       if (e.getButton() == 3) {
    	   int row = ((JTable)e.getComponent()).rowAtPoint(e.getPoint());
    	   if (Path.getInstance().getNodeAtRow(row).isFolder()) {
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
		Node selected = Path.getInstance().getNodeAtRow(row);
		switch (e.getActionCommand()) {
		case "Rename":
			table.changeSelection(row, 0, false, false);
			Path.getInstance().requestEdit(row);
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
