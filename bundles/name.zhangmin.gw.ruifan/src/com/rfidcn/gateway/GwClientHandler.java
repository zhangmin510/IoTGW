package com.rfidcn.gateway;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.apache.log4j.Logger;

public abstract class GwClientHandler
  implements Runnable
{
  private static Logger logger = Logger.getLogger(GwClientHandler.class);
  protected Socket client;

  public GwClientHandler(Socket client)
  {
    this.client = client;
  }

  public void run()
  {
    while (true)
    {
    	GwMessage msg;
      logger.debug("waiting for a message ...");
      try
      {
        msg = receiveMessage();
      }
      catch (SocketTimeoutException e)
      {
        
        OnClientIdle(this.client);
        break;
      } catch (IOException e) {
        OnClientException(this.client, e);
        break;
      }
      
      OnClientMessage(msg);
    }
    logger.debug("client handler exit");
  }
  private GwMessage receiveMessage() throws IOException { byte[] buf = new byte[256];
    int pos = 0;
    boolean startFound = false;
    int data;
    do { if (pos == 256) {
        logger.debug("drop data " + pos + " bytes: \n" + dump(buf, pos));
        pos = 0;
      }

      data = this.client.getInputStream().read();

      if (data == -1) {
        throw new IOException("no data available");
      }

      if (data == GwMessage.START) {
        if (pos != 0) {
          logger.debug("drop data " + pos + " bytes: \n" + dump(buf, pos));
          pos = 0;
        }
        startFound = true;
      }

      buf[(pos++)] = ((byte)data);
    }
    while ((!startFound) || (data != GwMessage.END));

    return new GwMessage(buf, pos); }

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

  public abstract void OnClientIdle(Socket paramSocket);

  public abstract void OnClientMessage(GwMessage paramGwMessage);

  public abstract void OnClientException(Socket paramSocket, IOException paramIOException);
}