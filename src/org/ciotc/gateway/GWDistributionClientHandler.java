/**
 *
 * GWDistributionClientHandler.java
 * ZhangMin.name - zhangmin@zhangmin.name
 * PositioningGW
 *
 */
package org.ciotc.gateway;

import java.net.Socket;
import org.apache.log4j.Logger;

/**
 * @author ZhangMin.name
 *
 */
public class GWDistributionClientHandler extends GWClientHandler {
	public static final Logger logger = 
			Logger.getLogger(GWDistributionClientHandler.class);

	public GWDistributionClientHandler(Socket client) {
		super(client);

	}

	@Override
	public void OnClientIdle(Socket socket) {
		String host = socket.getInetAddress().getHostAddress() +
				"[" + socket.getPort() + "]";
		logger.info("OnClientIdle: " + host);
		ConnectionManager.getInstance().removeDistributionClients(socket);
	}

	@Override
	public void OnClientMessage(Message msg) {
		logger.debug(Message.dump(msg.getBytes(),msg.getLength()));

		
	}

	@Override
	public void OnClientException(Socket socket, Exception exception) {
		String host = socket.getInetAddress().getHostAddress() + "[" +
						socket.getPort() + "]";
		logger.error("OnClientException: " + host + "\n" + exception);
		ConnectionManager.getInstance().removeDistributionClients(socket);
		
	}

}
