package org.ciotc.gateway;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
	public static final byte START = 2;
	public static final byte END = 3;
	public static final byte TRANSFORM = 4;

	public static final byte MT_EMPTY = 1;
	public static final byte MT_LOCATION = 5;
	public static final byte MT_CARDINFO = 6;
	public static final byte MT_READERINFO = 7;
	public static final byte MT_ACTIVATORINFO = 8;
	public static final byte MT_RESERVE09 = 9;
	public static final byte MT_RESERVE0A = 10;
	public static final byte MT_RESERVE0B = 11;
	public static final byte MT_RESERVE0C = 12;
	public static final byte MT_RESERVE0D = 13;
	public static final byte MT_RESERVE0E = 14;
	public static final byte MT_RESERVE0F = 15;
	public static final byte MT_CUSTOM = 16;
	public static final byte MT_UNKNOWN = -1;
	
	private byte[] data;
	SimpleDateFormat sdf = 
			new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss,SSSS");
	public Message(){ 
		
	}
	public Message(byte[] data){
		this.data = data;
	}
	public Message(byte[] buf,int length){
		this.data = new byte[length];
		for(int i = 0; i < length; i ++){
			this.data[i] = buf[i];
		}
	}
	public void mergeTime(){
		byte[] odata = new byte[8];
		String strDate = this.sdf.format(new Date());
		String[] dates = strDate.split(",");
		int i = 0;
		int v;
		for(String f : dates){
			v = Integer.parseInt(f);
			if(v > 255){
				odata[i] = ((byte) (v & 0xFF));
				v >>= 8;
				i ++;
			}
			odata[i] = ((byte) v);
			i ++;
		}
		
		byte[] ndata = new byte[8];
		i = 0;
		for(byte b : odata){
			if(b == 2){
				byte[] tmp = new byte[i + 2];
				System.arraycopy(ndata, 0, tmp, 0, i);
				tmp[i] = 4;
				tmp[(i + 1)] = 5;
				ndata = new byte[ndata.length + 1];
				System.arraycopy(tmp, 0, ndata, 0, tmp.length);
				i += 2;
			} else if (b == 3) {
		        byte[] tmp = new byte[i + 2];
		        System.arraycopy(ndata, 0, tmp, 0, i);
		        tmp[i] = 4;
		        tmp[(i + 1)] = 6;
		        ndata = new byte[ndata.length + 1];
		        System.arraycopy(tmp, 0, ndata, 0, tmp.length);
		        i += 2;
		     } else if (b == 4) {
		        byte[] tmp = new byte[i + 2];
		        System.arraycopy(ndata, 0, tmp, 0, i);
		        tmp[i] = 4;
		        tmp[(i + 1)] = 4;
		        ndata = new byte[ndata.length + 1];
		        System.arraycopy(tmp, 0, ndata, 0, tmp.length);
		        i += 2;
		      } else {
		        ndata[i] = b;
		        i++;
		      }
		 }
		 byte[] tmp = new byte[this.data.length];
		 System.arraycopy(this.data, 0, tmp, 0, this.data.length);
		 this.data = new byte[this.data.length - 8 + ndata.length];
		 System.arraycopy(tmp, 0, this.data, 0, tmp.length - 9);
		 System.arraycopy(ndata, 0, 
				 this.data, tmp.length - 9, ndata.length);
		 this.data[(this.data.length - 1)] = 3;
	}
	public byte[] getBytes(){
		return this.data;
	}
	public int getLength(){
		return this.data.length;
	}
	public static String dump(byte[] buf, int length)
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

	  private static boolean isPrint(byte b) {
	    return (b > 32) && (b < 126);
	  }
	  private static String toHex(byte b) {
	    return String.format("%02X", new Object[] { Byte.valueOf(b) });
	  }
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < data.length; i ++){
			sb.append(Byte.toString(data[i]));
		}
		return sb.toString();
	}
	public byte getType(){
		if(this.data.length == 2){
			return MT_EMPTY;
		}
		byte cmd = this.data[1];
		if((cmd >= MT_LOCATION) && cmd <=MT_CUSTOM){
			return cmd;
		}
		return MT_UNKNOWN;
	}
	public boolean isCustom(){
		return getType() == MT_CUSTOM;
	}
	public boolean isLocation() {
	    byte t = getType();
	    return (t == MT_LOCATION) || (t == MT_CARDINFO);
	}
	public boolean isMessage() {
	    byte t = getType();
	    return (t == MT_CARDINFO) || (t == MT_READERINFO) 
	    		|| (t == MT_ACTIVATORINFO);
	}
	public Message heartBeat(){
		byte[] hb = new byte[2];
		hb[0] = (byte)2;
		hb[1] = (byte)3;
		return new Message(hb);
	}
	public static void main(String[] args) {
		

	}

}
