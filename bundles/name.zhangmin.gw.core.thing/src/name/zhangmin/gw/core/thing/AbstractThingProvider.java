/**
 * 
 */
package name.zhangmin.gw.core.thing;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ZhangMin.name
 *
 */
public abstract class AbstractThingProvider implements ThingProvider {

	private List<ThingsChangeListener> thingsChangeListeners  = 
			new CopyOnWriteArrayList();

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.ThingProvider#addThingsChangeListener(name.zhangmin.gw.core.thing.ThingsChangeListener)
	 */
	@Override
	public void addThingsChangeListener(ThingsChangeListener listener) {
		this.thingsChangeListeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.ThingProvider#removeThingsChangeListener(name.zhangmin.gw.core.thing.ThingsChangeListener)
	 */
	@Override
	public void removeThingsChangeListener(ThingsChangeListener listener) {
		this.thingsChangeListeners.remove(listener);
	}
	
	protected void notifyThingsChangeListenersAboutAddedThing(Thing thing) {
		for (ThingsChangeListener thingChangeListener : this.thingsChangeListeners)
			thingChangeListener.thingAdded(this, thing);
	}
	protected void notifyThingsChangeListenersAboutRemovedThing(Thing thing) {
		for (ThingsChangeListener thingChangeListener : this.thingsChangeListeners)
			thingChangeListener.thingRemoved(this, thing);
	}	
	
	protected void notifyThingsChangeListenersAboutUpdatedThing(Thing oldThing, Thing newThing) {
		for (ThingsChangeListener thingChangeListener : this.thingsChangeListeners)
			thingChangeListener.thingUpdated(this, oldThing, newThing);
	}
}
