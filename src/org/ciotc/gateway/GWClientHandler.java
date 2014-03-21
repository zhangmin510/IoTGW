/**
 *
 * GWClientHandler.java
 * ZhangMin.name - zhangmin@zhangmin.name
 * PositioningGW
 *
 */
package org.ciotc.gateway;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.log4j.Logger;

/**
 * @author ZhangMin.name
 *
 */
public abstract class GWClientHandler implements Runnable{
	private static final Logger logger = 
			Logger.getLogger(GWClientHandler.class);
	protected Socket client;
	public GWClientHandler(Socket client){
		this.client = client;
	}
	@Override
	public void run() {
		while(true){
			Message msg = null;
			logger.debug("Waiting for a message from client...");
			try{
				msg = rcvMsg();
			}catch(SocketTimeoutException e){
				OnClientIdle(this.client);
			}catch(IOException e){
				OnClientException(this.client,e);
			}
			OnClientMessage(msg);
		}
	}
	private Message rcvMsg() throws IOException{
		
		return new Message();
	}
	public abstract void OnClientIdle(Socket socket);
	public abstract void OnClientMessage(Message msg);
	public abstract void OnClientException(
			Socket socket,Exception exception);
}
