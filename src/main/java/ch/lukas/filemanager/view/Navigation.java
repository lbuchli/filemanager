package ch.lukas.filemanager.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.lukas.filemanager.controller.NavButtonController;
import ch.lukas.filemanager.controller.PathController;
import ch.lukas.filemanager.controller.SearchController;
import ch.lukas.filemanager.model.CurrentPath;

/**
 * The navigation consisting on back buttons, a path input field and a search input field.
 * @author lukas
 */
public class Navigation extends JPanel {

	private static final long serialVersionUID = -464801905099601604L;
	
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
		
		path = new JTextField(CurrentPath.getInstance().toString(), 30);
		add(path, BorderLayout.CENTER);
		path.addActionListener(new PathController());
		path.setToolTipText("Current Path");
		
		search = new JTextField(10);
		add(search, BorderLayout.EAST);
		search.addActionListener(new SearchController());
		search.setToolTipText("Search");
		
		CurrentPath.getInstance().addPathChangeListener((String newPath) -> path.setText(newPath));
    	CurrentPath.getInstance().addSearchStopListener(() -> search.setText(""));
    	
    	setBackground(Color.WHITE);
	}
	
}