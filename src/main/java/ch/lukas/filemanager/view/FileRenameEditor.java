package ch.lukas.filemanager.view;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import ch.lukas.filemanager.model.Node;

/**
 * An editor to rename files in the JTable
 * @author lukas
 *
 */
public class FileRenameEditor extends AbstractCellEditor implements TableCellEditor {

	private static final long serialVersionUID = -1963277631631165503L;
	
	JTextField editor = new JTextField();

	@Override
	public Component getTableCellEditorComponent(JTable table, Object obj, boolean focused, int row, int column) {
		Node node = (Node) obj;
		editor.setText(node.getName());
		return editor;
	}

	@Override
	public Object getCellEditorValue() {
		return editor.getText();
	}
}
