/**
 * 
 */
package name.zhangmin.gw.test;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * {@link OSGiTest} is an abstract base class for OSGi based tests. It provides
 * convenience methods to register and unregister mocks as OSGi services. All
 * services, which are registered through the {@link OSGiTest#registerService}
 * methods, are unregistered automatically in the tear down of the test.
 * 
 * @author ZhangMin.name
 *
 */
public abstract class OSGiTest {
	BundleContext bundleContext;
	Map<String, ServiceRegistration<?>> registeredServices = 
			new HashMap<String, ServiceRegistration<?>>();
	
	public void bindBundleContext() {
		this.bundleContext = getBundleContext();
		
	}
	/**
	 * Returns the {@link BundleContext}, which is used for registration and unregistration of OSGi 
	 * services. By default it uses the bundle context of the test class itself. This method can be overridden
	 * by concrete implementations to provide another bundle context.
	 * 
	 * @return bundle context
	 */
	protected BundleContext getBundleContext() {
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		return bundle.getBundleContext();
	}
	/**
	 * Returns an OSGi service for the given class.
	 * 
	 * @param clazz class under which the OSGi service is registered
	 * @return OSGi service or null if no service can be found for the given class
	 */
	protected <T> T getService(Class<T> clazz){
		ServiceReference<?> serviceReference = bundleContext.getServiceReference(clazz.getName());
		if (serviceReference != null) return (T) bundleContext.getService(serviceReference);
		return null;
	}


	/**
	 * Registers the given object as OSGi service. The first interface is used as OSGi service 
	 * interface name.
	 * 
	 * @param service service to be registered
	 * @param properties OSGi service properties
	 * @return service registration object
	 */
	protected void registerService(Class service, Dictionary properties) {
		String interfaceName = service.getName();
		registeredServices.put(interfaceName, bundleContext.registerService(interfaceName, service, properties));
	}


	/**
	 * Unregisters an OSGi service by the given object, that was registered before. If the object is 
	 * a String the service is unregistered by the interface name. If the given service parameter is 
	 * the service itself the interface name is taken from the first interface of the service object.
	 * 
	 * @param service service or service interface name
	 * @return the service registration that was unregistered or null if no service could be found
	 */
	protected void unregisterService(String service) {
		
		registeredServices.remove(service);
	}

	
	public void unregisterMocks() {
		
	}
	
}
