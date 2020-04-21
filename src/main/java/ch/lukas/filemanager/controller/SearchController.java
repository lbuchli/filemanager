package ch.lukas.filemanager.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.lukas.filemanager.model.Path;
import ch.lukas.filemanager.model.listeners.SearchStopListener;

public class SearchController implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().isEmpty()) {
			Path.getInstance().stopSearch();
		} else {
			Path.getInstance().startSearch(e.getActionCommand());
		}
	}
}
