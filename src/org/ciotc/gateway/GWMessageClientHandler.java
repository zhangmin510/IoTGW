/**
 *
 * GWMessageClientHandler.java
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
public class GWMessageClientHandler extends GWClientHandler {
	private static final Logger logger = Logger.getLogger(GWMessageClientHandler.class);
	public GWMessageClientHandler(Socket client) {
		super(client);
	}
	@Override
	public void OnClientIdle(Socket sock)
	  {
	    String host = sock.getInetAddress().getHostAddress() 
	    			+ "[" + sock.getPort() + "]";
	    logger.info("OnClientIdle: " + host);
	    ConnectionManager.getInstance().removeMessageClients(sock);
	  }
	@Override
	  public void OnClientMessage(Message msg)
	  {
	    if (msg.isCustom()) {
	      logger.debug(Message.dump(msg.getBytes(), msg.getLength()));

	      ConnectionManager.getInstance().broadcastToMessageClients(msg);
	    } else {
	      logger.debug("throw away : " + 
	    		  Message.dump(msg.getBytes(),msg.getLength()));
	    }
	  }
	@Override
	public void OnClientException(Socket socket, Exception exception) {
		String host = socket.getInetAddress().getHostAddress() 
					+ "[" + socket.getPort() + "]";
	    logger.debug("OnClientException: " + host + "\n" + exception);
	    ConnectionManager.getInstance().removeMessageClients(socket);
	}

}
