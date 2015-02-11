package name.zhangmin.gw.io.rest;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTActivator implements BundleActivator {

	private static BundleContext context;
    private static final Logger logger = LoggerFactory.getLogger(RESTActivator.class);
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		RESTActivator.context = bundleContext;
		logger.debug("REST API has been started.");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		RESTActivator.context = null;
		logger.debug("REST API has been stopped.");
	}
}
