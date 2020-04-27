package ch.lukas.filemanager.view.renderer;

import java.awt.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.CharacterIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import ch.lukas.filemanager.model.File;
import ch.lukas.filemanager.model.Node;
import ch.lukas.filemanager.view.Icons;

/**
 * Render the nodes in the JTable file list
 * @author lukas
 */
public class NodeCellRenderer implements TableCellRenderer {
	
	@Override
	public Component getTableCellRendererComponent(
			JTable table,
			Object obj,
			boolean isSelected,
			boolean hasFocus,
			int row,
			int column
			) {
		Node selected = (Node) obj;
		java.nio.file.Path path = Paths.get(selected.getPath());
		
		JLabel result;
		switch (column) {
		case 0: 
			result = new JLabel(selected.getName(), getIconFor(selected), JLabel.LEFT);
			break;
		case 1: 
			try {
				if (selected.isFolder()) {
					result = new JLabel("");
				} else {
					result = new JLabel(humanReadableSize(Files.size(path)), JLabel.CENTER);
				}
			} catch (IOException e) {
				result = new JLabel("ERROR: Could not read size");
			}
			break;
		case 2: 
			try {
				Date modified = Date.from(Files.getLastModifiedTime(path).toInstant());
				result = new JLabel(new SimpleDateFormat("hh:mm dd.MM.yyyy  ").format(modified), JLabel.RIGHT);
			} catch (IOException e) {
				result = new JLabel("ERROR: Could not read modified time");
			}
			break;
		default: result = new JLabel("ERROR");
		}
		
		if (isSelected) {
            result.setBackground(table.getSelectionBackground());
        } else {
            result.setBackground(table.getSelectionForeground());
        }
		
		return result;
	}

	private ImageIcon getIconFor(Node node) {
		String data;
		if (node.isFolder()) {
			data = Icons.FOLDER;
		} else {
			switch (((File) node).getType()) {
			case TEXT:
				data = Icons.TEXT;
				break;
			case IMAGE:
				data = Icons.IMAGE;
				break;
			case AUDIO:
				data = Icons.AUDIO;
				break;
			case VIDEO:
				data = Icons.VIDEO;
				break;
			case EXEC:
				data = Icons.EXECUTABLE;
				break;
			case PDF:
				data = Icons.PDF;
				break;
			default:
				data = Icons.OTHER;
				break;
			}
		}
		
		return Icons.getImageIcon(data);
	}

	// source: https://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java
	private String humanReadableSize(long bytes) {
		long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
	    if (absB < 1024) {
	        return bytes + " B";
	    }
	    long value = absB;
	    CharacterIterator ci = new StringCharacterIterator("KMGTPE");
	    for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
	        value >>= 10;
	        ci.next();
	    }
	    value *= Long.signum(bytes);
	    return String.format("%.1f %ciB", value / 1024.0, ci.current());
	}
}
