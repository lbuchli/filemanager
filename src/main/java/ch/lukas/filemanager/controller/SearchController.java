package ch.lukas.filemanager.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.lukas.filemanager.model.CurrentPath;

/**
 * Starts or stops the search, depending on the search input field value
 * @author lukas
 */
public class SearchController implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().isEmpty()) {
			CurrentPath.getInstance().stopSearch();
		} else {
			CurrentPath.getInstance().startSearch(e.getActionCommand());
		}
	}
}
