package ch.lukas.filemanager.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.lukas.filemanager.model.Path;

public class NavButtonController implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "🠔": 
				Path.getInstance().setCurrentFolder(Path.getInstance().getPreviousFolder());
				break;
			case "🠕": 
				Path.getInstance().setCurrentFolder(Path.getInstance().getCurrentFolder().getParent());
				break;
		}
		Path.getInstance().stopSearch();
	}
}
