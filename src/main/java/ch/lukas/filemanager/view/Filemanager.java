package ch.lukas.filemanager.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Filemanager extends JFrame {

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
