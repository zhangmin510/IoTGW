/**
 * IotGW Core Activator
 */
package name.zhangmin.gw.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The activator class controls the plug-in life cycle
 */
public class CoreActivator implements BundleActivator{

	private static Logger logger = LoggerFactory.getLogger(CoreActivator.class); 
	private static BundleContext context;

	@Override
	public void start(BundleContext arg0) throws Exception {
		context = arg0;
		logger.debug("Core bundle has been started.");
		
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		context = arg0;
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
