package view;


import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileSelectorView {
	static JFileChooser chooser;
  public FileSelectorView() {
	     chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new File("./src/graphs"));
	    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
  }

  public static String choose() {


  String name = new String();
    int r = chooser.showOpenDialog(new JFrame());
    if (r == JFileChooser.APPROVE_OPTION) {
      name = chooser.getSelectedFile().getPath();
      
    }
    return name;
  }
}


       