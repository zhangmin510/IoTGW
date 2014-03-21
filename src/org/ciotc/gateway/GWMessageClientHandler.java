/**
 *
 * GWMessageClientHandler.java
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
public class GWMessageClientHandler extends GWClientHandler {

	public GWMessageClientHandler(Socket client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void OnClientIdle(Socket socket) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnClientMessage(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnClientException(Socket socket, Exception exception) {
		// TODO Auto-generated method stub
		
	}

}
