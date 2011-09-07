package model;

import java.awt.event.ActionEvent;

	@SuppressWarnings("serial")
	/**
	 * class which described events to be passed between modules.
	 */
	public class ModelEvent extends ActionEvent {
		/**
		 * Constructor for ModelEvent.
		 * @param obj - object being reffered to
		 * @param id - message id
		 * @param message - message
		 */
		public ModelEvent(Object obj, int id, String message){
			super(obj, id, message);
		}
		/**
		 * function that returns amount contained in event
		 * @return - returns amount.
		 */
}