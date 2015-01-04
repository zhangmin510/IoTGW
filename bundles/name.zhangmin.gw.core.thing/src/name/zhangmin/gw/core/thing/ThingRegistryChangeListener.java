package name.zhangmin.gw.core.thing;

/**
 * {@link ThingRegistryChangeListener} can be implemented to listen for things
 * being added or removed. The listener must be added and removed via
 * {@link ThingRegistry#addThingRegistryChangeListener(ThingRegistryChangeListener)}
 * and
 * {@link ThingRegistry#removeThingRegistryChangeListener(ThingRegistryChangeListener)}
 * .
 */
public interface ThingRegistryChangeListener {

	/**
	 * Notifies the listener that a single thing has been added
	 * 
	 * @param thing the thing that has been added
	 */
	public void thingAdded(Thing thing);
	
	/**
	 * Notifies the listener that a single thing has been removed
	 * 
	 * @param thing the thing that has been removed
	 */
	public void thingRemoved(Thing thing);
}
