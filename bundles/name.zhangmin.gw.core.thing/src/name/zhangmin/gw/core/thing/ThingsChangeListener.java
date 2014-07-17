/**
 * 
 */
package name.zhangmin.gw.core.thing;

/**
 * This is a listener interface which should be implemented wherever
 * thing providers or the thing registry are used in order to be notified
 * of any dynamic changes in the provided things.
 * 
 * @author ZhangMin.name
 *
 */
public interface ThingsChangeListener {
	/**
	 * Notifies the listener that a single thing has been added
	 * @param provider the concerned thing provider
	 * @param thing the thing has been added
	 */
	public void thingAdded(ThingProvider provider, Thing thing);
	/**
	 * Notifies the listener that a single thing has been removed.
	 * @param provider the concerned thing provider
	 * @param thing the thing has been removed
	 */
	public void thingRemoved(ThingProvider provider, Thing thing);
	/**
	 * Notifies the listener that a single thing has been updated
	 * @param provider the concerned thing provider
	 * @param oldThing the thing before update
	 * @param newThing the thing after update
	 */
	public void thingUpdated(ThingProvider provider, Thing oldThing, Thing newThing);
	
	
}
