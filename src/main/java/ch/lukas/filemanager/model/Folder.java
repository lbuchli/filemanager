package ch.lukas.filemanager.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

/**
 * Represents a single folder in the file system. A folder contains children Nodes and can search for things
 * with this folder as the root of the search.
 * @author lukas
 */
public class Folder extends Node implements Publisher<Node> {

    private Vector<Node> children;
    private Set<Subscriber<? super Node>> searchSubscribers;
    private Path path;
    private Thread searchThread;
   
    public Folder(Folder parent, String name) throws FileNotFoundException {
        super(parent, name);
        
        searchSubscribers = new HashSet<Subscriber<? super Node>>();
        
        path = Paths.get(getPath());
        if (!Files.exists(path) || !Files.isDirectory(path)) {
        	throw new FileNotFoundException();
        }
    }

    /**
     * Get a child by name
     * @param name the name
     * @return the file if one exists
     * @throws FileNotFoundException
     */
    public Node getChild(String name) throws FileNotFoundException {
        for (Node n : getChildren()) {
        	if (n.getName().equals(name)) {
        		return n;
        	}
        }
        throw new FileNotFoundException();
    }
    
    /**
     * Start a search
     * @param text the text to search for
     */
    public void searchFor(String text) {
    	searchThread = new Thread(() -> {
    		try {
				Files.walk(path)
					.filter(p -> p.getFileName().toString().contains(text))
					.map(this::pathToNode)
					.forEach(this::notifySubscribers);
			} catch (IOException | UncheckedIOException e) {}
    	});
    	searchThread.start();
    }
    
   
    /**
     * Stop the current search if it exists.
     */
    public void cancelSearch() {
    	if (searchThread != null) {
        	searchThread.interrupt();
    	}
    }

    /**
     * Get the children nodes of this folder
     * @return the children nodes
     */
    public Vector<Node> getChildren() {
    	if (children == null) {
    		children = new Vector<Node>();
    		java.io.File[] files = new java.io.File(getPath()).listFiles();
    		
    		for (java.io.File file : files) {
    			if (file.isDirectory()) {
    				try {
						children.add(new Folder(this, file.getName()));
					} catch (FileNotFoundException e) {
						throw new RuntimeException(e.getMessage(), e.getCause());
					}
    			} else {
    				try {
						children.add(new File(this, file.getName()));
					} catch (FileNotFoundException e) {
						throw new RuntimeException(e.getMessage(), e.getCause());
					}
    			}
    		}
    	}
    	
    	return children;
    }
    
    /**
     * Update the children index (e.g. when having renamed a file)
     */
    protected void updateChildren() {
    	children = null;
    	getChildren();
    }

    @Override
    public boolean isFolder() { return true; }

	@Override
	public void subscribe(Subscriber<? super Node> sub) {
		searchSubscribers.add(sub);
	}
	
	/**
	 * Check if someone is a search subscriber	
	 * @param sub the subscriber
	 * @return true if it is a subscriber
	 */
	public boolean isSubscriber(Subscriber<? super Node> sub) {
		return searchSubscribers.contains(sub);
	}
	
	private Node pathToNode(Path p) {
	    Folder parent;
		try {
			parent = CurrentPath.constructPath(p.getParent().toString());
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e.getCause());
		}
		String name = p.getFileName().toString();
		try {
			return p.toFile().isDirectory() 
				? new Folder(parent, name) 
				: new File(parent, name);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e.getCause());
		} 
    }
	    
    private void notifySubscribers(Node node) {
    	for (Subscriber<? super Node> sub : searchSubscribers) {
    		sub.onNext(node);
    	}
    }
	    
}
