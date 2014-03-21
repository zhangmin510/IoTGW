/**
 *
 * ClientSimulator.java
 * ZhangMin.name - zhangmin@zhangmin.name
 * PositioningGW
 *
 */
package org.ciotc.gateway.test;

import java.io.IOException;
import java.net.Socket;

/**
 * @author ZhangMin.name
 *
 */
public class ClientSimulator implements Runnable{
	private final String host;
	private final int port;
	private boolean type;//true sender;false receiver
	
	public ClientSimulator(String host,int port,boolean type){
		this.host = host;
		this.port = port;
		this.type = true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Thread(new ClientSimulator("localhost",5001,true)).start();

	}
	@Override
	public void run() {
		Socket sock = null;
		try {
			sock = new Socket(host,port);
			while(true){
				//sender client
				if(type){
					String msg = "hello";
					sock.getOutputStream().write(msg.getBytes());
				}else{
					sock.getInputStream().read();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
