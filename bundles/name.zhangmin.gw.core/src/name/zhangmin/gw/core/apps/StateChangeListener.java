package name.zhangmin.gw.core.apps;

import name.zhangmin.gw.core.type.State;



/**
 * <p>This interface must be implemented by all classes that want to be notified
 * about changes in the state of an app.</p>
 * <p>The {@link GenericApp} class provides the possibility to register such
 * listeners.</p>
 * 
 *
 */
public interface StateChangeListener {
	
	/**
	 * This method is called, if a state has changed.
	 * 
	 * @param app the app whose state has changed
	 * @param oldState the previous state
	 * @param newState the new state
	 */
	public void stateChanged(App app, State oldState, State newState);

	/**
	 * This method is called, if a state was updated, but has not changed
	 * 
	 * @param app the app whose state was updated
	 * @param state the current state, same before and after the update
	 */
	public void stateUpdated(App app, State state);

}
