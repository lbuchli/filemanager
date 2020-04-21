package ch.lukas.filemanager.model.listeners;

@FunctionalInterface
public interface PathChangeListener {
	
	public void onChange(String newPath);
}
