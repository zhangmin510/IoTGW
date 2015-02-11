/**
 * 
 */
package name.zhangmin.gw.binding.positioning;

import java.util.Collection;

import com.google.common.collect.Lists;

import name.zhangmin.gw.core.thing.type.ChannelDefinition;
import name.zhangmin.gw.core.thing.type.ChannelType;
import name.zhangmin.gw.core.thing.type.ThingType;
import name.zhangmin.gw.core.thing.type.ThingTypeChangeListener;
import name.zhangmin.gw.core.thing.type.ThingTypeProvider;
import name.zhangmin.gw.core.thing.uid.ChannelTypeUID;
import name.zhangmin.gw.core.thing.uid.ThingTypeUID;

/**
 * @author ZhangMin.name
 *
 */
public class PositioningThingTypeProvider implements ThingTypeProvider{

	private static final String MANUFACTURER = "Ruifan";
	
	public final static ChannelType UDP_CHANNEL_TYPE = 
			new ChannelType(new ChannelTypeUID("positioning:udp"), 
					"RFID Positioning", "RFID Positioning Data", "Udp Channel", "?");
	public final static ChannelDefinition UDP_CHANNEL_DEFINITION = 
			new ChannelDefinition("udp", UDP_CHANNEL_TYPE);
	
	public final static ChannelDefinition UDP_CHANNEL_DEFINITION1 = 
			new ChannelDefinition("tcp", UDP_CHANNEL_TYPE);
	public final static ThingType POSITIONING_THING_TYPE = 
			new ThingType(new ThingTypeUID(PositioningBindingInfo.BINDING_ID, "udp"),
					"positioning", "positioning data",MANUFACTURER,
					Lists.newArrayList(UDP_CHANNEL_DEFINITION, UDP_CHANNEL_DEFINITION1), "?");
	
	@Override
	public Collection<ThingType> getThingTypes() {
		return Lists.newArrayList(POSITIONING_THING_TYPE);
	}

	@Override
	public void addThingTypeChangeListener(ThingTypeChangeListener listener) {
		
	}

	@Override
	public void removeThingTypeChangeListener(ThingTypeChangeListener listener) {
		
		
	}

}
