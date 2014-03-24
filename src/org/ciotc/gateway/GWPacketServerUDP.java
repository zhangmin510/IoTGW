/**
 *
 * GWPacketServerUDP.java
 * ZhangMin.name - zhangmin@zhangmin.name
 * PositioningGW
 *
 */
package org.ciotc.gateway;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import org.apache.log4j.Logger;

/**
 * @author ZhangMin.name
 *
 */
public class GWPacketServerUDP implements Runnable {
	private static final Logger logger = 
			Logger.getLogger(GWPacketServerUDP.class);
	protected DatagramSocket server;
	protected int port;
	
	public GWPacketServerUDP(int port){
		this.port = port;
	}
	public void start() throws IOException {
		this.server = new DatagramSocket(this.port);
		new Thread(this).start();
	}

	public void stop() {
	    try {
		    this.server.close();
		} catch (Exception e) {
		    logger.error(e);
		}
	}
	@Override
	public void run() {
		 OnServerStarted();
		 while (true) {
		      try
		      {
		        Message msg = receiveMessage();
		        OnClientMessage(msg);
		      }
		      catch (SocketException e)
		      {
		      }
		      catch (IOException localIOException)
		      {
		      }

		 }
	}
	private Message receiveMessage() throws IOException {
		byte[] receiveByte = new byte[256];
		DatagramPacket receivePacket = 
				new DatagramPacket(receiveByte,receiveByte.length);
		this.server.receive(receivePacket);
		int len = receivePacket.getLength();
		if(len == 0){
			throw new IOException("no data available");
		}
		if((receiveByte[0] == Message.START) && 
				(receiveByte[len - 1] == Message.END)){
			return new Message(receiveByte,len);
		}
		throw new IOException("dropped error data packet");
	}
	public void OnServerStarted() {
		 logger.info("PacketServerUDP is started on port[" + this.port + "].");
	}
	public void OnServerStopped() {
		 logger.info("PacketServerUDP is stopped.");
	}
	public void OnClientMessage(Message msg) {
		 if (msg.isLocation()) {
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

}
