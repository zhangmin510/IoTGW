/**
 * 
 */
package name.zhangmin.gw.core.thing.binding.builder;

import name.zhangmin.gw.core.thing.ThingImpl;
import name.zhangmin.gw.core.thing.uid.ThingTypeUID;
import name.zhangmin.gw.core.thing.uid.ThingUID;



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
