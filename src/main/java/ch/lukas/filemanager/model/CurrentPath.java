package ch.lukas.filemanager.model;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import javax.swing.table.AbstractTableModel;

import ch.lukas.filemanager.model.listeners.PathChangeListener;
import ch.lukas.filemanager.model.listeners.SearchStopListener;

/**
 * A singleton where the current path resides. This class provides a table model which either shows the
 * files in the current folder or search results.
 * @author lukas
 */
public class CurrentPath extends AbstractTableModel implements Subscriber<Node> {

	private static final long serialVersionUID = 8117224923308976061L;
	
	private static final String[] COLUMN_NAMES = new String[] {"Name", "Size", "Modified"};
    private static CurrentPath instance;

    private Folder currentFolder;
    private Folder previousFolder;
    
    private Set<PathChangeListener> pathChangeListeners;
    private Set<SearchStopListener> searchStopListeners;
    
    private Set<Integer> requestedEdits;
    
    private boolean isSearching;
    private Vector<Node> searchResults;
   
    private CurrentPath() {
        pathChangeListeners = new HashSet<PathChangeListener>();
        searchStopListeners = new HashSet<SearchStopListener>();
        
        requestedEdits = new HashSet<Integer>();
        
        searchResults = new Vector<Node>();
        if (!setPath(System.getProperty("user.home"))) {
        	throw new RuntimeException("Could not set default path");
        }
        previousFolder = currentFolder;
    }

    /**
     * Get the instance of this class
     * @return the instance
     */
    public static CurrentPath getInstance() {
        if (instance == null) {
            instance = new CurrentPath();
        }

        return instance;
    }
    
    @Override
    public String toString() {
    	return currentFolder.getPath();
    }
    
    /**
     * Get the current folder
     * @return current folder
     */
    public Folder getCurrentFolder() {
    	return currentFolder;
    }
    
    /**
     * Sets the current folder, updating the view
     * @param f the new folder
     */
    public void setCurrentFolder(Folder f) {
    	previousFolder = currentFolder;
    	currentFolder = f;
    	stopSearch();
    	fireUpdateData();
    }
    
    /**
     * Start a search
     * @param text the text to search for
     */
    public void startSearch(String text) {
    	searchResults = new Vector<Node>();
    	isSearching = true;
    	
    	if (!currentFolder.isSubscriber(this)) {
        	currentFolder.subscribe(this);
    	}
    	currentFolder.searchFor(text);
    	fireUpdateData();
    }
    
    /**
     * Cancel the search if one is currently active
     */
    public void stopSearch() {
    	if (isSearching) {
        	isSearching = false;
        	currentFolder.cancelSearch();
        	fireUpdateData();
        	
        	for (SearchStopListener listener : searchStopListeners) {
        		listener.onSearchStop();
        	}
    	}
    }
    
    /**
     * Check if currently searching
     * @return true if searching
     */
    public boolean isSearching() {
    	return isSearching;
    }
    
    /**
     * Get the node present at a row in the table
     * @param row the number of the row to get the node 
     * @return the node present there
     */
    public Node getNodeAtRow(int row) {
    	return isSearching ? searchResults.get(row) : currentFolder.getChildren().get(row);
    }
    
    /**
     * Subscribe to path changes
     * @param listener the subscriber/listener
     */
    public void addPathChangeListener(PathChangeListener listener) {
    	pathChangeListeners.add(listener);
    }

    /**
     * Subscribe to search stops
     * @param listener the subscriber/listener
     */
	public void addSearchStopListener(SearchStopListener listener) {
		searchStopListeners.add(listener);
	}
	
	/**
	 * Request an edit for a specific cell. This request will be dismissed once edited
	 * or in case of a data change.
	 * @param row
	 */
	public void requestEdit(int row) {
		requestedEdits.add(row);
	}

    /**
     * Set the current path
     * @param path the new path 
     * @return 'true' if path was valid and is set
     */
    public boolean setPath(String path) {
    	previousFolder = currentFolder;
        try {
			currentFolder = constructPath(path);
		} catch (FileNotFoundException e) {
			return false;
		}
        fireUpdateData();
        return true;
    }
    
    /**
     * Construct a folder path from a string
     * @param path the path, formatted as a string
     * @return the folder present there
     * @throws FileNotFoundException 
     */
    public static Folder constructPath(String path) throws FileNotFoundException {
    	String[] folders = path.split("[\\/]");
    	if (folders.length < 1) {
    		throw new FileNotFoundException();
    	}
    	
    	Folder current = new Folder(null, folders[0]);
        
        for (int i = 1; i < folders.length; i++) {
        	current = (Folder) current.getChild(folders[i]);
        }
        
        return current;
    }

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return isSearching ? searchResults.size() : currentFolder.getChildren().size();
	}

	@Override
	public Node getValueAt(int row, int column) {
		 return isSearching ? searchResults.get(row) 
					  : currentFolder.getChildren().get(row);
	}
	
	@Override
	public Class<?> getColumnClass(int column) {
		return Node.class;
	}
	
	@Override
	public String getColumnName(int i) {
		return COLUMN_NAMES[i];
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 0 && requestedEdits.contains(row)) {
			requestedEdits.remove(row);
			return true;
		}
		return false;
	}
	
	@Override
	public void setValueAt(Object name, int row, int column) {
		if (column == 0) {
			getValueAt(row, column).setName(name.toString());
		}
		fireUpdateData();
	}
	
	/**
	 * Get the previous folder
	 * @return the previous folder or the current one if there is none.
	 */
	public Folder getPreviousFolder() {
		return previousFolder;
	}

	@Override
	public void onComplete() {}

	@Override
	public void onError(Throwable cause) {
		throw new RuntimeException("Error during search", cause);
	}

	@Override
	public void onNext(Node node) {
		int size = searchResults.size();
		searchResults.add(node);
		fireTableRowsInserted(size-1, size);
	}

	@Override
	public void onSubscribe(Subscription sub) {}
	
	private void firePathChangeEvent() {
		String newPath = currentFolder.getPath();
		for (PathChangeListener listener : pathChangeListeners) {
			listener.onChange(newPath);
		}
	}
	
	private void fireUpdateData() {
        fireTableDataChanged();
        firePathChangeEvent();
        requestedEdits.clear();
	}
}
