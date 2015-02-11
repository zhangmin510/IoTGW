package name.zhangmin.gw.binding.positioning;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This activator sets up the needed services for the positioning devices.
 * 
 * @author ZhangMin.name
 *
 */
public class PositioningActivator implements BundleActivator {
	private static final Logger logger = LoggerFactory.getLogger(PositioningActivator.class);
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		PositioningActivator.context = bundleContext;
		logger.debug("Positioning binding has been started.");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		PositioningActivator.context = null;
		logger.debug("Positioning binding has been stopped.");
	}

}
