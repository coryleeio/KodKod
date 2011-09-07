package view;

import controller.Controller;
import model.Model;
/**
 * Public interface for the view classes to implement.
 * @author Cory M. Lee
 *
 */
public interface View {
	Controller getController();
	public void setController(Controller aController);
	public Model getModel();
	public void setModel(Model aModel);
}