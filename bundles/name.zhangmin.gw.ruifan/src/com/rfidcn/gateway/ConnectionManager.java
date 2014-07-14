package com.rfidcn.gateway;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

public class ConnectionManager
  implements Runnable
{
  private static Logger logger = Logger.getLogger(ConnectionManager.class);
  private static ConnectionManager cm;
  private ExecutorService taskPool;
  private ScheduledExecutorService timer;
  private ScheduledFuture<?> timerHandle;
  private List<Socket> feedClients = new ArrayList();
  private List<Socket> distributionCliens = new ArrayList();
  private List<Socket> messageClients = new ArrayList();

  public void start()
  {
    this.taskPool = Executors.newCachedThreadPool();
    this.timer = Executors.newScheduledThreadPool(1);

    this.timerHandle = this.timer.scheduleAtFixedRate(this, 25000L, 25000L, TimeUnit.MILLISECONDS);
    logger.debug("timer started");
  }

  public void shutdown() {
    this.timerHandle.cancel(true);
    logger.debug("timer canceled");
    this.timer.shutdown();

    this.taskPool.shutdown();
    try
    {
      if (!this.taskPool.awaitTermination(5L, TimeUnit.SECONDS)) {
        this.taskPool.shutdownNow();

        if (!this.taskPool.awaitTermination(5L, TimeUnit.SECONDS))
          logger.warn("Pool did not terminate");
      }
    }
    catch (InterruptedException ie) {
      this.taskPool.shutdownNow();

      Thread.currentThread().interrupt();
    }
    logger.info("ConnectionManager shutdown complete.");
  }

  public static ConnectionManager getInstance() {
    if (cm == null) {
      cm = new ConnectionManager();
    }
    return cm;
  }

  public synchronized void addFeedClients(Socket client) {
    this.feedClients.add(client);
    logger.debug("feedClients count: " + this.feedClients.size());
    this.taskPool.execute(new GwFeedClientHandler(client));
  }

  public synchronized void removeFeedClients(Socket client) {
    this.feedClients.remove(client);
    try {
      client.close();
    } catch (IOException e) {
      logger.error(e);
    }
    logger.debug("feedClients count: " + this.feedClients.size());
  }

  public synchronized void closeAllFeedClients() {
    for (int i = this.feedClients.size(); i > 0; i--)
    {
      Socket c = (Socket)this.feedClients.get(i - 1);
      removeFeedClients(c);
    }
  }

  public synchronized void addDistributionClients(Socket client) { this.distributionCliens.add(client);
    logger.debug("distributionClients count: " + this.distributionCliens.size());
    this.taskPool.execute(new GwDistributionClientHandler(client)); }

  public synchronized void removeDistributionClients(Socket client)
  {
    this.distributionCliens.remove(client);
    try {
      client.close();
    } catch (IOException e) {
      logger.error(e);
    }
    logger.debug("distributionCliens count: " + this.distributionCliens.size());
  }

  public synchronized void broadcastToDistributionClients(GwMessage msg) {
    for (int i = this.distributionCliens.size(); i > 0; i--)
    {
      Socket c = (Socket)this.distributionCliens.get(i - 1);
      try {
        c.getOutputStream().write(msg.getBytes());
      } catch (IOException e) {
        removeDistributionClients(c);
        logger.warn(e);
      }
    }
  }

  public synchronized void closeAllDistributionClients() {
    for (int i = this.distributionCliens.size(); i > 0; i--)
    {
      Socket c = (Socket)this.distributionCliens.get(i - 1);
      removeDistributionClients(c);
    }
  }

  public synchronized void addMessageClients(Socket client) {
    this.messageClients.add(client);
    logger.debug("messageClients count: " + this.messageClients.size());
    this.taskPool.execute(new GwMessageClientHandler(client));
  }

  public synchronized void removeMessageClients(Socket client) {
    this.messageClients.remove(client);
    try {
      client.close();
    } catch (IOException e) {
      logger.error(e);
    }
    logger.debug("messageClients count: " + this.messageClients.size());
  }

  public synchronized void broadcastToMessageClients(GwMessage msg) {
    for (int i = this.messageClients.size(); i > 0; i--)
    {
      Socket c = (Socket)this.messageClients.get(i - 1);
      try {
        c.getOutputStream().write(msg.getBytes());
      } catch (IOException e) {
        removeMessageClients(c);
        logger.warn(e);
      }
    }
  }

  public synchronized void closeAllMessageClients() {
    for (int i = this.messageClients.size(); i > 0; i--)
    {
      Socket c = (Socket)this.messageClients.get(i - 1);
      removeMessageClients(c);
    }
  }

  public void run() {
    GwMessage heartBeat = new GwMessageHeartBeat();

    broadcastToDistributionClients(heartBeat);
    broadcastToMessageClients(heartBeat);
  }
}