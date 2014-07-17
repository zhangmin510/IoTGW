/**
 * 
 */
package name.zhangmin.gw.core.thing.binding.builder;

import name.zhangmin.gw.core.thing.ThingTypeUID;
import name.zhangmin.gw.core.thing.ThingUID;
import name.zhangmin.gw.core.thing.impl.ThingImpl;



/**
 * 
 * @author ZhangMin.name
 *
 */
public class ThingBuilder extends GenericThingBuilder<ThingBuilder>{
    private ThingBuilder(ThingImpl thing) {
        super(thing);
    }

    public static ThingBuilder create(ThingTypeUID thingTypeUID, String thingId) {
        ThingImpl thing = new ThingImpl(thingTypeUID, thingId);
        return new ThingBuilder(thing);
    }
    
    public static ThingBuilder create(ThingUID thingUID) {
    	ThingImpl thing = new ThingImpl(thingUID);
    	return new ThingBuilder(thing);
    }
}
