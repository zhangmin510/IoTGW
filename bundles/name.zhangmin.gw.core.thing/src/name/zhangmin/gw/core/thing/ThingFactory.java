/**
 * 
 */
package name.zhangmin.gw.core.thing;

import java.util.List;

import com.google.common.collect.Lists;

import name.zhangmin.gw.config.Configuration;
import name.zhangmin.gw.core.thing.binding.builder.ChannelBuilder;
import name.zhangmin.gw.core.thing.binding.builder.GenericThingBuilder;
import name.zhangmin.gw.core.thing.binding.builder.ThingBuilder;
import name.zhangmin.gw.core.thing.type.ChannelDefinition;
import name.zhangmin.gw.core.thing.type.ChannelType;
import name.zhangmin.gw.core.thing.type.ThingType;
import name.zhangmin.gw.core.thing.uid.ChannelUID;
import name.zhangmin.gw.core.thing.uid.ThingUID;

/**
 * This class helps to create thing based on a given {@link ThingType}
 * 
 * @author ZhangMin.name
 *
 */
public class ThingFactory {
	 /**
     * 
     * Creates a thing based on given thing type.
     * 
     * @param thingType
     *            thing type (should not be null)
     * @param thingUID
     *            thindUID (should not be null)
     * @param configuration
     *            (should not be null)
     * @return thing
     */
	public static Thing createThing(ThingType thingType, 
			ThingUID thingUID, Configuration configuration) {
		if (thingType == null) 
			throw new IllegalArgumentException("The thingType should not be null");
		if (thingUID == null)
			throw new IllegalArgumentException("The thingUID should not be null");
		List<Channel> channels = createChannels(thingType, thingUID);
		return createThingBuilder(thingType, thingUID).withConfiguration(configuration)
				.withChannels(channels).build();
	}
   
    private static List<Channel> createChannels(ThingType thingType, ThingUID thingUID) {
        List<Channel> channels = Lists.newArrayList();
        List<ChannelDefinition> channelDefinitions = thingType.getChannelDefinitions();
        for (ChannelDefinition channelDefinition : channelDefinitions) {
            channels.add(createChannel(channelDefinition, thingUID));
        }
        return channels;
    }
    private static GenericThingBuilder<?> createThingBuilder(ThingType thingType, ThingUID thingUID) { 
        return ThingBuilder.create(thingType.getUID(), thingUID.getId());
    }
    private static Channel createChannel(ChannelDefinition channelDefinition, ThingUID thingUID) {
        ChannelType type = channelDefinition.getType();
        Channel channel = ChannelBuilder.create(
                new ChannelUID(thingUID, channelDefinition.getId()), type.getAppType()).build();
        return channel;
    }
   
}
