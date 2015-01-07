
package name.zhangmin.gw.core.thing;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import name.zhangmin.gw.config.Configuration;
import name.zhangmin.gw.core.storage.Storage;
import name.zhangmin.gw.core.storage.StorageSelector;
import name.zhangmin.gw.core.storage.StorageSelector.StorageSelectionListener;
import name.zhangmin.gw.core.storage.StorageService;
import name.zhangmin.gw.core.thing.binding.ThingHandlerFactory;
import name.zhangmin.gw.core.thing.uid.ThingTypeUID;
import name.zhangmin.gw.core.thing.uid.ThingUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link ManagedThingProvider} is an OSGi service, that allows to add or remove
 * things at runtime by calling {@link ManagedThingProvider#addThing(Thing)} or
 * {@link ManagedThingProvider#removeThing(Thing)}. An added thing is
 * automatically exposed to the {@link ThingRegistry}.
 * 
 * 
 */
public class ManagedThingProvider extends AbstractThingProvider implements StorageSelectionListener<Thing> {

    private final static Logger logger = LoggerFactory.getLogger(ManagedThingProvider.class);

    private Storage<Thing> storage;

    private StorageSelector<Thing> storageSelector;

    private List<ThingHandlerFactory> thingHandlerFactories = new CopyOnWriteArrayList<>();

    public ManagedThingProvider() {
        this.storageSelector = new StorageSelector<>(ThingActivator.getContext(), Thing.class.getName(),
                this);
    }

    /**
     * Adds a things and informs all listeners.
     * 
     * @param thing
     *            thing that should be added.
     */
    public void addThing(Thing thing) {
        logger.info("Adding thing to managed thing provider '{}'.", thing.getUID());
        Thing oldThing = storage.put(thing.getUID().toString(), thing);
        if (oldThing != null) {
            notifyThingsChangeListenersAboutRemovedThing(oldThing);
        }
        notifyThingsChangeListenersAboutAddedThing(thing);
    }

    /**
     * Creates a thing based on the given configuration properties, adds it and
     * informs all listeners.
     * 
     * @param thingTypeUID
     *            thing type unique id
     * @param thingUID
     *            thing unique id which should be created. This id might be null.
     * @param bridge
     *            the thing's bridge. Null if there is no bridge or if the thing
     *            is a bridge by itself.
     * @param properties
     *            the configuration
     * @return the created thing
     */
    public Thing createThing(ThingTypeUID thingTypeUID, ThingUID thingUID,
            Configuration configuration) {
        logger.debug("Creating thing for type '{}'.", thingTypeUID);
        for (ThingHandlerFactory thingHandlerFactory : thingHandlerFactories) {
            if (thingHandlerFactory.supportsThingType(thingTypeUID)) {
                Thing thing = thingHandlerFactory.createThing(thingTypeUID, configuration, thingUID);
                addThing(thing);
                return thing;
            }
        }
        logger.warn(
                "Cannot create thing. No binding found that supports creating a thing for the thing type {}.",
                thingTypeUID);
        return null;
    }

    @Override
    public Collection<Thing> getThings() {
        return storage.getValues();
    }

    /**
     * Removes a thing and informs all listeners.
     * 
     * @param uid
     *            UID of the thing that should be removed
     * @return thing that was removed or null if no thing was the given id
     *         exists
     */
    public Thing removeThing(ThingUID uid) {
        logger.debug("Removing thing from managed thing provider '{}'.", uid);
        Thing removedThing = storage.remove(uid.toString());
        if (removedThing != null) {
            notifyThingsChangeListenersAboutRemovedThing(removedThing);
        }
        return removedThing;
    }

    @Override
    public void storageSelected(Storage<Thing> storage) {
        this.storage = storage;
    }

    public void addStorageService(StorageService storageService) {
        this.storageSelector.addStorageService(storageService);
    }

    public void addThingHandlerFactory(ThingHandlerFactory thingHandlerFactory) {
        this.thingHandlerFactories.add(thingHandlerFactory);
    }

    public void removeStorageService(StorageService storageService) {
        this.storageSelector.removeStorageService(storageService);
    }

    protected void removeThingHandlerFactory(ThingHandlerFactory thingHandlerFactory) {
        this.thingHandlerFactories.remove(thingHandlerFactory);
    }

}
