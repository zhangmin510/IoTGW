/**
 * 
 */
package name.zhangmin.gw.core.thing.binding;

import name.zhangmin.gw.config.Configuration;
import name.zhangmin.gw.core.thing.Thing;
import name.zhangmin.gw.core.thing.ThingTypeUID;
import name.zhangmin.gw.core.thing.ThingUID;

/**
 * This class is responsiable for creating {@link Thing}s and
 * registering {@link ThingHandler}s. Therefore it must be registed as
 * OSGi service.
 * 
 * @author ZhangMin.name
 *
 */
public interface ThingHandlerFactory {
	/**
	 * Returns whether the handler is able to create a thing or register
	 * a thing hander for the given type.
	 * 
	 * @param thingTypeUID thing type
	 * @return true, if the handler supports the thing type, false otherwise
	 * 
	 */
	boolean supportsThingType(ThingTypeUID thingTypeUID);
	/**
	 * This method is called, if the {@link ThingHandlerFactory} supports the
	 * type of the given thing. A {@link ThingHandler} must be registerd as
	 * OSGi service for the given thing.
	 * 
	 * @param thing thing for which a new handler must be registered.
	 */
	void registerHandler(Thing thing);
	/**
	 * This method is called, when a {@link ThingHandler} must be ungistered.
	 * @param thing thing, which handler must be unregistered
	 */
	void unregisterHandler(Thing thing);
	/**
	 * Creates a thing for given arguments.
	 * 
	 * @param thingTypeUID the thing type uid
	 * @param configuration configuration
	 * @param thingUID thing uid, which can be null
	 * @return
	 */
	Thing createThing(ThingTypeUID thingTypeUID, Configuration configuration, ThingUID thingUID);
	/**
	 * A thing with the given {@link Thing} UID was removed.
	 * @param thingUID thing UID of the removed object.
	 */
	void removeThing(ThingUID thingUID);
	
	
}
