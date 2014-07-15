package name.zhangmin.gw.io.servicediscovery;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {

	private static Logger logger = LoggerFactory.getLogger(Activator.class);
	
	/**
	 * Called whenever the OSGi framework starts our bundle
	 */
	public void start(BundleContext context) throws Exception {
		logger.debug("Discovery service has been started.");
	}

	/**
	 * Called whenever the OSGi framework stops our bundle
	 */
	public void stop(BundleContext context) throws Exception {
		logger.debug("Discovery service has been stopped.");
	}

}
