/**
 * IotGW Core Activator
 */
package name.zhangmin.gw.core.apps;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The activator class controls the plug-in life cycle
 */
public class AppActivator implements BundleActivator{
	private static BundleContext context;
	
	public static BundleContext getContext() {
		return context;
	}

	@Override
	public void start(BundleContext arg0) throws Exception {
		AppActivator.context = arg0;
		
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		AppActivator.context = null;
		
	}
}
