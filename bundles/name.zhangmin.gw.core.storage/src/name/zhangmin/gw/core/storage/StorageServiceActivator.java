/**
 * IotGW Core Activator
 */
package name.zhangmin.gw.core.storage;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The activator class controls the plug-in life cycle
 */
public class StorageServiceActivator implements BundleActivator {
	private static BundleContext context;
	private static Logger logger = LoggerFactory.getLogger(StorageServiceActivator.class); 
	

	
	public static BundleContext getContext() {
		return context;
	}


	@Override
	public void start(BundleContext arg0) throws Exception {
		StorageServiceActivator.context = arg0;
		
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		StorageServiceActivator.context = null;
	}
}
