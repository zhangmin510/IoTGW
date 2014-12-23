/**
 * IotGW Core Activator
 */
package name.zhangmin.gw.core;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The activator class controls the plug-in life cycle
 */
public class CoreActivator{

	private static Logger logger = LoggerFactory.getLogger(CoreActivator.class); 
	private static BundleContext context;

	
	public void setContext(BundleContext arg0){
		context = arg0;
	}
	
	public void start() {
		logger.debug("Core bundle has been started.");
	}
	
	public void stop() {
		context = null;
		logger.debug("Core bundle has been stopped.");
	}
	/**
	 * Returns the bundle context of this bundle
	 * @return the bundle context
	 */
	public static BundleContext getContext() {
		return context;
	}
}
