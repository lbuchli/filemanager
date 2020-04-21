package ch.lukas.filemanager.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import ch.lukas.filemanager.controller.FileContextMenuController;
import ch.lukas.filemanager.controller.FileListController;
import ch.lukas.filemanager.model.Node;
import ch.lukas.filemanager.model.Path;
import ch.lukas.filemanager.view.renderer.NodeCellRenderer;

public class FileList extends JPanel {

	private static final int ROW_HEIGHT = 22;
	
    private JTable table;

    public FileList() {
    	table = new JTable(Path.getInstance());
    	table.setDefaultRenderer(Node.class, new NodeCellRenderer());
    	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	table.addMouseListener(new FileListController());
    	table.addMouseListener(new FileContextMenuController());
    	table.getColumnModel().getColumn(0).setCellEditor(new FileRenameEditor());
    	table.setRowHeight(ROW_HEIGHT);
    	table.setGridColor(Color.LIGHT_GRAY);
    	add(new JScrollPane(table), BorderLayout.CENTER);
    	
    	setBackground(Color.white);
    }

}
