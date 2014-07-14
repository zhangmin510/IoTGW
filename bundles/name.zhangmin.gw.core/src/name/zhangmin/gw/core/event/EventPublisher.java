/**
 * 
 */
package name.zhangmin.gw.core.event;

import name.zhangmin.gw.core.type.Command;
import name.zhangmin.gw.core.type.Data;

/**
 * This interface is a service which is used to send commands or data
 * through the <i>IoTGW</i> event bus.
 * <p>
 * The event bus belongs to the <i>IotGW</i> core and is used as major
 * intercommunication mechanism between the different modules.
 * <p>
 * An event is always send under a specific topic starting with the 
 * prefix {@link EventConstants#TOPIC_PREFIX} followed by a sub-topic.
 * A listener can subscribe to a topic and receive the information 
 * subscribed to.The simiplest way of receiving events is to extends 
 * the {@link AbstractEventSubscriber} which already subscribes to the 
 * according <i>IoTGW</i> topics and which already implements the 
 * {@link EventSubscriber} listener interface.
 * 
 * @see EventSubscriber
 * @see AbstractEventSubscriber
 * 
 * @author ZhangMin.name
 *
 */
public interface EventPublisher {
	/**
	 * Sends a command under a specific app name through the event bus in
	 * a synchronous way.
	 * 
	 * @param appName name of the app to send command for
	 * 		(must neither be null, nor empty and must follow general app name
	 * 		specification)
	 * @param command the command to send(must not be null)
	 * @throws IllegalArgumentException if the app name is null or empty or does
	 * 		not follow the general app specification, or the command is null
	 * @throws IllegalStateException if the underlying event bus module is not
	 * 		available
	 * 
	 * @see #postCommand(String, Command)
	 */
	void sendCommand(String appName, Command command)
		throws IllegalArgumentException, IllegalStateException;
	/**
	 * Sends a command under a specific app name through the event bus in
	 * a synchronous way.
	 * 
	 * @param appName name of the app to send command for
	 * 		(must neither be null, nor empty and must follow general app name
	 * 		specification)
	 * @param command the command to send(must not be null)
	 * @param source a string identifying the sender. This should usually be
	 * 		the bundle symbolic name
	 * @throws IllegalArgumentException if the app name is null or empty or does
	 * 		not follow the general app specification, or the command is null
	 * @throws IllegalStateException if the underlying event bus module is not
	 * 		available
	 * 
	 * @see #postCommand(String, Command, String)
	 */
	void sendCommand(String appName, Command command, String source)
			throws IllegalArgumentException, IllegalStateException;
	/**
	 * Posts a command under a specific app name through the event bus in
	 * a asynchronous way.
	 * 
	 * @param appName name of the app to send command for
	 * 		(must neither be null, nor empty and must follow general app name
	 * 		specification)
	 * @param command the command to send(must not be null)
	 * @throws IllegalArgumentException if the app name is null or empty or does
	 * 		not follow the general app specification, or the command is null
	 * @throws IllegalStateException if the underlying event bus module is not
	 * 		available
	 * 
	 * @see #sendCommand(String, Command, String)
	 */
	void postCommand(String appName, Command command)
			throws IllegalArgumentException, IllegalStateException;
	/**
	 * Posts a command under a specific app name through the event bus in
	 * a asynchronous way.
	 * 
	 * @param appName name of the app to send command for
	 * 		(must neither be null, nor empty and must follow general app name
	 * 		specification)
	 * @param command the command to send(must not be null)
	 * @param source a string identifying the sender. This should usually be
	 * 		the bundle symbolic name
	 * @throws IllegalArgumentException if the app name is null or empty or does
	 * 		not follow the general app specification, or the command is null
	 * @throws IllegalStateException if the underlying event bus module is not
	 * 		available
	 * 
	 * @see #sendCommand(String, Command)
	 */
	void postCommand(String appName, Command command, String source)
			throws IllegalArgumentException, IllegalStateException;
	/**
	 * Sends data under a specific app name through the event bus in
	 * a asynchronous way.
	 * 
	 * @param appName name of the app to send command for
	 * 		(must neither be null, nor empty and must follow general app name
	 * 		specification)
	 * @param command the command to send(must not be null)
	 * @throws IllegalArgumentException if the app name is null or empty or does
	 * 		not follow the general app specification, or the command is null
	 * @throws IllegalStateException if the underlying event bus module is not
	 * 		available
	 * 
	 */
	void postData(String appName, Data data)
			throws IllegalArgumentException, IllegalStateException;
	/**
	 * Posts data under a specific app name through the event bus in
	 * a asynchronous way.
	 * 
	 * @param appName name of the app to send command for
	 * 		(must neither be null, nor empty and must follow general app name
	 * 		specification)
	 * @param command the command to send(must not be null)
	 * @param source a string identifying the sender. This should usually be
	 * 		the bundle symbolic name
	 * @throws IllegalArgumentException if the app name is null or empty or does
	 * 		not follow the general app specification, or the command is null
	 * @throws IllegalStateException if the underlying event bus module is not
	 * 		available
	 * 
	 */
	void postData(String appName, Data data, String source)
			throws IllegalArgumentException, IllegalStateException;
}
