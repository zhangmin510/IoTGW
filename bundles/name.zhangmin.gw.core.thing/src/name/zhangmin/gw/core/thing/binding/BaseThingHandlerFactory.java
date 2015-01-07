/**
 * 
 */
package name.zhangmin.gw.core.thing.binding;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import name.zhangmin.gw.config.Configuration;
import name.zhangmin.gw.core.thing.Thing;
import name.zhangmin.gw.core.thing.ThingActivator;
import name.zhangmin.gw.core.thing.ThingFactory;
import name.zhangmin.gw.core.thing.type.ThingType;
import name.zhangmin.gw.core.thing.type.ThingTypeRegistry;
import name.zhangmin.gw.core.thing.uid.ThingTypeUID;
import name.zhangmin.gw.core.thing.uid.ThingUID;

/**
 * This class provides a base implementation for the
 * {@link ThingHandlerFactory} interface. It provides the OSGi service
 * registration logic.
 * 
 * @author ZhangMin.name
 *
 */
public abstract class BaseThingHandlerFactory implements ThingHandlerFactory {
	private Map<String, ServiceRegistration<ThingHandler>> thingHandlers = new HashMap<>();
	private BundleContext bundleContext;
	private ServiceTracker<ThingTypeRegistry, ThingTypeRegistry> thingTypeRegistryServiceTracker;
	
	protected void activate() {
		this.bundleContext = ThingActivator.getContext();
		this.thingTypeRegistryServiceTracker = new ServiceTracker<>(bundleContext, ThingTypeRegistry.class.getName(), null);
		this.thingTypeRegistryServiceTracker.open();
	}
	
	protected void deactivate() {
		for (ServiceRegistration<ThingHandler> serviceRegistration : this.thingHandlers.values()) {
			ThingHandler thingHandler = (ThingHandler) this.bundleContext.getService(serviceRegistration.getReference());
			thingHandler.dispose();
		}
		this.thingTypeRegistryServiceTracker.close();
		this.bundleContext = null;
	}
	
	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.binding.ThingHandlerFactory#supportedThingType(name.zhangmin.gw.core.thing.ThingTypeUID)
	 */
	@Override
	public boolean supportsThingType(ThingTypeUID thingTypeUID) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.binding.ThingHandlerFactory#registerHandler(name.zhangmin.gw.core.thing.Thing)
	 */
	@Override
	public void registerHandler(Thing thing) {
		ThingHandler thingHandler = createHandler(thing);
		ServiceRegistration<ThingHandler> serviceRegistration = registerAsService(thing, thingHandler);
		this.thingHandlers.put(thing.getUID().toString(), serviceRegistration);
		thingHandler.initialize();
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.binding.ThingHandlerFactory#unregisterHandler(name.zhangmin.gw.core.thing.Thing)
	 */
	@Override
	public void unregisterHandler(Thing thing) {
		ServiceRegistration<ThingHandler> serviceRegistration = this.thingHandlers.remove(thing.getUID().toString());
		if (serviceRegistration != null) {
			ThingHandler thingHandler = this.bundleContext.getService(serviceRegistration.getReference());
			serviceRegistration.unregister();
			removeHandler(thingHandler);
			thingHandler.dispose();
		}

	}
	private ServiceRegistration<ThingHandler> registerAsService(Thing thing, ThingHandler thingHandler) {
		Dictionary<String, Object> serviceProperties = getServiceProperties(thing, thingHandler);
		
		@SuppressWarnings("unchecked")
		ServiceRegistration<ThingHandler> serviceRegistration = (ServiceRegistration<ThingHandler>) 
				this.bundleContext.registerService(ThingHandler.class.getName(), thingHandler, serviceProperties);
		return serviceRegistration;
	}
	private Dictionary<String, Object>getServiceProperties(Thing thing, ThingHandler thingHandler) {
		Dictionary<String, Object> serviceProperties = new Hashtable<>();
		serviceProperties.put(ThingHandler.SERVICE_PROPERTY_THING_ID, thing.getUID());
		serviceProperties.put(ThingHandler.SERVICE_PROPERTY_THING_TYPE, thing.getThingTypeUID().toString());
		
		Map<String, Object> additionalServiceProperties = getServiceProperties(thingHandler);
		if (additionalServiceProperties != null) {
			for (Entry<String, Object> additionalServiceProperty : additionalServiceProperties.entrySet()) {
				serviceProperties.put(additionalServiceProperty.getKey(), additionalServiceProperty.getValue());
			}
		}
		
		return serviceProperties;
	}
	   /**
     * This method can be overridden to append additional service properties to
     * the registered OSGi {@link ThingHandler} service.
     * 
     * @param thingHandler
     *            thing handler, which will be registered as OSGi service
     * @return map of additional service properties
     */
    protected Map<String, Object> getServiceProperties(ThingHandler thingHandler) {
        return null;
    }
	
    /**
     * The method implementation must create and return the {@link ThingHandler}
     * for the given thing.
     * 
     * @param thing
     *            thing
     * @return thing handler
     */
    protected abstract ThingHandler createHandler(Thing thing);

    /**
     * This method is called when a thing handler should be removed. The
     * implementing caller can override this method to release specific
     * resources.
     * 
     * @param thing
     *            thing
     */
    protected void removeHandler(ThingHandler thingHandler) {
        // can be overridden
    }

    @Override
    public void removeThing(ThingUID thingUID) {
        // can be overridden
    }
    
    /**
     * Returns the {@link ThingType} which is represented by the given {@link ThingTypeUID}.
     * @param thingTypeUID the unique id of the thing type
     * @return the thing type represented by the given unique id
     */
    protected ThingType getThingTypeByUID(ThingTypeUID thingTypeUID) {
    	ThingTypeRegistry thingTypeRegistry = thingTypeRegistryServiceTracker.getService();
    	if (thingTypeRegistry != null) {
    		return thingTypeRegistry.getThingType(thingTypeUID);
    	}
    	return null;
    }
    
    /**
     * Creates a thing based on given thing type uid.
     * 
     * @param thingTypeUID
     *            thing type uid (should not be null)
     * @param thingUID
     *            thingUID (should not be null)
     * @param configuration
     *            (should not be null)
     * @param bridge
     *            (can be null)
     * @return thing
     */
    protected Thing createThing(ThingTypeUID thingTypeUID, ThingUID thingUID,
            Configuration configuration) {
    	ThingType thingType = getThingTypeByUID(thingTypeUID);
    	return ThingFactory.createThing(thingType, thingUID, configuration);
    }

	

}
