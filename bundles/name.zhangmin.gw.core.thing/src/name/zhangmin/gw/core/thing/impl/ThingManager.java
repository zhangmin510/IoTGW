/**
 * 
 */
package name.zhangmin.gw.core.thing.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.zhangmin.gw.core.event.EventPublisher;
import name.zhangmin.gw.core.event.impl.AbstractEventSubscriber;
import name.zhangmin.gw.core.thing.Channel;
import name.zhangmin.gw.core.thing.ChannelUID;
import name.zhangmin.gw.core.thing.Thing;
import name.zhangmin.gw.core.thing.ThingRegistry;
import name.zhangmin.gw.core.thing.ThingStatus;
import name.zhangmin.gw.core.thing.ThingUID;
import name.zhangmin.gw.core.thing.binding.ThingHandler;
import name.zhangmin.gw.core.thing.binding.ThingHandlerFactory;
import name.zhangmin.gw.core.thing.link.AppChannelLinkRegistry;
import name.zhangmin.gw.core.type.Command;
import name.zhangmin.gw.core.type.Data;

/**
 * This class tracks all things in the {@link ThingRegistry} and
 * mediates the communication between the {@link Thing} ant the
 * {@link ThingHandler} from the binding. Therefore it tracks
 * {@link ThingHandlerFactory}s and calls 
 * {@link ThingHandlerFactory#registerHandler(Thing)}
 * for each thing, that was added to the {@link ThingRegistry}.
 * In addition the {@link ThingManager} acts as an {@link EventHandler} and
 * subscribe to IoTGW update and command events.
 * 
 * @author ZhangMin.name
 *
 */
public class ThingManager extends AbstractEventSubscriber implements ThingTracker{
	
	 private final class ThingHandlerTracker extends ServiceTracker<ThingHandler, ThingHandler> {

	        public ThingHandlerTracker(BundleContext context) {
	            super(context, ThingHandler.class.getName(), null);
	        }

	        @Override
	        public ThingHandler addingService(ServiceReference<ThingHandler> reference) {
	            ThingUID thingId = getThingId(reference);

	            logger.warn("Thing handler for thing '{}' added.", thingId);

	            ThingHandler thingHandler = bundleContext.getService(reference);
	            Thing thing = getThing(thingId);

	            if (thing != null) {
	                handlerAdded(thing, thingHandler);
	            } else {
	                logger.warn("Found handler for non-existing thing '{}'.", thingId);
	            }

	            thingHandlers.put(thingId, thingHandler);
	            return thingHandler;
	        }

	        @Override
	        public void removedService(ServiceReference<ThingHandler> reference, ThingHandler service) {
	            ThingUID thingId = getThingId(reference);
	            logger.warn("Thing handler for thing '{}' removed.", thingId);
	            Thing thing = getThing(thingId);
	            if (thing != null) {
	                handlerRemoved(thing, service);
	            }
	            thingHandlers.remove(getThingId(reference));
	        }

	        private ThingUID getThingId(ServiceReference<ThingHandler> reference) {
	            return (ThingUID) reference.getProperty("thing.id");
	        }

	    }

	    private BundleContext bundleContext;

	    private EventPublisher eventPublisher;

	    private AppChannelLinkRegistry appChannelLinkRegistry;

	    private Logger logger = LoggerFactory.getLogger(ThingManager.class);

	    private List<ThingHandlerFactory> thingHandlerFactories = new CopyOnWriteArrayList<>();

	    private Map<ThingUID, ThingHandler> thingHandlers = new ConcurrentHashMap<>();

	    private ThingHandlerTracker thingHandlerTracker;

	    private ThingListener thingListener = new ThingListener() {

	        @Override
	        public void channelUpdated(ChannelUID channelUID, Data state) {
	            String item = appChannelLinkRegistry.getBoundApp(channelUID);
	            if (item != null) {
	                eventPublisher.postData(item, state);
	            }
	        }
	    };

	    private ThingRegistryImpl thingRegistry;

	    private Set<Thing> things = new CopyOnWriteArraySet<>();

