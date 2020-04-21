package ch.lukas.filemanager.controller;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JTable;

import ch.lukas.filemanager.model.Folder;
import ch.lukas.filemanager.model.Node;
import ch.lukas.filemanager.model.Path;

public class FileListController extends MouseAdapter {

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			JTable table = (JTable) e.getComponent(); 
			int row = table.rowAtPoint(e.getPoint());
			if (table.isRowSelected(row)) {
				Node chosen = Path.getInstance().getNodeAtRow(row);
				if (chosen.isFolder()) {
					Path.getInstance().setCurrentFolder((Folder) chosen);
				} else {
					try {
						Desktop.getDesktop().open(new File(chosen.getPath()));
					} catch (IOException e1) {}
				}
			}
		}
	}
}
