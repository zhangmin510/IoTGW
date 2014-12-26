/**
 * 
 */
package name.zhangmin.gw.core.event.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.zhangmin.gw.core.event.EventConstants;
import name.zhangmin.gw.core.event.EventSubscriber;
import name.zhangmin.gw.core.lib.type.Command;
import name.zhangmin.gw.core.lib.type.EventType;
import name.zhangmin.gw.core.lib.type.State;

/**
 * This class is an abstract implementation of the {@link EventSubscriber}
 * event listener interface which belongs to the <i>IoTGW</i> event bus.
 * <p>
 * This class is abstracted and <i>must</i> be extended. It helps to easily
 * extract the correct objects out of an incoming event received by the 
 * event bus, and forwards incoming data and command events to the according
 * callback methods of the {@link EventSubscriber} interface.
 * <p>
 * To get notified about events, the concrete implementation of this class must
 * be regisiterd as event listener with an according filter at the <i>IoTGW</i>
 * event bus.
 * Furthermore the method {@link #receiveData(String, Data)} and/or
 * {@link #receiveCommand(String, Command)} must be overriden.
 * 
 * @see EventPublisher
 * @see EventSubscriber
 * 
 * @author ZhangMin.name
 *
 */
public abstract class AbstractEventSubscriber implements EventSubscriber, EventHandler{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private List<String> sourceFilterList = new CopyOnWriteArrayList<String>();
	
	protected List<String> getSourceFilterList() {
		return sourceFilterList;
	}
	
	public AbstractEventSubscriber() {
		
	}
	/**
	 * {@inheritDoc}
	 */
	public void handleEvent(Event event) {
		Object appNameObj = event.getProperty("app");
		// received an invalid app name
		if (!(appNameObj instanceof String)) return; 
		String appName = (String) appNameObj;
		// received an empty app name
		if (appName.isEmpty()) return; 
		Object sourceObj = event.getProperty("source");
		if (sourceObj instanceof String) {
			String source = (String) sourceObj;
			// received an not supposed to process this event.
			if (sourceFilterList.contains(source)) return; 
		}
		
		String topic = event.getTopic();
		String[] topicParts = topic.split(EventConstants.TOPIC_SEPERATOR);
		// received an event with an invalid topic
		if ((topicParts.length <= 2) || !EventConstants.TOPIC_PREFIX.equals(topicParts[0])) return;
		String operation = topicParts[1];
		if (EventType.STATE.toString().equals(operation)) {
			Object newDataObj = event.getProperty("data");
			if (newDataObj instanceof State) {
				State data = (State) newDataObj;
				try {
					receiveState(appName, data);
				} catch(Exception e) {
					this.logger.error("An error occured within the 'receiveData' method"
							+ " of the event subscriber!", e);
				}
			}
		} else if (EventType.COMMAND.toString().equals(operation)) {
			Object commandObj = event.getProperty("command");
			if (commandObj instanceof Command) {
				Command command = (Command) commandObj;
				try {
					receiveCommand(appName, command);
				} catch(Exception e) {
					this.logger.error("An error occured within the 'receiveCommand' method"
							+ " of the event subscriber!", e);
				}
			}
		}
		
	}
	/**
	 * {@inheritDoc}
	 */
	public void receiveCommand(String appName, Command command) {
		
	}
	/**
	 * {@inheritDoc}
	 */
	public void receiveData(String appName, State state) {
		
	}
}
