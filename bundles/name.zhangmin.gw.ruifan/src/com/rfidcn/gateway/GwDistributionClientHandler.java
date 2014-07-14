package com.rfidcn.gateway;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import org.apache.log4j.Logger;

public class GwDistributionClientHandler extends GwClientHandler
{
  private static Logger logger = Logger.getLogger(GwDistributionClientHandler.class);

  public GwDistributionClientHandler(Socket client) {
    super(client);
  }

  public void OnClientIdle(Socket sock)
  {
    String host = sock.getInetAddress().getHostAddress() + "[" + sock.getPort() + "]";
    logger.info("OnClientIdle: " + host);
    //ConnectionManager.getInstance().removeDistributionClients(sock);
  }

  public void OnClientMessage(GwMessage msg)
  {
    logger.debug(dump(msg.getBytes(), msg.getBytes().length));
  }

  public void OnClientException(Socket sock, IOException e)
  {
    String host = sock.getInetAddress().getHostAddress() + "[" + sock.getPort() + "]";
    logger.debug("OnClientException: " + host + "\n" + e);
    ConnectionManager.getInstance().removeDistributionClients(sock);
  }
}