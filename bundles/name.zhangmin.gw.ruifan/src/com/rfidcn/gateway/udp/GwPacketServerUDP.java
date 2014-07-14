package com.rfidcn.gateway.udp;

import com.rfidcn.gateway.ConnectionManager;
import com.rfidcn.gateway.GwMessage;
import com.rfidcn.gateway.StaffMessageDto;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;

import org.apache.log4j.Logger;

public class GwPacketServerUDP
  implements Runnable
{
  private static Logger logger = Logger.getLogger(GwPacketServerUDP.class);
  protected DatagramSocket server;
  protected int serverPort;

  public GwPacketServerUDP(int port)
  {
    this.serverPort = port;
  }

  public void start() throws IOException {
    this.server = new DatagramSocket(this.serverPort);
    new Thread(this).start();
  }

  public void stop() {
    try {
      this.server.close();
    } catch (Exception e) {
      logger.error(e);
    }
  }

  public void run()
  {
    OnServerStarted();
    while (true)
    {
      try
      {
        GwMessage msg = receiveMessage();
        OnClientMessage(msg);
      }
      catch (SocketException e)
      {
      }
      catch (IOException localIOException)
      {
      }

    }

    //OnServerStopped();
  }

  public void OnServerStarted() {
    logger.info("PacketServerUDP is started on port[" + this.serverPort + "].");
  }
  public void OnServerStopped() {
    logger.info("PacketServerUDP is stopped.");
  }

  private GwMessage receiveMessage() throws IOException {
    byte[] receiveByte = new byte[256];

    DatagramPacket receivePacket = new DatagramPacket(receiveByte, receiveByte.length);
    this.server.receive(receivePacket);
    int len = receivePacket.getLength();
    if (len == 0) {
      throw new IOException("no data available");
    }

    if ((receiveByte[0] == GwMessage.START) && (receiveByte[(len - 1)] == GwMessage.END)) {
      return new GwMessage(receiveByte, len);
    }
    throw new IOException("dropped error data packet");
  }

  protected String dump(byte[] buf, int length)
  {
    int bytesPerLine = 16;
    String result = "\n";

    String hexDisplay = "";
    String display = "";

    int i = 0;
    while (i < length) {
      byte b = buf[(i++)];
      hexDisplay = hexDisplay + toHex(b) + " ";
      if (isPrint(b))
        display = display + String.valueOf(b);
      else {
        display = display + '.';
      }

      if ((i == length) || ((i % bytesPerLine == 0) && (i != 0)))
      {
        if ((i == length) && (i % bytesPerLine != 0)) {
          for (int pad = length % bytesPerLine; pad < bytesPerLine; pad++) {
            hexDisplay = hexDisplay + "   ";
          }
        }
        result = result + hexDisplay + " - " + display + "\n";
        hexDisplay = "";
        display = "";
      }
    }

    result = result + "\n";
    return result;
  }

  private boolean isPrint(byte b) {
    return (b > 32) && (b < 126);
  }
  private String toHex(byte b) {
    return String.format("%02X", new Object[] { Byte.valueOf(b) });
  }

  public void OnClientMessage(GwMessage msg) {
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
}