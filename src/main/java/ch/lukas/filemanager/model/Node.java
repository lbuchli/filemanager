package ch.lukas.filemanager.model;

public abstract class Node {
	
	private static String separator;

    private String name;
    private Folder parent;

    public Node(Folder parent, String name) {
    	separator = System.getProperty("file.separator");
        this.name = name;
        this.parent = parent;
    }
    
    public String getPath() {
    	
    	if (getParent() == null) {
    		return name.isEmpty() ? "/" : name;
    	} else {
    		String pPath = getParent().getPath();
    		return pPath + (pPath.contentEquals("/") ? "" : separator) + getName();
    	}
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
    	java.io.File old = new java.io.File(getPath());
    	String parentPath = parent == null ? "" : parent.getPath() + separator;
    	old.renameTo(new java.io.File(parentPath + name));
    	if (parent != null) {
    		parent.updateChildren();
    	}
    }
    
    public Folder getParent() {
    	return parent;
    }
    
    public abstract boolean isFolder();
}
