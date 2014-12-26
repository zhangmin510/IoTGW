package name.zhangmin.gw.core.thing;

import java.util.List;

import name.zhangmin.gw.config.Configuration;
import name.zhangmin.gw.core.lib.type.State;
import name.zhangmin.gw.core.thing.binding.ThingHandler;


/**
 * This class is a representation of a connected part (e.g. physical
 * device or clound service) from the real world. It contains a list
 * of {@link Channel}s, which can be bound to {@link App}s.
 * @author ZhangMin.name
 *
 */
public interface Thing {
	List<Channel> getChannels();
	
	ThingStatus getStatus();
	void setStatus(ThingStatus status);
	
	void setHandler(ThingHandler thingHandler);
	ThingHandler getHandler();
	
	void channelUpdated(ChannelUID channelUID, State state);
	
	Configuration getConfiguration();
	
	void setName(String name);
	String getName();
	
	ThingUID getUID();
	ThingTypeUID getThingTypeUID();
}
