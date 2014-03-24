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
				msg = receiveMessage();

			}catch(SocketTimeoutException e){
				OnClientIdle(this.client);
			}catch(IOException e){
				OnClientException(this.client,e);
			}
			OnClientMessage(msg);
		}
	}
	private Message receiveMessage() throws IOException{
		byte[] buf = new byte[256];
	    int pos = 0;
	    boolean startFound = false;
	    int data;
	    do { if (pos == 256) {
	        logger.debug("drop data " + pos + " bytes: \n" 
	        		+ Message.dump(buf, pos));
	        pos = 0;
	      }

	      data = this.client.getInputStream().read();

	      if (data == -1) {
	        throw new IOException("no data available");
	      }

	      if (data == Message.START) {
	        if (pos != 0) {
	          logger.debug("drop data " + pos + " bytes: \n" 
	        		  + Message.dump(buf, pos));
	          pos = 0;
	        }
	        startFound = true;
	      }

	      buf[(pos++)] = ((byte)data);
	    }
	    while ((!startFound) || (data != Message.END));
	    return new Message(buf, pos); 
	}
	public abstract void OnClientIdle(Socket socket);
	public abstract void OnClientMessage(Message msg);
	public abstract void OnClientException(
			Socket socket,Exception exception);
}
