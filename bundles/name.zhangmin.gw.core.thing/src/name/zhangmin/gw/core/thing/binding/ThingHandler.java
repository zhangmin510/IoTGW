/**
 * 
 */
package name.zhangmin.gw.core.thing.binding;

import name.zhangmin.gw.core.lib.type.Command;
import name.zhangmin.gw.core.lib.type.State;
import name.zhangmin.gw.core.thing.Thing;
import name.zhangmin.gw.core.thing.uid.ChannelUID;


/**
 * This class can 'handle' a {@link Thing}. It must be registered as
 * OSGi service with the id of the corresponding {@link Thing} as 
 * service property 'thing.id' and its type with the property 'thing.type'.
 * When a {@link Command} is sent to an {@link App} and the app is bound to
 * a channel, the handler of the corresponding thing will receive the command
 * via the {@link ThingHandler#handleCommand(Channel, Command)} method.
 * 
 * @author ZhangMin.name
 *
 */
public interface ThingHandler {
	public static final String SERVICE_PROPERTY_THING_ID = "thing.id";
	public static final String SERVICE_PROPERTY_THING_TYPE = "thing.type";
	
	Thing getThing();
	
	/**
	 * Handles a command for a given channel
	 * @param channelUID unique identifier of the channel to which the command
	 * 	was sent
	 * @param command {@link Command}
	 */
	void handleCommand(ChannelUID channelUID, Command command);
	/**
	 * Handles a command for a given channel.
	 * @param channelUID unique indentifier of the channel on which the data was
	 * 	performed
	 * @param state new state
	 */
	void handleState(ChannelUID channelUID, State state);
	/**
	 * This method is called, before the handler is shut down.
	 * An implementing class can clean resources here.
	 */
	void dispose();
	/**
	 * This method is called, when the handler is started.
	 */
	void initialize();
	
}
