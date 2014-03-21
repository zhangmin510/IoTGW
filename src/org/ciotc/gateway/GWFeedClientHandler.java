/**
 *
 * GWFeedClientHandler.java
 * ZhangMin.name - zhangmin@zhangmin.name
 * PositioningGW
 *
 */
package org.ciotc.gateway;

import java.net.Socket;

/**
 * @author ZhangMin.name
 *
 */
public class GWFeedClientHandler extends GWClientHandler {

	public GWFeedClientHandler(Socket client) {
		super(client);
	}

	@Override
	public void OnClientIdle(Socket socket) {
		
	}

	@Override
	public void OnClientMessage(Message msg) {
		ConnectionManager.getInstance().broadcastToDistributionClients(msg);
		
	}

	@Override
	public void OnClientException(Socket socket, Exception exception) {
		
		
	}

}
