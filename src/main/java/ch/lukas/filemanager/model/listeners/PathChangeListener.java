package ch.lukas.filemanager.model.listeners;

/**
 * A listener to be notified when the current path changes
 * @author lukas
 */
@FunctionalInterface
public interface PathChangeListener {
	
	public void onChange(String newPath);
}
