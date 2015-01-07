/**
 * 
 */
package name.zhangmin.gw.core.thing;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import name.zhangmin.gw.core.thing.ThingTracker.ThingTrackerEvent;
import name.zhangmin.gw.core.thing.uid.ThingUID;

/**
 * @author ZhangMin.name
 *
 */
public class ThingRegistryImpl implements ThingRegistry, ThingsChangeListener {

	private Logger logger = LoggerFactory.getLogger(ThingRegistryImpl.class.getName());
	private Collection<ThingRegistryChangeListener> thingListeners = new CopyOnWriteArraySet<>();
	private Map<ThingProvider, Collection<Thing>> thingMap = new ConcurrentHashMap<>();
	private List<ThingTracker> thingTrackers = new CopyOnWriteArrayList<>();
	
	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.ThingRegistry#addThingRegistryChangeListener(name.zhangmin.gw.core.thing.ThingRegistryChangeListener)
	 */
	@Override
	public void addThingRegistryChangeListener(
			ThingRegistryChangeListener listener) {
		this.thingListeners.add(listener);

	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.ThingRegistry#getByUID(name.zhangmin.gw.core.thing.ThingUID)
	 */
	@Override
	public Thing getByUID(ThingUID uid) {
		for (Thing thing : getThings()) {
			if (thing.getUID().equals(uid))
				return thing;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.ThingRegistry#getThings()
	 */
	@Override
	public List<Thing> getThings() {
		return Lists.newArrayList(Iterables.concat(thingMap.values()));
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.ThingRegistry#removeThingRegistryChangeListener(name.zhangmin.gw.core.thing.ThingRegistryChangeListener)
	 */
	@Override
	public void removeThingRegistryChangeListener(
			ThingRegistryChangeListener listener) {
		this.thingListeners.remove(listener);

	}

	@Override
	public void thingAdded(ThingProvider provider, Thing thing) {
		Collection<Thing> things = thingMap.get(provider);
		if (things != null) {
			things.add(thing);
            notifyListenersAboutAddedThing(thing);
		}
		
	}

	@Override
	public void thingRemoved(ThingProvider provider, Thing thing) {
		Collection<Thing> things = thingMap.get(provider);
		if(things != null) {
			things.remove(thing);
            notifyListenersAboutRemovedThing(thing);
		}
		
	}

	@Override
	public void thingUpdated(ThingProvider provider, Thing oldThing,
			Thing newThing) {
		thingRemoved(provider, oldThing);
		thingAdded(provider, newThing);
		
	}
	private void notifyListenerAboutAllThingsAdded(ThingTracker thingTracker) {
        for (Thing thing : getThings()) {
			thingTracker.thingAdded(thing, ThingTrackerEvent.TRACKER_ADDED);
		}
	}
	
    private void notifyListenerAboutAllThingsRemoved(ThingTracker thingTracker) {
        for (Thing thing : getThings()) {
			thingTracker.thingRemoved(thing, ThingTrackerEvent.TRACKER_REMOVED);
		}
	}
	
    private void notifyListenersAboutAddedThing(Thing thing) {
		for (ThingTracker thingTracker : thingTrackers) {
			thingTracker.thingAdded(thing, ThingTrackerEvent.THING_ADDED);
		}
		for (ThingRegistryChangeListener listener : thingListeners) {
			listener.thingAdded(thing);
		}
	}
    
    private void notifyListenersAboutRemovedThing(Thing thing) {
		for (ThingTracker thingTracker : thingTrackers) {
			thingTracker.thingRemoved(thing, ThingTrackerEvent.THING_REMOVED);
		}
		for (ThingRegistryChangeListener listener : thingListeners) {
			listener.thingRemoved(thing);
		}
	}

    public void addThingProvider(ThingProvider thingProvider) {
		// only add this provider if it does not already exist
		if(!thingMap.containsKey(thingProvider)) {
			Collection<Thing> things = new CopyOnWriteArraySet<Thing>(thingProvider.getThings());
			thingProvider.addThingsChangeListener(this);
			thingMap.put(thingProvider, things);
            logger.debug("Thing provider '{}' has been added.", thingProvider.getClass().getName());
		}
	}



    protected void deactivate() {
		for (ThingTracker thingTracker : thingTrackers) {
			removeThingTracker(thingTracker);
		}
	}

    public void removeThingProvider(ThingProvider thingProvider) {
		if(thingMap.containsKey(thingProvider)) {
			thingMap.remove(thingProvider);
			thingProvider.removeThingsChangeListener(this);
            logger.debug("Thing provider '{}' has been removed.", thingProvider.getClass()
                    .getName());
		}
	}
    /**
     * Removes a thing tracker.
     * 
     * @param thingTracker
     *            the thing tracker
     */
	public void removeThingTracker(ThingTracker thingTracker) {
		notifyListenerAboutAllThingsRemoved(thingTracker);
		thingTrackers.remove(thingTracker);
	}
    /**
     * Adds a thing tracker.
     * 
     * @param thingTracker
     *            the thing tracker
     */
	public void addThingTracker(ThingTracker thingTracker) {
		notifyListenerAboutAllThingsAdded(thingTracker);
		thingTrackers.add(thingTracker);
	}
}
