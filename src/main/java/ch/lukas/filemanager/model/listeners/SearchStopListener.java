package ch.lukas.filemanager.model.listeners;

/**
 * A listener to be notified when the search gets cancelled
 * @author lukas
 */
@FunctionalInterface
public interface SearchStopListener {
	public void onSearchStop();
}
