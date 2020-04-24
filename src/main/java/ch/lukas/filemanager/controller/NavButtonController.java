package ch.lukas.filemanager.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.lukas.filemanager.model.CurrentPath;

/**
 * Controls the two navigation buttons at the top left corner, reacting on presses
 * @author lukas
 */
public class NavButtonController implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "🠔": 
				CurrentPath.getInstance().setCurrentFolder(CurrentPath.getInstance().getPreviousFolder());
				break;
			case "🠕": 
				CurrentPath.getInstance().setCurrentFolder(CurrentPath.getInstance().getCurrentFolder().getParent());
				break;
		}
		CurrentPath.getInstance().stopSearch();
	}
}