	    /**
	     * Method is called when a {@link ThingHandler} is added.
	     * 
	     * @param thing
	     *            thing
	     * @param thingHandler
	     *            thing handler
	     */
	    public void handlerAdded(Thing thing, ThingHandler thingHandler) {
	        logger.info("Assigning handler and setting status to ONLINE.", thing.getUID());
	        ((ThingImpl) thing).addThingListener(thingListener);
	        thing.setHandler(thingHandler);
	        thing.setStatus(ThingStatus.ONLINE);
	    }

	    /**
	     * Method is called when a {@link ThingHandler} is removed.
	     * 
	     * @param thing
	     *            thing
	     * @param thingHandler
	     *            thing handler
	     */
	    public void handlerRemoved(Thing thing, ThingHandler thingHandler) {
	        logger.info("Removing handler and setting status to OFFLINE.", thing.getUID());
	        ((ThingImpl) thing).removeThingListener(thingListener);
	        thing.setHandler(null);
	        thing.setStatus(ThingStatus.OFFLINE);
	    }

	    @Override
	    public void receiveCommand(String appName, Command command) {
	        for (Thing thing : this.things) {
	            List<Channel> channels = thing.getChannels();
	            for (Channel channel : channels) {
	                if (isLinked(appName, channel)) {
	                    logger.info(
	                            "Delegating command '{}' for item '{}' to handler for channel '{}'",
	                            command, appName, channel.getUID());
	                    try {
	                        thing.getHandler().handleCommand(channel.getUID(), command);
	                    } catch (Exception ex) {
	                        logger.error("Exception occured while calling handler: " + ex.getMessage(),
	                                ex);
	                    }
	                }
	            }
	        }
	    }

	    @Override
	    public void receiveData(String itemName, Data newState) {
	        for (Thing thing : this.things) {
	            List<Channel> channels = thing.getChannels();
	            for (Channel channel : channels) {
	                if (isLinked(itemName, channel)) {
	                    ThingHandler handler = thing.getHandler();
	                    if (handler != null) {
	                        logger.info(
	                                "Delegating update '{}' for item '{}' to handler for channel '{}'",
	                                newState, itemName, channel.getUID());
	                        try {
	                            handler.handleData(channel.getUID(), newState);
	                        } catch (Exception ex) {
	                            logger.error(
	                                    "Exception occured while calling handler: " + ex.getMessage(),
	                                    ex);
	                        }
	                    }
	                }
	            }
	        }
	    }

	    @Override
	    public void thingAdded(Thing thing, ThingTrackerEvent thingTrackerEvent) {
	        this.things.add(thing);
	        logger.debug("Thing '{}' is tracked by ThingManager.", thing.getUID());
	        ThingHandler thingHandler = thingHandlers.get(thing.getUID());
	        if (thingHandler == null) {
	            ThingHandlerFactory thingHandlerFactory = findThingHandlerFactory(thing);
	            if (thingHandlerFactory != null) {
	                registerHandler(thing, thingHandlerFactory);
	            } else {
	                logger.info("Cannot register handler. No handler factory for thing '{}' found.",
	                        thing.getUID());
	            }
	        } else {
	            logger.debug("Handler for thing '{}' already exists.", thing.getUID());
	            handlerAdded(thing, thingHandler);
	        }
	    }

	    @Override
	    public void thingRemoved(Thing thing, ThingTrackerEvent thingTrackerEvent) {
	        if (thingTrackerEvent == ThingTrackerEvent.THING_REMOVED) {
	            ThingUID thingId = thing.getUID();
	            ThingHandler thingHandler = thingHandlers.get(thingId);
	            if (thingHandler != null) {
	                ThingHandlerFactory thingHandlerFactory = findThingHandlerFactory(thing);
	                if (thingHandlerFactory != null) {
	                    unregisterHandler(thing, thingHandlerFactory);
	                } else {
	                    logger.info(
	                            "Cannot unregister handler. No handler factory for thing '{}' found.",
	                            thing.getUID());
	                }
	            }
	        }
	        logger.info("Thing '{}' is no longer tracked by ThingManager.", thing.getUID());
	        this.things.remove(thing);
	    }
	    

