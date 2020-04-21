package ch.lukas.filemanager.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.lukas.filemanager.model.Path;

public class PathController implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Path.getInstance().setPath(e.getActionCommand());
	}
}
