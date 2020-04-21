package ch.lukas.filemanager.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.InsetsUIResource;

import ch.lukas.filemanager.controller.NavButtonController;
import ch.lukas.filemanager.controller.PathController;
import ch.lukas.filemanager.controller.SearchController;
import ch.lukas.filemanager.model.Path;

public class Navigation extends JPanel {
	
	private JTextField path;
	private JTextField search;
	
	private JButton back = new JButton("🠔");
	private JButton up = new JButton("🠕");
	
	public Navigation() {
		NavButtonController buttonListener = new NavButtonController();
		JPanel buttons = new JPanel();
		buttons.setBackground(Color.WHITE);
		buttons.add(back);
		buttons.add(up);
		back.addActionListener(buttonListener);
		up.addActionListener(buttonListener);
		add(buttons, BorderLayout.WEST);
		back.setToolTipText("Back");
		up.setToolTipText("Up");
		back.setBackground(Color.WHITE);
		up.setBackground(Color.WHITE);
		
		path = new JTextField(Path.getInstance().toString(), 30);
		add(path, BorderLayout.CENTER);
		path.addActionListener(new PathController());
		path.setToolTipText("Current Path");
		
		search = new JTextField(10);
		add(search, BorderLayout.EAST);
		search.addActionListener(new SearchController());
		search.setToolTipText("Search");
		
		Path.getInstance().addPathChangeListener((String newPath) -> path.setText(newPath));
    	Path.getInstance().addSearchStopListener(() -> search.setText(""));
    	
    	setBackground(Color.WHITE);
	}
	
}