	    private ThingHandlerFactory findThingHandlerFactory(Thing thing) {
	        for (ThingHandlerFactory factory : thingHandlerFactories) {
	            if (factory.supportsThingType(thing.getThingTypeUID())) {
	                return factory;
	            }
	        }
	        return null;
	    }

	    private Thing getThing(ThingUID id) {
	        for (Thing thing : this.things) {
	            if (thing.getUID().equals(id)) {
	                return thing;
	            }
	        }

	        return null;
	    }

	    private boolean isLinked(String itemName, Channel channel) {
	        return appChannelLinkRegistry.isLinked(itemName, channel.getUID());
	    }

	    private void registerHandler(Thing thing, ThingHandlerFactory thingHandlerFactory) {
	        logger.info("Creating handler for thing '{}'.", thing.getUID());
	        try {
	            thingHandlerFactory.registerHandler(thing);
	        } catch (Exception ex) {
	            logger.error("Exception occured while calling handler: " + ex.getMessage(), ex);
	        }
	    }

	    private void unregisterHandler(Thing thing, ThingHandlerFactory thingHandlerFactory) {
	        logger.info("Removing handler for thing '{}'.", thing.getUID());
	        try {
	            thingHandlerFactory.unregisterHandler(thing);
	        } catch (Exception ex) {
	            logger.error("Exception occured while calling handler: " + ex.getMessage(), ex);
	        }
	    }

	    protected void activate(ComponentContext componentContext) {
	        this.bundleContext = componentContext.getBundleContext();
	        this.thingHandlerTracker = new ThingHandlerTracker(this.bundleContext);
	        this.thingHandlerTracker.open();
	    }

	    protected void addThingHandlerFactory(ThingHandlerFactory thingHandlerFactory) {

	        logger.debug("Thing handler factory '{}' added", thingHandlerFactory.getClass()
	                .getSimpleName());

	        thingHandlerFactories.add(thingHandlerFactory);

	        for (Thing thing : this.things) {
	            if (thingHandlerFactory.supportsThingType(thing.getThingTypeUID())) {
	                ThingUID thingId = thing.getUID();

	                ThingHandler thingHandler = thingHandlers.get(thingId);
	                if (thingHandler == null) {
	                    registerHandler(thing, thingHandlerFactory);
	                } else {
	                    logger.warn("Thing handler for thing '{}' already exists.", thingId);
	                }
	            }
	        }
	    }

	    protected void deactivate(ComponentContext componentContext) {
	        this.thingHandlerTracker.close();
	    }

	    protected void removeThingHandlerFactory(ThingHandlerFactory thingHandlerFactory) {
	        logger.info("Thing handler factory '{}' removed", thingHandlerFactory.getClass()
	                .getSimpleName());
	        thingHandlerFactories.remove(thingHandlerFactory);
	    }

	    protected void setEventPublisher(EventPublisher eventPublisher) {
	        this.eventPublisher = eventPublisher;
	    }

	    protected void setItemChannelLinkRegistry(AppChannelLinkRegistry itemChannelLinkRegistry) {
	        this.appChannelLinkRegistry = itemChannelLinkRegistry;
	    }

	    protected void setThingRegistry(ThingRegistry thingRegistry) {
	        this.thingRegistry = (ThingRegistryImpl) thingRegistry;
	        this.thingRegistry.addThingTracker(this);
	    }

	    protected void unsetEventPublisher(EventPublisher eventPublisher) {
	        this.eventPublisher = null;
	    }

	    protected void unsetItemChannelLinkRegistry(AppChannelLinkRegistry itemChannelLinkRegistry) {
	        this.appChannelLinkRegistry = null;
	    }

	    protected void unsetThingRegistry(ThingRegistry thingRegistry) {
	        this.thingRegistry.removeThingTracker(this);
	        this.thingRegistry = null;
	    }


}
