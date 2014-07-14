package com.rfidcn.gateway;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class GwMessage
{
  public static byte START = 2;
  public static byte END = 3;
  public static byte TRANSFORM = 4;

  public static byte MT_EMPTY = 1;
  public static byte MT_LOCATION = 5;
  public static byte MT_CARDINFO = 6;
  public static byte MT_READERINFO = 7;
  public static byte MT_ACTIVATORINFO = 8;
  public static byte MT_RESERVE09 = 9;
  public static byte MT_RESERVE0A = 10;
  public static byte MT_RESERVE0B = 11;
  public static byte MT_RESERVE0C = 12;
  public static byte MT_RESERVE0D = 13;
  public static byte MT_RESERVE0E = 14;
  public static byte MT_RESERVE0F = 15;
  public static byte MT_CUSTOM = 16;
  public static byte MT_UNKNOWN = -1;
  protected byte[] data;
  SimpleDateFormat dateFormat = new SimpleDateFormat("yy,MM,dd,HH,mm,ss,SSSS");

  protected GwMessage()
  {
  }

  public GwMessage(byte[] buf, int length) {
    this.data = new byte[length];
    for (int i = 0; i < length; i++)
      this.data[i] = buf[i];
  }

  public void mergeTime()
  {
    byte[] odata = new byte[8];
    String strDate = this.dateFormat.format(new Date());
    String[] dates = strDate.split(",");
    int i = 0;
    int v;
    for (String f : dates) {
      v = Integer.parseInt(f);
      if (v > 255) {
        odata[i] = ((byte)(v & 0xFF));
        v >>= 8;
        i++;
      }
      odata[i] = ((byte)v);
      i++;
    }
    byte[] ndata = new byte[8];

    i = 0;
    for (byte b : odata) {
      if (b == 2) {
        byte[] tmp = new byte[i + 2];
        System.arraycopy(ndata, 0, tmp, 0, i);
        tmp[i] = 4;
        tmp[(i + 1)] = 5;
        ndata = new byte[ndata.length + 1];
        System.arraycopy(tmp, 0, ndata, 0, tmp.length);
        i += 2;
      }
      else if (b == 3) {
        byte[] tmp = new byte[i + 2];
        System.arraycopy(ndata, 0, tmp, 0, i);
        tmp[i] = 4;
        tmp[(i + 1)] = 6;
        ndata = new byte[ndata.length + 1];
        System.arraycopy(tmp, 0, ndata, 0, tmp.length);
        i += 2;
      }
      else if (b == 4) {
        byte[] tmp = new byte[i + 2];
        System.arraycopy(ndata, 0, tmp, 0, i);
        tmp[i] = 4;
        tmp[(i + 1)] = 4;
        ndata = new byte[ndata.length + 1];
        System.arraycopy(tmp, 0, ndata, 0, tmp.length);
        i += 2;
      }
      else {
        ndata[i] = b;
        i++;
      }
    }
    byte[] tmp = new byte[this.data.length];
    System.arraycopy(this.data, 0, tmp, 0, this.data.length);
    this.data = new byte[this.data.length - 8 + ndata.length];
    System.arraycopy(tmp, 0, this.data, 0, tmp.length - 9);
    System.arraycopy(ndata, 0, this.data, tmp.length - 9, ndata.length);
    this.data[(this.data.length - 1)] = 3;
  }
  public static StaffMessageDto parsePacket(byte[] oData){
		StaffMessageDto smd = new StaffMessageDto();
		if(2 == oData.length){
			return null;
		}else{
			//去掉包头包尾
			byte[] tmp = new byte[oData.length - 2];
			System.arraycopy(oData, 1, tmp, 0, tmp.length);
			byte[] temp = unEscape(tmp);
			//debug
			//System.out.println("Parsing data:" + dump(temp));
			if(temp[0] == GwMessage.MT_LOCATION){
				byte[] id = new byte[4];
				byte[] time = new byte[8];
				int j = 0;
				int t = 0;
				//读取定位器卡号，天线号，基站号
				for(int i = 1;i < temp.length;i ++){
					if(i < 13){
						id[j % 4] = temp[i];
						if(j == 3){
							//卡信息
							smd.setCardID(bytesToInteger(id).toString());
						}else if(j == 7){
							//阅读器（通讯基站）
							smd.setBaseID(bytesToInteger(id).toString());
						}else if(j == 11){
							//激活器号（定位基站）最高字节表示RSSI值，其余3字节表示激活器号码
							byte[] realID = new byte[4];
							realID[3] = 0;
							System.arraycopy(id, 0, realID, 0, 3);
							smd.setAntennID(bytesToInteger(realID).toString());
						}else{ }
						j ++;
					}else{
						time[t ++] = temp[i];
					}
				}
				smd.setTime(bytesToTimeString(time));
			}else{ }
		}
		return smd;
	}
  public static final int bigEdianToLittleEdian(int i){
		return (i & 0xFF) << 24
		                | (0xFF & i >> 8) << 16
		                | (0xFF & i >> 16) << 8
		                | (0xFF & i >> 24);   
	}
	public static Integer bytesToInteger(byte[] b){
		
		byte[] a = new byte[4];
		//Big Edian
		int i = a.length - 1,j = b.length - 1;
		for (; i >= 0 ; i--,j--) {//从b的尾部(即int值的低位)开始copy数据
			if(j >= 0)
				a[i] = b[j];
			else
				a[i] = 0;//如果b.length不足4,则将高位补0
		}
		//Little Edian
//		int j = 0;
//		for(int i = 0; i < a.length;i++,j++){
//			if(j < 4){
//				a[i] = b[j];
//			}else{
//				a[i] = 0;//不足四位补0
//			}
//		}
		//&0xff将byte值无差异转成int,避免Java自动类型提升后,会保留高位的符号位
		int v0 = (a[0] & 0xff) << 24;
		int v1 = (a[1] & 0xff) << 16;
		int v2 = (a[2] & 0xff) << 8;
		int v3 = (a[3] & 0xff) ;
		int ret = v0 + v1 + v2 + v3;
		
		//debug 
	    //System.out.println("bytesToInteger:" + ret);
	    //System.out.println("bytesToInteger:" + dump(b));
		return bigEdianToLittleEdian(ret);
	}
	/**
	 * convert 8 bytes to time string
	 * Format:yyyy-MM-dd HH:mm:ss
	 * @param b
	 * @return
	 */
	public static String bytesToTimeString(byte[] b){
		//StringBuilder sb = new StringBuilder();
		//Bytes order is wrong

		if(b.length == 8){
//			int year = (int)b[7];
//			sb.append(year+2000);
//			sb.append("-").append((int)b[6]).append("-").append((int)b[5])
//			.append(" ").append((int)b[4]).append(":").append((int)b[3])
//			.append(":").append((int)b[2]);	
			
			int year = (int)b[0];
			int month = (int)b[1] - 1;
			int day = (int)b[2];
			int hh = (int)b[3];
			int mm = (int)b[4];
			int ss = (int)b[5];
			Calendar c = Calendar.getInstance();
			c.set(year+2000,month,day,hh,mm,ss);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
//			sb.append(year+2000);
//			sb.append("-").append((int)b[1]).append("-").append((int)b[2])
//			.append(" ").append((int)b[3]).append(":").append((int)b[4])
//			.append(":").append((int)b[5]);
			//暂时精确到秒
//			byte[] millss = new byte[]{b[0],b[1]};
//			int mills = bytesToInteger(millss);
//			sb.append(" ").append(mills);
			return sdf.format(c.getTime());
		}else{
			return null;
		}
		
	
		//debug 
		//System.out.println("Timebytes:" + dump(b));
		//System.out.println("bytesToTimeString:" + sb.toString());
		
	}
	
	public static byte[] unEscape(byte[] oData){
		byte[] nData = new byte[oData.length];
		int i = 0;
		byte b;
		byte next;
		for(int j = 0;j < oData.length;j ++){
			b = oData[j];
			if(4 == b){
				if(j  == oData.length - 1){
					continue;
				}
				next = oData[j + 1];
				if(4 == next){
					nData[i] = oData[j];
					j ++;
				}else if(5 == next){
					nData[i] = 2;
					j ++;
				}else if(6 == next){
					nData[i] = 3;
					j ++;
				}else{
					nData[i] = oData[j];
				}
			}else {
				nData[i] = oData[j];
			}
			i ++;
		}
		byte[] ret = new byte[i];
		System.arraycopy(nData, 0, ret, 0, ret.length);
		//debug
		//System.out.println("unEscapebyte:" + dump(ret));
		return ret;
	}
  public boolean isCustom() {
    return getType() == MT_CUSTOM;
  }
  public boolean isLocation() {
    byte t = getType();
    return (t == MT_LOCATION) || (t == MT_CARDINFO);
  }
  public boolean isMessage() {
    byte t = getType();
    return (t == MT_CARDINFO) || (t == MT_READERINFO) || (t == MT_ACTIVATORINFO);
  }

  public byte getType() {
    if (this.data.length == 2) {
      return MT_EMPTY;
    }
    byte cmd = this.data[1];
    if ((cmd >= MT_LOCATION) && (cmd <= MT_CUSTOM)) {
      return cmd;
    }
    return MT_UNKNOWN;
  }

  public byte[] getBytes() {
    return this.data;
  }
}