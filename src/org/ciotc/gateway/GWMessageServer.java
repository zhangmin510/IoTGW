/**
 *
 * GWMessageServer.java
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
public class GWMessageServer extends GWPacketServer {
	private static final Logger logger = Logger.getLogger(GWMessageServer.class);
	public GWMessageServer(int port) {
		super(port);
	}

	@Override
	public void OnConnectionEstablished(Socket sock) {
		String host = sock.getInetAddress().getHostAddress() 
						+ "[" + sock.getPort() + "]";
	    logger.info("Client: opening socket to " + host);
	    try
	    {
	      sock.setSoTimeout(60000);

	      ConnectionManager.getInstance().addMessageClients(sock);
	    } catch (SocketException e) {
	      logger.error(e);
	    }
		
	}

	@Override
	public void OnServerStarted() {
		 logger.info("MessageServer is started on port[" 
				 		+ this.serverPort + "].");
		
	}

	@Override
	public void OnServerStopped() {
		ConnectionManager.getInstance().closeAllMessageClients();
	    logger.info("MessageServer is stopped.");
		
	}

}
