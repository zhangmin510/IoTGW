package com.rfidcn.gateway;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import org.apache.log4j.Logger;

public class GwFeedClientHandler extends GwClientHandler
{
  private static Logger logger = Logger.getLogger(GwFeedClientHandler.class);

  public GwFeedClientHandler(Socket client) {
    super(client);
  }

  public void OnClientIdle(Socket sock)
  {
    String host = sock.getInetAddress().getHostAddress() + "[" + sock.getPort() + "]";
    logger.info("OnClientIdle: " + host);
    ConnectionManager.getInstance().removeFeedClients(sock);
  }

  public void OnClientMessage(GwMessage msg)
  {
    if (msg.isLocation())
    {
      if (msg.getType() == GwMessage.MT_LOCATION) {
        msg.mergeTime();
      }

      logger.debug(dump(msg.getBytes(), msg.getBytes().length));

      ConnectionManager.getInstance().broadcastToDistributionClients(msg);
    } else if (msg.isMessage()) {
      logger.debug(dump(msg.getBytes(), msg.getBytes().length));

      ConnectionManager.getInstance().broadcastToMessageClients(msg);
    } else {
      logger.debug("throw away : " + dump(msg.getBytes(), msg.getBytes().length));
    }
  }

  public void OnClientException(Socket sock, IOException e)
  {
    String host = sock.getInetAddress().getHostAddress() + "[" + sock.getPort() + "]";
    logger.debug("OnClientException: " + host + "\n" + e);
    ConnectionManager.getInstance().removeFeedClients(sock);
  }
}