/**
 * 
 */
package name.zhangmin.gw.core.event.impl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import name.zhangmin.gw.core.event.EventConstants;
import name.zhangmin.gw.core.event.EventPublisher;
import name.zhangmin.gw.core.lib.type.Command;
import name.zhangmin.gw.core.lib.type.EventType;
import name.zhangmin.gw.core.lib.type.State;


/**
 * This class is the main implementation of the {@link EventPublisher}
 * service interface.
 * <p>
 * This implementation uses the <i>OSGi Event Admin</i> service as 
 * event bus implementation to broadcast <i>IoTGW</i> events.
 *  
 * @author ZhangMin.name
 *
 */
public class EventPublisherImpl implements EventPublisher{
	private EventAdmin eventAdmin;
	
	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}
	
	public void unsetEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = null;
	}
	
    /**
     * {@inheritDoc}
     */
	public void sendCommand(String appName, Command command)
			throws IllegalArgumentException, IllegalStateException {
		sendCommand(appName, command, null);	
	}
	/**
	 * {@inheritDoc}
	 */
	public void sendCommand(String appName, Command command, String source)
			throws IllegalArgumentException, IllegalStateException {
		//AppUtil.assertValidAppName(appName);
		if (command == null) throw new IllegalArgumentException("The command must not be null");
		
		EventAdmin eventAdmin = this.eventAdmin;
		if (eventAdmin != null) {
			try {
				eventAdmin.sendEvent(createCommandEvent(appName, command, source));
			} catch (Exception e) {
				throw new IllegalStateException("Cannot send the command!", e);
			}
		} else {
			throw new IllegalStateException("The event bus module is not avaiable!");
		}
		
	}
	/**
	 * {@inheritDoc}
	 */
	public void postCommand(String appName, Command command)
			throws IllegalArgumentException, IllegalStateException {
		postCommand(appName, command, null);
	}
	/**
	 * {@inheritDoc}
	 */
	public void postCommand(String appName, Command command, String source)
			throws IllegalArgumentException, IllegalStateException {
		//AppUtil.assertValidAppName(appName);
		if (command == null) throw new IllegalArgumentException("The command must not be null");
		
		EventAdmin eventAdmin = this.eventAdmin;
		if (eventAdmin != null) {
			try {
				eventAdmin.postEvent(createCommandEvent(appName, command, source));
			} catch (Exception e) {
				throw new IllegalStateException("Cannot send the command!", e);
			}
		} else {
			throw new IllegalStateException("The event bus module is not avaiable!");
		}
		
	}
	/**
	 * {@inheritDoc}
	 */
	public void postState(String appName, State state)
			throws IllegalArgumentException, IllegalStateException {
		postState(appName, state, null);
	}
	/**
	 * {@inheritDoc}
	 */
	public void postState(String appName, State state, String source)
			throws IllegalArgumentException, IllegalStateException {
		//AppUtil.assertValidAppName(appName);
		if (state == null) throw new IllegalArgumentException("The data must not be null");
		
		EventAdmin eventAdmin = this.eventAdmin;
		if (eventAdmin != null) {
			try {
				eventAdmin.postEvent(createStateEvent(appName, state, source));
			} catch (Exception e) {
				throw new IllegalStateException("Cannot send the data!", e);
			}
		} else {
			throw new IllegalStateException("The event bus module is not avaiable!");
		}
		
	}
	
	private String createTopic(EventType type, String appName) {
		return EventConstants.TOPIC_PREFIX + EventConstants.TOPIC_SEPERATOR + 
				type + EventConstants.TOPIC_SEPERATOR + appName;
	}
	private Event createCommandEvent(String appName, Command command, String source) {
		Dictionary<String, Object> props = new Hashtable<String, Object>(2);
		props.put("app", appName);
		props.put("command", command);
		if (source != null) props.put("source", source);
		return new Event(createTopic(EventType.COMMAND, appName), props);
	}
	private Event createStateEvent(String appName, State state, String source) {
		Dictionary<String, Object> props = new Hashtable<String, Object>(2);
		props.put("app", appName);
		props.put("state", state);
		if (source != null) props.put("source", source);
		return new Event(createTopic(EventType.STATE, appName), props);
	}

}
