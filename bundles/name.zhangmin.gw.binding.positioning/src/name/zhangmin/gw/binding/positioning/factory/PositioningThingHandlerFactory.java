/**
 * 
 */
package name.zhangmin.gw.binding.positioning.factory;

import name.zhangmin.gw.binding.positioning.PositioningThingTypeProvider;
import name.zhangmin.gw.binding.positioning.handler.PositioningHandler;
import name.zhangmin.gw.core.thing.Thing;
import name.zhangmin.gw.core.thing.binding.BaseThingHandlerFactory;
import name.zhangmin.gw.core.thing.binding.ThingHandler;
import name.zhangmin.gw.core.thing.uid.ThingTypeUID;
import name.zhangmin.gw.core.thing.uid.ThingUID;
import name.zhangmin.gw.config.Configuration;

/**
 * @author ZhangMin.name
 *
 */
public class PositioningThingHandlerFactory extends BaseThingHandlerFactory{
	
	@Override
	protected ThingHandler createHandler(Thing thing) {
		if (thing.getThingTypeUID().equals(PositioningThingTypeProvider.POSITIONING_THING_TYPE.getUID())) {
			return new PositioningHandler(thing);
		}
		return null;
	}

	@Override
	public Thing createThing(ThingTypeUID thingTypeUID, Configuration configuration,
			ThingUID thingUID) {
		if (PositioningThingTypeProvider.POSITIONING_THING_TYPE.getUID().equals(thingTypeUID)) {
			Thing thing = super.createThing(thingTypeUID, thingUID, configuration);
			return thing;
		}
		throw new IllegalArgumentException("The thing type " + thingTypeUID
				+ " is not supported by the positioning binding.");
	}
	
	@Override
	public boolean supportsThingType(ThingTypeUID thingTypeUID) {
		return PositioningThingTypeProvider.POSITIONING_THING_TYPE.getUID().equals(thingTypeUID);
	}
	

}
