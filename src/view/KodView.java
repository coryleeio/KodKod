package view;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JButton;

import controller.KodController;

import model.KodModel;
import model.ModelEvent;

public class KodView extends JFrameView {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel PathDescriptorLabel = null;
	private JTextField PathBar = null;
	private JLabel FileNameLabel = null;
	private JTextField FileTextField = null;
	private JButton FindPathButton = null;
	private JButton FileSelectorButton = null;
	/**
	 * This method initializes PathBar	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getPathBar() {
		if (PathBar == null) {
			PathBar = new JTextField();
			PathBar.setBounds(new Rectangle(165, 60, 496, 31));
		}
		return PathBar;
	}

	/**
	 * This method initializes FileTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getFileTextField() {
		if (FileTextField == null) {
			FileTextField = new JTextField();
			FileTextField.setBounds(new Rectangle(165, 15, 496, 31));
			FileTextField.setText("//");
			FileTextField.setEditable(false);
		}
		return FileTextField;
	}

	/**
	 * This method initializes FindPathButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getFindPathButton() {
		if (FindPathButton == null) {
			FindPathButton = new JButton();
			FindPathButton.setBounds(new Rectangle(165, 105, 121, 16));
			FindPathButton.setToolTipText("Press this button to find the pathstring for the selected file...");
			FindPathButton.setText("Find Paths");
			FindPathButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					((KodController)getController()).FindPathsFromFile(((KodController)getController()).getCurFile());
				}
			});
		}
		return FindPathButton;
	}

	/**
	 * This method initializes FileSelectorButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getFileSelectorButton() {
		if (FileSelectorButton == null) {
			FileSelectorButton = new JButton();
			FileSelectorButton.setBounds(new Rectangle(675, 15, 31, 31));
			FileSelectorButton.addActionListener(new java.awt.event.ActionListener() {   
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					FileSelectorView fc = new FileSelectorView();
					String temp = fc.choose();
					((KodController)getController()).setFile(temp);
					FileTextField.setText(temp);
				}
			
			});
		}
		return FileSelectorButton;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new KodController();
	}

	/**
	 * This is the default constructor
	 * @param kodController 
	 * @param kodModel 
	 */
	public KodView(KodModel kodModel, KodController kodController) {
		super(kodModel, kodController);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(765, 165);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			FileNameLabel = new JLabel();
			FileNameLabel.setBounds(new Rectangle(90, 15, 69, 31));
			FileNameLabel.setText("FileName = ");
			PathDescriptorLabel = new JLabel();
			PathDescriptorLabel.setBounds(new Rectangle(90, 60, 69, 31));
			PathDescriptorLabel.setText("Path =");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(PathDescriptorLabel, null);
			jContentPane.add(getPathBar(), null);
			jContentPane.add(FileNameLabel, null);
			jContentPane.add(getFileTextField(), null);
			jContentPane.add(getFindPathButton(), null);
			jContentPane.add(getFileSelectorButton(), null);
		}
		return jContentPane;
	}

	@Override
	public void modelChanged(ModelEvent event) {
		PathBar.setText(((KodController)getController()).getCurPath());
		
		
	}

}  //  @jve:decl-index=0:visual-constraint="107,43"
