package ch.lukas.filemanager.model;

import java.util.Vector;

import javax.swing.DefaultListModel;

/**
 * A singleton containing the currently pinned folders.
 * @author lukas
 */
public class PinnedFolders extends DefaultListModel<Folder> {

	private static final long serialVersionUID = 3878820193133500210L;

	private static PinnedFolders instance;
    private Vector<Folder> pinned;

    private PinnedFolders() {
        pinned = new Vector<Folder>();
    }

    /**
     * Get the instance
     * @return the instance
     */
    public static PinnedFolders getInstance() {
        if (instance == null) {
            instance = new PinnedFolders();
        }

        return instance;
    }

    /**
     * Get the pinned folders
     * @return the pinned folders
     */
    public Vector<Folder> getPinned() {
        return pinned;
    }
    
    /**
     * Remove a pinned folder
     * @param index the index of the folder to remove
     */
    public void removeElement(int index) {
    	pinned.remove(index);
    	fireContentsChanged(this, index, index);
    }

    @Override
    public int getSize() {
        return pinned.size();
    }

    @Override
    public Folder getElementAt(int index) {
        return pinned.get(index);
    }

    @Override
    public void addElement(Folder folder) {
        pinned.add(folder);
        fireContentsChanged(this, 0, getSize());
    }
}
