package view;

import javax.swing.JFrame;
import model.Model;
import model.AbstractModel;
import model.ModelListener;
import controller.Controller;

@SuppressWarnings("serial")
/**
 * abstract super class for views to inherit methods from.
 */
abstract public class JFrameView extends JFrame implements View, ModelListener {
	private Model model;
	private Controller controller;
	/**
	 * Constructor for JFrameView
	 * @param model - model to be set to view.
	 * @param controller controller to be set to view.
	 */
	public JFrameView (Model model, Controller controller){
		setModel(model);
		setController(controller);
	}
	/**
	 * registers the view with the model.
	 */
	public void registerWithModel(){
		((AbstractModel)model).addModelListener(this);
	}
	public void unregisterWithModel(){
		((AbstractModel)model).removeModelListener(this);
	}
	/**
	 * getter for the view's controller.
	 */
	public Controller getController(){return controller;}
	
	/**
	 * setter for the view's controller.
	 */
	public void setController(Controller controller){this.controller = controller;}
	/**
	 * getter for the view's model.
	 */
	public Model getModel(){return model;}
	
	/**
	 * setter for the view's model.
	 */
	public void setModel(Model model) {
		this.model = model;
		registerWithModel();
	}
	
}