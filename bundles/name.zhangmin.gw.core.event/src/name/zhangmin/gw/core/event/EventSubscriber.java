/**
 * 
 */
package name.zhangmin.gw.core.event;

import name.zhangmin.gw.core.lib.type.Command;
import name.zhangmin.gw.core.lib.type.State;



/**
 * This interface defines the callback interface for receiving
 * events from the <i>IoTGW</i> event bus.
 * <p>
 * Any events containing a command or data can be received by
 * this listener interface. Use the {@link AbstractEventSubscriber}
 * class as concrete implementation of this lintener interface.
 * <p>
 * For further information about sending events through the event
 * bus check {@link EventPublisher} service specification.
 * 
 * @see EventPublisher
 * @see AbstractEventSubscriber
 * 
 * @author ZhangMin.name
 *
 */
public interface EventSubscriber {
	/**
	 * Callback method if a command was sent on the event bus.
	 * <p>
	 * Any exceptions, which may occur in this callback method,
	 * are caught and logged.
	 * <p>
	 * Hint: Do not block the reception of this event for long-term
	 * tasks.
	 * For long-term tasks create an own thread.
	 * 
	 * @param appName the app for which a command was sent
	 * 		(not null, not empty, follows the app name specification)
	 * @param command the command that was sent (not null)
	 */
	void receiveCommand(String appName, Command command);
	/**
	 * Callback method if data was sent on the event bus.
	 * <p>
	 * Any exceptions, which may occur in this callback method,
	 * are caught and logged.
	 * <p>
	 * Hint: Do not block the reception of this event for long-term
	 * tasks.
	 * For long-term tasks create an own thread.
	 * 
	 * @param appName the app for which a command was sent
	 * 		(not null, not empty, follows the app name specification)
	 * @param data the data that was sent (not null)
	 */
	void receiveState(String appName, State state);
}