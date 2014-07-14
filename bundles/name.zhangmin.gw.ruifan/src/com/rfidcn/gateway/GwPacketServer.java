package com.rfidcn.gateway;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import org.apache.log4j.Logger;

public abstract class GwPacketServer
  implements Runnable
{
  private static Logger logger = Logger.getLogger(GwPacketServer.class);
  protected ServerSocket server;
  protected int serverPort;
  protected int serverSocketLength = 128;

  public GwPacketServer(int port) {
    this.serverPort = port;
  }

  public void start() throws IOException {
    this.server = new ServerSocket(this.serverPort, this.serverSocketLength);
    new Thread(this).start();
  }

  public void stop() {
    try {
      this.server.close();
    } catch (IOException e) {
      logger.error(e);
    }
  }

  public void run()
  {
    OnServerStarted();

    Socket sock = null;
    try {
      while ((sock = this.server.accept()) != null)
      {
        OnConnectionEstablished(sock);
      }
    }
    catch (SocketException e) {
      logger.debug(e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error(e);
    }

    OnServerStopped();
  }

  public abstract void OnConnectionEstablished(Socket paramSocket);

  public abstract void OnServerStarted();

  public abstract void OnServerStopped();
}