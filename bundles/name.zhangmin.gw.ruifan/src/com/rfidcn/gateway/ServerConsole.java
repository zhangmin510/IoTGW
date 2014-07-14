package com.rfidcn.gateway;

import com.rfidcn.gateway.udp.GwPacketServerUDP;

import java.io.Console;
import java.io.IOException;

public class ServerConsole
{
  public static void main(String[] args)
  {
    ConnectionManager.getInstance().start();

    GwPacketServerUDP feedServer = new GwPacketServerUDP(5001);
    GwPacketServer distributionServer = new GwDistributionServer(5002);
    GwPacketServer messageServer = new GwMessageServer(5003);

    Console c = System.console();

    c.printf("\n\nWelcome to Server Console! \n\n", new Object[0]);
    try {
        feedServer.start();
        distributionServer.start();
        messageServer.start();
        Thread.sleep(2000);
        c.printf("\n\nGateway started! \n\n", new Object[0]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    while (true)
    {
      String cmd = new String(c.readLine("$ ", new Object[0]));
      
      if (cmd.equals("start")) {
        try {
          feedServer.start();
          distributionServer.start();
          messageServer.start();
          Thread.sleep(2000);
          c.printf("\n\nGateway started! \n\n", new Object[0]);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else if (cmd.equals("stop")) {
        messageServer.stop();
        distributionServer.stop();
        feedServer.stop(); 
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        c.printf("\n\nGateway stopped! \n\n", new Object[0]);
      } else {
        if (cmd.equals("exit")) {
          break;
        }
        c.printf("Bad command. \n", new Object[0]);
      }
    }

    ConnectionManager.getInstance().shutdown();
  }
}