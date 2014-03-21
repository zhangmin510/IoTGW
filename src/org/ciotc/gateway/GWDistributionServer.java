/**
 *
 * GWDistributionServer.java
 * ZhangMin.name - zhangmin@zhangmin.name
 * PositioningGW
 *
 */
package org.ciotc.gateway;

import java.net.Socket;
import java.net.SocketException;
import org.apache.log4j.Logger;



/**
 * @author ZhangMin.name
 *
 */
public class GWDistributionServer extends GWPacketServer {
	private static Logger logger = Logger.getLogger(GWFeedServer.class);
	public GWDistributionServer(int port) {
		super(port);
	}

	@Override
	public void OnConnectionEstablished(Socket sock) {
		String host = sock.getInetAddress().getHostAddress() + "[" + sock.getPort() + "]";
	    logger.info("Client: opening socket to " + host);
	    try
	    {
	      sock.setSoTimeout(60000);
	      ConnectionManager.getInstance().addFeedClients(sock);
	    } catch (SocketException e) {
	      logger.error(e);
	    }
		
	}

	@Override
	public void OnServerStarted() {
		logger.info("DistributionServer is started on port[" + this.serverPort + "].");
	}

	@Override
	public void OnServerStopped() {
		 ConnectionManager.getInstance().closeAllDistributionClients();
		 logger.info("DistributionServer is started on port[" + this.serverPort + "].");
	}

}
