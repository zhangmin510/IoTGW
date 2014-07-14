package com.rfidcn.gateway;

import com.rfidcn.gateway.udp.GwPacketServerUDP;
import java.io.IOException;

public class GatewayServer
{
  private ConnectionManager cm = ConnectionManager.getInstance();

  private GwPacketServerUDP feedServer = new GwPacketServerUDP(5001);
  private GwPacketServer distributionServer = new GwDistributionServer(5002);
  private GwPacketServer messageServer = new GwMessageServer(5003);

  public void start() {
    this.cm.start();
    try {
      this.feedServer.start();
      this.distributionServer.start();
      this.messageServer.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void stop() { this.messageServer.stop();
    this.distributionServer.stop();
    this.feedServer.stop();

    this.cm.shutdown();
  }
}