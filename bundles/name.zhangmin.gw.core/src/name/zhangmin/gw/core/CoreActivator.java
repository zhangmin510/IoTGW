/**
 * IotGW Core Activator
 */
package name.zhangmin.gw.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The activator class controls the plug-in life cycle
 */
public class CoreActivator{

	private static Logger logger = LoggerFactory.getLogger(CoreActivator.class); 
	
	
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
