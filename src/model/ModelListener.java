package model;
/**
 * Public interface for model listeners to implement.
 * @author Cory M. Lee
 *
 */
public interface ModelListener {
	public void modelChanged(ModelEvent event);
}