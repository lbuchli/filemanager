package ch.lukas.filemanager.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * The filemanager window, consisting of navigation, pinned folders and the current folder children / search results
 * @author lukas
 */
public class Filemanager extends JFrame {

	private static final long serialVersionUID = 8649669646451354890L;
	
	private Pinned pinned;
    private FileList fileList;
    private Navigation navigation;

    public Filemanager() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                buildGUI();
            }
        });
    }
    
    private void buildGUI() {
    	 pinned = new Pinned();
         fileList = new FileList();
         navigation = new Navigation();
         
         getContentPane().add(navigation, BorderLayout.NORTH);
         getContentPane().add(pinned, BorderLayout.WEST);
         getContentPane().add(fileList, BorderLayout.CENTER);
         
         setVisible(true);
         setDefaultCloseOperation(DISPOSE_ON_CLOSE);
         pack();
    }
}
