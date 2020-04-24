package ch.lukas.filemanager.model;

import java.io.FileNotFoundException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a single file in the file system.
 * The file automatically determines a filetype based on the file name
 * @author lukas
 */
public class File extends Node {

	/**
	 * Different file types
	 * @author lukas
	 */
    public enum FileType {
        TEXT, IMAGE, AUDIO, VIDEO, EXEC, PDF, OTHER
    }

    private FileType type;

    public File(Folder parent, String name) throws FileNotFoundException {
        super(parent, name);
        
        java.nio.file.Path path = Paths.get(getPath());
        if (!Files.exists(path) || Files.isDirectory(path)) {
        	throw new FileNotFoundException();
        }
        
        type = determineType(path);
    }

    /**
     * Get the file type
     * @return the type
     */
    public FileType getType() {
        return type;
    }
    
    @Override
    public boolean isFolder() { return false; }
    
    private static FileType determineType(Path path) throws FileNotFoundException {
    	FileType result;
    	String mimeType = URLConnection.guessContentTypeFromName(path.toFile().getName());
        
    	if (mimeType == null) {
    		return FileType.OTHER;
    	}
    	
        switch (mimeType.split("/")[0]) {
        case "text": 
        	result = FileType.TEXT;
        	break;
        case "image": 
        	result = FileType.IMAGE;
        	break;
        case "audio": 
        	result = FileType.AUDIO; 
        	break;
        case "video": 
        	result = FileType.VIDEO;
        	break;
        default: 
        	result = FileType.OTHER;
        }
        
        if (Files.isExecutable(path)) {
        	result = FileType.EXEC;
        } else if (mimeType.equals("application/pdf")) {
        	result = FileType.PDF;
        }
        
        return result;
    }
}
