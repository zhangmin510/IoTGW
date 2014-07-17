/**
 * 
 */
package name.zhangmin.gw.core.thing.impl;

import name.zhangmin.gw.core.thing.Thing;


/**
 * This class can be used to track added and removed thigns. In contrast to
 * the {@link ThingRegistryChangeListener} the method 
 * {@link ThingTracker#thingAdded(Thing, ThingTrackerEvent) is called for every
 * thing, although it was added before the tracker was register.
 * 
 * @author ZhangMin.name
 *
 */
public interface ThingTracker {
    public enum ThingTrackerEvent {
        THING_ADDED, THING_REMOVED, THING_UPDATED, TRACKER_ADDED, TRACKER_REMOVED
    }

    /**
     * This method is called for every thing that exists in the
     * {@link ThingRegistryImpl} and for every added thing.
     * 
     * @param thing
     *            thing
     * @param thingTrackerEvent
     *            thing tracker event
     */
    void thingAdded(Thing thing, ThingTrackerEvent thingTrackerEvent);

    /**
     * This method is called for every thing that was removed from the
     * {@link ThingRegistryImpl}. Moreover the method is called for every thing,
     * that exists in the {@link ThingRegistryImpl}, when the tracker is
     * unregistered.
     * 
     * @param thing
     *            thing
     * @param thingTrackerEvent
     *            thing tracker event
     */
    void thingRemoved(Thing thing, ThingTrackerEvent thingTrackerEvent);
}
