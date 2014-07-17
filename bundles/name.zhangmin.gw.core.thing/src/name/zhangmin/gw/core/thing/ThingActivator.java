package name.zhangmin.gw.core.thing;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThingActivator implements BundleActivator {
	private static Logger logger = LoggerFactory.getLogger(ThingActivator.class);
	private static BundleContext context;

	public static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		ThingActivator.context = bundleContext;
		logger.info("IoTGW thing management bundle started");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		ThingActivator.context = null;
		logger.info("IoTGW thing management bundle stopped");
	}

}
