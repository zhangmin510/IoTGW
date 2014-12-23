/**
 * 
 */
package name.zhangmin.gw.core.event.impl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import name.zhangmin.gw.core.apps.AppUtil;
import name.zhangmin.gw.core.event.EventConstants;
import name.zhangmin.gw.core.event.EventPublisher;
import name.zhangmin.gw.core.type.Command;
import name.zhangmin.gw.core.type.Data;
import name.zhangmin.gw.core.type.EventType;

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
		AppUtil.assertValidAppName(appName);
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
		AppUtil.assertValidAppName(appName);
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
	public void postData(String appName, Data data)
			throws IllegalArgumentException, IllegalStateException {
		postData(appName, data, null);
	}
	/**
	 * {@inheritDoc}
	 */
	public void postData(String appName, Data data, String source)
			throws IllegalArgumentException, IllegalStateException {
		AppUtil.assertValidAppName(appName);
		if (data == null) throw new IllegalArgumentException("The data must not be null");
		
		EventAdmin eventAdmin = this.eventAdmin;
		if (eventAdmin != null) {
			try {
				eventAdmin.postEvent(createDataEvent(appName, data, source));
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
	private Event createDataEvent(String appName, Data data, String source) {
		Dictionary<String, Object> props = new Hashtable<String, Object>(2);
		props.put("app", appName);
		props.put("data", data);
		if (source != null) props.put("source", source);
		return new Event(createTopic(EventType.STATE, appName), props);
	}

}
