package ch.lukas.filemanager.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import ch.lukas.filemanager.model.Folder;
import ch.lukas.filemanager.model.Path;
import ch.lukas.filemanager.model.PinnedFolders;
import ch.lukas.filemanager.view.PinnedContextMenu;

public class PinnedController extends MouseAdapter implements ActionListener {
	
	PinnedContextMenu menu;
	MouseEvent previousEvent;
	
	public PinnedController() {
		menu = new PinnedContextMenu(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		previousEvent = e;
		if (e.getButton() == 1) {
			JList<Folder> list = (JList<Folder>) e.getComponent();
			int row = list.locationToIndex(e.getPoint());
			if (list.isSelectedIndex(row)) {
				Folder chosen = PinnedFolders.getInstance().getElementAt(row);
				Path.getInstance().setCurrentFolder(chosen);
				Path.getInstance().stopSearch();
			}
		} else if (e.getButton() == 3) {
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int row = ((JList) previousEvent.getComponent()).locationToIndex(previousEvent.getPoint());
		PinnedFolders.getInstance().removeElement(row);
	}
}
