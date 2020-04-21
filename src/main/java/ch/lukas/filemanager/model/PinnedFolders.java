package ch.lukas.filemanager.model;

import java.io.FileNotFoundException;
import java.util.Vector;

import javax.swing.DefaultListModel;

public class PinnedFolders extends DefaultListModel<Folder> {

    private static PinnedFolders instance;

    private Vector<Folder> pinned;

    private PinnedFolders() {
        pinned = new Vector<Folder>();
        try {
        	// TODO more generic pinned folders?
			pinned.add(new Folder(Path.constructPath("/home/lukas"), ".config"));
			pinned.add(new Folder(Path.constructPath("/home/lukas"), ".local"));
			pinned.add(new Folder(Path.constructPath("/home/lukas"), "docs"));
			pinned.add(new Folder(Path.constructPath("/home/lukas"), "workspace"));
		} catch (FileNotFoundException e) {}
    }

    public static PinnedFolders getInstance() {
        if (instance == null) {
            instance = new PinnedFolders();
        }

        return instance;
    }

    public Vector<Folder> getPinned() {
        return pinned;
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
    
    public void removeElement(int index) {
    	pinned.remove(index);
    	fireContentsChanged(this, index, index);
    }
}
