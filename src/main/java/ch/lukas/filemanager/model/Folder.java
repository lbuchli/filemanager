package ch.lukas.filemanager.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.UncheckedIOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class Folder extends Node implements Publisher<Node> {

    private Vector<Node> children;
    private Set<Subscriber<? super Node>> searchSubscribers;
    private java.nio.file.Path path;
    private Thread searchThread;
   
    public Folder(Folder parent, String name) throws FileNotFoundException {
        super(parent, name);
        
        searchSubscribers = new HashSet<Subscriber<? super Node>>();
        
        path = Paths.get(getPath());
        if (!Files.exists(path) || !Files.isDirectory(path)) {
        	throw new FileNotFoundException();
        }
    }

    public Node getChild(String name) throws FileNotFoundException {
        for (Node n : getChildren()) {
        	if (n.getName().equals(name)) {
        		return n;
        	}
        }
        throw new FileNotFoundException();
    }
    
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
    
    private Node pathToNode(java.nio.file.Path p) {
    	Folder parent;
		try {
			parent = Path.constructPath(p.getParent().toString());
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
    
    public void cancelSearch() {
    	if (searchThread != null) {
        	searchThread.interrupt();
    	}
    }

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
    
    public void updateChildren() {
    	children = null;
    	getChildren();
    }

    public boolean isFolder() { return true; }

	@Override
	public void subscribe(Subscriber<? super Node> sub) {
		searchSubscribers.add(sub);
	}
	
	public boolean isSubscriber(Subscriber<? super Node> sub) {
		return searchSubscribers.contains(sub);
	}
}
