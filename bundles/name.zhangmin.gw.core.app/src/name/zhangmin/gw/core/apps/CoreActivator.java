/**
 * IotGW Core Activator
 */
package name.zhangmin.gw.core.apps;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The activator class controls the plug-in life cycle
 */
public class CoreActivator{
	private static BundleContext context;
	private static Logger logger = LoggerFactory.getLogger(CoreActivator.class); 
	
	
	public void setContext(BundleContext context) {
		CoreActivator.context = context;
	}
	
	public static BundleContext getContext() {
		return context;
	}
	/**
	 * Called whenever the OSGi framework starts our bundle
	 */
	public void start() {
		logger.debug("Core bundle has been started.");
	}

	/**
	 * Called whenever the OSGi framework stops our bundle
	 */
	public void stop() {
		logger.debug("Core bundle has been stopped.");
	}
}
