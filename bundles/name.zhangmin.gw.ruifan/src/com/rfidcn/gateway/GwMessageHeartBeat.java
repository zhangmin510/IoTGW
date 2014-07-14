package com.rfidcn.gateway;

public class GwMessageHeartBeat extends GwMessage
{
  public GwMessageHeartBeat()
  {
    this.data = new byte[2];
    this.data[0] = GwMessage.START;
    this.data[1] = GwMessage.END;
  }
}