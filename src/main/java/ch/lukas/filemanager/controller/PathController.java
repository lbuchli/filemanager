package ch.lukas.filemanager.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.lukas.filemanager.model.CurrentPath;

/**
 * Sets the path when typed by the user in the top input field
 * @author lukas
 */
public class PathController implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		CurrentPath.getInstance().setPath(e.getActionCommand());
	}
}
