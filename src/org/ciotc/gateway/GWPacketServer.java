/**
 *
 * GWPacketServer.java
 * ZhangMin.name - zhangmin@zhangmin.name
 * PositioningGW
 *
 */
package org.ciotc.gateway;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;

/**
 * @author ZhangMin.name
 *
 */
public abstract class GWPacketServer implements Runnable{
	private static final Logger logger = 
			Logger.getLogger(GWPacketServer.class);
	protected ServerSocket serverSock;
	protected int serverPort;
	private int serverSockLen = 128;
	public GWPacketServer(int port){
		this.serverPort = port;
	}
	public void start() throws IOException{
		this.serverSock = new ServerSocket(
				this.serverPort,this.serverSockLen);
		new Thread(this).start();
	}
	public void stop(){
		try {
			this.serverSock.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}
	@Override
	public void run() {
		OnServerStarted();
		Socket sock = null;
		try {
			while((sock = this.serverSock.accept()) != null){
				OnConnectionEstablished(sock);
			}
		} catch (SocketException e){
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		OnServerStopped();
		
	}
	public abstract void OnConnectionEstablished(Socket sock);
	public abstract void OnServerStarted();
	public abstract void OnServerStopped();
}
