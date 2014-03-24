/**
 *
 * JettyExample.java
 * ZhangMin.name - zhangmin@zhangmin.name
 * PositioningGW
 *
 */
package org.ciotc.gateway;
import java.io.IOException;

/**
 * @author ZhangMin.name
 *
 */
public class GWManager{
	private GWPacketServerUDP feedServer;
	private GWPacketServer distributionServer;
	private GWPacketServer messageServer;
	private static GWManager manager;
	public static boolean running;
	public static long startTime;
	private GWManager(){
		feedServer = new GWPacketServerUDP(5001);
		distributionServer = new GWDistributionServer(5002);
		messageServer = new GWMessageServer(5003);
	}
	public static GWManager getInstance(){
		if(manager == null){
			manager = new GWManager();
		}
	    return manager;
	}
	
	public void start(){
		ConnectionManager.getInstance().start();
		try {
			
			feedServer.start();
			distributionServer.start();
			messageServer.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		running = true;
		startTime = System.currentTimeMillis();
	}
	public void stop(){
		feedServer.stop();
		distributionServer.stop();
		messageServer.stop();
		ConnectionManager.getInstance().shutdown();
		startTime = -1;
		running = false;
	}
}
