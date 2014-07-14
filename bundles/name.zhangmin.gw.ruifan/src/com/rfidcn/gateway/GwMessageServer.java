package com.rfidcn.gateway;

import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import org.apache.log4j.Logger;

public class GwMessageServer extends GwPacketServer
{
  private static Logger logger = Logger.getLogger(GwMessageServer.class);

  public GwMessageServer(int port) {
    super(port);
  }

  public void OnConnectionEstablished(Socket sock)
  {
    String host = sock.getInetAddress().getHostAddress() + "[" + sock.getPort() + "]";
    logger.info("Client: opening socket to " + host);
    try
    {
      sock.setSoTimeout(60000);

      ConnectionManager.getInstance().addMessageClients(sock);
    } catch (SocketException e) {
      logger.error(e);
    }
  }

  public void OnServerStarted()
  {
    logger.info("MessageServer is started on port[" + this.serverPort + "].");
  }

  public void OnServerStopped()
  {
    ConnectionManager.getInstance().closeAllMessageClients();
    logger.info("MessageServer is stopped.");
  }
}