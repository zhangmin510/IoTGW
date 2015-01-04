/**
 * 
 */
package name.zhangmin.gw.core.thing.type;

import name.zhangmin.gw.core.thing.Thing;

/**
 * @author ZhangMin.name
 *
 */
public interface ThingTypeChangeListener {
    /**
     * Notifies the listener that a single thing type has been added
     * 
     * @param provider
     *            the concerned thing type provider
     * @param thing
     *            the thing type that has been added
     */
    public void thingTypeAdded(ThingTypeProvider provider, ThingType thingType);

    /**
     * Notifies the listener that a single thing type has been removed
     * 
     * @param provider
     *            the concerned thing type provider
     * @param thing
     *            the thing type that has been removed
     */
    public void thingTypeRemoved(ThingTypeProvider provider, Thing thingType);
}
