package controller;

import view.View;
import model.Model;
/**
 * Public interface for controller classes to implement.
 * @author Cory M. Lee
 *
 */
public interface Controller {
	void setModel(Model model);
	Model getModel();
	View getView();
	void setView(View view);
}