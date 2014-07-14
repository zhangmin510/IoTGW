package name.zhangmin.gw.init;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("START IOTGW INITIALIZTION BUNDLE");
		//set gateway home path
		String gwHome = System.getProperty("name.zhangmin.gw.home");
		if (gwHome == null) {
			String userDir = System.getProperty("user.dir");
			System.setProperty("name.zhangmin.gw.home", userDir);
			gwHome = System.getProperty("name.zhangmin.gw.home");
		}
		System.out.println("ALL IOTGW CONFIGURATION PATHS RELATIVE TO " +
				System.getProperty("name.zhangmin.gw.home"));
		
		// may set up other property here
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
