/**
 *
 * ConnectionManager.java
 * ZhangMin.name - zhangmin@zhangmin.name
 * PositioningGW
 *
 */
package org.ciotc.gateway;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;


/**
 * @author ZhangMin.name
 *
 */
public class ConnectionManager implements Runnable {
	private static final Logger logger = 
			Logger.getLogger(ConnectionManager.class);
	private static ConnectionManager cm;
	private ExecutorService taskPool;
	private ScheduledExecutorService timer;
	private ScheduledFuture<?> timerHandler;
	
	private List<Socket> feedClients = new ArrayList<Socket>();
	private List<Socket> distributionClients = new ArrayList<Socket>();
	private List<Socket> messageClients = new ArrayList<Socket>();
	
	private ConnectionManager() { }
	public void start(){
		this.taskPool = Executors.newCachedThreadPool();
		this.timer = Executors.newScheduledThreadPool(1);
		this.timerHandler = this.timer.scheduleAtFixedRate(
				this, 25000L, 25000L, TimeUnit.MILLISECONDS);
		logger.debug("ConnectionManager Timer started");
	}
	public void shutdown(){
		this.timerHandler.cancel(true);
		logger.debug("ConnectionManager Timer canceled");
		this.timer.shutdown();
		this.taskPool.shutdown();
		
		try{
			if(!this.taskPool.awaitTermination(5L, TimeUnit.SECONDS)){
				this.taskPool.shutdownNow();
				if(!this.taskPool.awaitTermination(5L, TimeUnit.SECONDS)){
					logger.warn(
						"ConnectionManager TaskPool did not terminate");
				}
			}
		}catch (InterruptedException ie){
			this.taskPool.shutdownNow();
			Thread.currentThread().interrupt();
		}
		logger.info("ConnectionManager shutdown complete");
	}
	public HashMap<String,String> status(){
		HashMap<String,String> stat = new HashMap<String,String>();
		stat.put("feedClientNum", 
				new Integer(this.feedClients.size()).toString());
		stat.put("distClientNum", 
				new Integer(this.distributionClients.size()).toString());
		stat.put("messageClientNum", 
				new Integer(this.messageClients.size()).toString());
		return stat;
	}
	public static ConnectionManager getInstance() {
		if(cm == null){
			cm = new ConnectionManager();
		}
		return cm;
	}
	public synchronized void addFeedClients(Socket socket){
		this.feedClients.add(socket);
		logger.debug("FeedClients count : " + this.feedClients.size());
		this.taskPool.execute(new GWFeedClientHandler(socket));
	}
	public synchronized void removeFeedClients(Socket client){
		this.feedClients.remove(client);
		try{
			client.close();
		} catch(IOException e) {
			logger.error(e);
		}
		logger.debug("FeedClients count: " + this.feedClients.size());
	}
	public synchronized void closeAllFeedClients(){
		for(int i = 0;i < this.feedClients.size(); i ++){
			Socket s = this.feedClients.get(i);
			removeFeedClients(s);
		}
	}
	public synchronized void addDistributionClients(Socket client) { 
		this.distributionClients.add(client);
	    logger.debug("distributionClients count: " + this.distributionClients.size());
	    this.taskPool.execute(new GWDistributionClientHandler(client)); 
	}
	public synchronized void removeDistributionClients(Socket client) {
	    this.distributionClients.remove(client);
	    try {
	      client.close();
	    } catch (IOException e) {
	      logger.error(e);
	    }
	    logger.debug("distributionCliens count: " + this.distributionClients.size());
	}
	public synchronized void broadcastToDistributionClients(Message msg){
		for(int i = 0; i < this.distributionClients.size(); i ++){
			Socket s = this.distributionClients.get(i);
			try {
				s.getOutputStream().write(msg.getBytes());
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	public synchronized void closeAllDistributionClients(){
		for(int i = 0;i < this.distributionClients.size(); i ++){
			Socket s = this.distributionClients.get(i);
			removeDistributionClients(s);
		}
	}
	
	public synchronized void addMessageClients(Socket client) { 
		this.distributionClients.add(client);
	    logger.debug("distributionClients count: " + this.distributionClients.size());
	    this.taskPool.execute(new GWDistributionClientHandler(client)); 
	}
	public synchronized void removeMessageClients(Socket client) {
	    this.messageClients.remove(client);
	    try {
	      client.close();
	    } catch (IOException e) {
	      logger.error(e);
	    }
	    logger.debug("distributionCliens count: " + this.distributionClients.size());
	}
	public synchronized void broadcastToMessageClients(Message msg){
		for(int i = 0; i < this.messageClients.size(); i ++){
			Socket s = this.messageClients.get(i);
			try {
				s.getOutputStream().write(msg.getBytes());
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	public synchronized void closeAllMessageClients(){
		for(int i = 0;i < this.messageClients.size(); i ++){
			Socket s = this.messageClients.get(i);
			removeDistributionClients(s);
		}
	}
	@Override
	public void run() {
		Message heartBeat = new Message().heartBeat();
		this.broadcastToDistributionClients(heartBeat);
		this.broadcastToMessageClients(heartBeat);
	}

}
