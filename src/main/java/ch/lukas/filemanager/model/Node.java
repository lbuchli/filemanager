package ch.lukas.filemanager.model;

/**
 * Represents a folder or a file in the file system, consists of a name and a parent folder.
 * @author lukas
 */
public abstract class Node {
	
	private static String separator;

    private String name;
    private Folder parent;

    public Node(Folder parent, String name) {
    	separator = System.getProperty("file.separator");
        this.name = name;
        this.parent = parent;
    }
    
    /**
     * Get the string formatted path to this node
     * @return the path
     */
    public String getPath() {
    	if (separator == "\\") { // on  windows
    		if (getParent() == null) {
        		return "";
        	} else {
        		String pPath = getParent().getPath();
        		return pPath + (pPath.isEmpty() ? "" : separator) + getName();
        	}
    	} else { // assume unix
    		if (getParent() == null) {
        		return "/";
        	} else {
        		String pPath = getParent().getPath();
        		return pPath + (pPath.equals("/") ? "" : separator) + getName();
        	}
    	}
    	
    }

    /**
     * Get the name of the node
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the name of the node, renaming the file in the filesystem
     * @param name the new name
     */
    public void setName(String name) {
    	java.io.File old = new java.io.File(getPath());
    	String parentPath = parent == null ? "" : parent.getPath() + separator;
    	old.renameTo(new java.io.File(parentPath + name));
    	if (parent != null) {
    		parent.updateChildren();
    	}
    }
    
    /**
     * Get the parent folder of this node
     * @return the parent  folder
     */
    public Folder getParent() {
    	return parent;
    }
    
    /**
     * Check if node is a folder
     * @return true if it is a folder
     */
    public abstract boolean isFolder();
}
