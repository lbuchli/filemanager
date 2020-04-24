package ch.lukas.filemanager.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import ch.lukas.filemanager.controller.FileListController;
import ch.lukas.filemanager.model.Node;
import ch.lukas.filemanager.model.CurrentPath;
import ch.lukas.filemanager.view.renderer.NodeCellRenderer;

/**
 * Displays the list of files in the current path or search results
 * @author lukas
 */
public class FileList extends JPanel {

	private static final long serialVersionUID = 8557035732852155633L;

	private static final int ROW_HEIGHT = 22;
    private JTable table;

    public FileList() {
    	table = new JTable(CurrentPath.getInstance());
    	table.setDefaultRenderer(Node.class, new NodeCellRenderer());
    	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	table.addMouseListener(new FileListController());
    	table.getColumnModel().getColumn(0).setCellEditor(new FileRenameEditor());
    	table.setRowHeight(ROW_HEIGHT);
    	table.setGridColor(Color.LIGHT_GRAY);
    	add(new JScrollPane(table), BorderLayout.CENTER);
    	
    	setBackground(Color.white);
    }

}
