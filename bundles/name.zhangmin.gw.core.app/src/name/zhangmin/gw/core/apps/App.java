/**
 * 
 */
package name.zhangmin.gw.core.apps;

import java.util.List;

import name.zhangmin.gw.core.lib.type.Command;
import name.zhangmin.gw.core.lib.type.State;


/**
 * This class defines the core features of an IoTGW app.
 * <p>
 * App instances are used for all stateful services and are especially
 * important for the {@link AppRegistry}
 * 
 * @author ZhangMin.name
 *
 */
public interface App {
	/**
	 * Returns the current state of the app.
	 * @return the current state
	 */
	public State getState();
	
	/**
	 * Returns the current state of the app as specific type
	 * @param typeClass 
	 * @return the current sate in the requested type or
	 * null, if state cannot be provided as the requested type
	 */
	public State getStateAs(Class<? extends State> typeClass);

	/**
	 * Returns the name of the app
	 * @return the name of the app
	 */
	public String getName();
	
	/**
	 * Returns the type of the app
	 * @return the type of the app
	 */
	public String getType();
	
	/**
	 * This method provides a list of all state type that can be used to 
	 * update the app state
	 * 
	 * @return a list of state types that can be used to update the app state
	 */
	public List<Class<? extends State>> getAcceptedStateTypes();
	
	/**
	 * This method provides a list of all command type that can be used for
	 * this app.
	 * @return a list of all command types that can be used for this item
	 */
	public List<Class<? extends Command>> getAcceptedCommandTypes();
	
}
