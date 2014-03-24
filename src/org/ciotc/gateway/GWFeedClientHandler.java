/**
 *
 * GWFeedClientHandler.java
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
public class GWFeedClientHandler extends GWClientHandler {
	public static final Logger logger = 
			Logger.getLogger(GWFeedClientHandler.class);
	public GWFeedClientHandler(Socket client) {
		super(client);
	}

	@Override
	public void OnClientIdle(Socket socket) {
		String host = socket.getInetAddress().getHostAddress() + 
				"[" + socket.getPort() + "]";
	    logger.info("OnClientIdle: " + host);
	    ConnectionManager.getInstance().removeFeedClients(socket);
	}

	@Override
	public void OnClientMessage(Message msg) {
		if (msg.isLocation())
	    {
	      if (msg.getType() == Message.MT_LOCATION) {
	        msg.mergeTime();
	      }
	      logger.debug(Message.dump(msg.getBytes(), msg.getBytes().length));
	      ConnectionManager.getInstance().broadcastToDistributionClients(msg);
	    } else if (msg.isMessage()) {
	      logger.debug(Message.dump(msg.getBytes(), msg.getBytes().length));

	      ConnectionManager.getInstance().broadcastToMessageClients(msg);
	    } else {
	      logger.debug("throw away : " + Message.dump(msg.getBytes(), msg.getBytes().length));
	    }
		
	}

	@Override
	public void OnClientException(Socket socket, Exception exception) {
		String host = socket.getInetAddress().getHostAddress() + "[" +
				socket.getPort() + "]";
		logger.error("OnClientException: " + host + "\n" + exception);
		ConnectionManager.getInstance().removeFeedClients(socket);
		
	}

}
