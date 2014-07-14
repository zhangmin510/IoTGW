package name.zhangmin.gw.ruifan;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.rfidcn.gateway.ConnectionManager;
import com.rfidcn.gateway.GwDistributionServer;
import com.rfidcn.gateway.GwMessageServer;
import com.rfidcn.gateway.GwPacketServer;
import com.rfidcn.gateway.udp.GwPacketServerUDP;

public class Activator implements BundleActivator {

	private static BundleContext context;
	GwPacketServerUDP feedServer = new GwPacketServerUDP(5001);
	GwPacketServer distributionServer = new GwDistributionServer(5002);
	GwPacketServer messageServer = new GwMessageServer(5003);
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("RuiFan Gateway start...");
	    ConnectionManager.getInstance().start();
        feedServer.start();
        distributionServer.start();
        messageServer.start();
        Thread.sleep(2000);
        System.out.println("RuiFan Gateway started");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		messageServer.stop();
	    distributionServer.stop();
	    feedServer.stop(); 
	    ConnectionManager.getInstance().shutdown();
	    System.out.println("RuiFan Gateway stopped");
	}

}
