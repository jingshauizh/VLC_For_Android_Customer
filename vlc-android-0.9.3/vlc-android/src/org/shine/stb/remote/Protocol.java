/*************************************************************************************************
* @author Jason Zhang
* email: jingshuaizh@163.com
* blog:  http://blog.csdn.net/jingshuaizh
* 
* @date Oct 3, 2014
**************************************************************************************************/
package org.shine.stb.remote;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Protocol
{
  private static String keyPostUrl = "http://192.168.4.102:18080/keyevent.html";
  private static String rtspUrl = "rtsp://192.168.4.102:10564" ;
  private static String hostIp = "192.168.4.102";	
  private static String rtspPort = "10564";
  
  public static Map<String,String> keyEventMap;
  
  static {
	  keyEventMap = new HashMap<String,String>();
	  keyEventMap.put("19", "126");  //up
	  keyEventMap.put("20", "125");  //down
	  keyEventMap.put("21", "123");  //left
	  keyEventMap.put("22", "124");  //right
	  keyEventMap.put("82", "46");  //menu
	  keyEventMap.put("90", "123");  //ff
	  keyEventMap.put("89", "122");  //bb
	  keyEventMap.put("4", "31");  //pause
	  keyEventMap.put("19", "126");  //to begin
	  keyEventMap.put("19", "126");  //to end
	  
	  keyEventMap.put("66", "76");  //OK	  
  }
	public static String getRtspUrl() {
		return rtspUrl;
	}
	public static void setRtspUrl(String rtspUrl) {
		getHostIpAndPort(rtspUrl);
		Protocol.rtspUrl = rtspUrl;
	}
	public static String getHostIp() {
		return hostIp;
	}
	public static void setHostIp(String hostIp) {
		Protocol.hostIp = hostIp;
	}
	public static String getRtspPort() {
		return rtspPort;
	}
	public static void setRtspPort(String rtspPort) {
		Protocol.rtspPort = rtspPort;
	}	
	
	private static void getHostIpAndPort(String rtspUrl){
		String url = rtspUrl;		  
		Pattern p = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
		Matcher m = p.matcher(url);
		while(m.find()) {  
		  System.out.println("ip:"+m.group(1));  
		  setHostIp(m.group(1));
		  System.out.println("port:"+m.group(2));  
		  setRtspPort(m.group(2));
		} 
		StringBuilder postUrl = new StringBuilder("http://");
		postUrl.append(hostIp);
		postUrl.append(":18080/keyevent.html");
		keyPostUrl = postUrl.toString();
	}
	

	public static String getKeyPostUrl()
	{	
		return keyPostUrl;
	}
	
	
	
	public static void main(String [] args3){
		String url= "rtsp://192.168.4.102:10564";
		Protocol.setRtspUrl(url);
		
		 System.out.println("rtspUrl:"+Protocol.getRtspUrl());  
		 System.out.println("host ip :"+Protocol.getHostIp());  
		 System.out.println("port:"+Protocol.getRtspPort()); 
		 
		  if(Protocol.keyEventMap.containsKey("19")){
			  System.out.println("1111111");
		  }
		  else{
			  System.out.println("22222222222");
		  }
	}
}

