/***********************************************************************
* Author: Jason.Zhang
*
*
*
*********************************************************************************/


package org.shine.stb.remote;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class RemoteEngine {
	private static RemoteEngine instance;
	
	private static final String MOUSE_MOVE_CODE = "mousemove";
	private static final String MOUSE_LEFTCLICK_CODE = "mouseclick";
	private static final String MOUSE_EVENT_CHECK = "ACTION_MOVE";
	
	public static RemoteEngine getInstance()
	{
		if (instance == null)
			instance = new RemoteEngine();		
		return instance;
	}	

	public void sendRemoteEvent(String keyCode) {
		// TODO Auto-generated method stub
		String rtspPort = Protocol.getRtspPort();  
		sendRemoteEvent(keyCode,rtspPort);
	}
	
	public Boolean sendRemoteEvent(String keyCode, String rtspPort){
		Map<String, String> params = new HashMap<String, String>();
		Log.e("Remote","keyCode="+keyCode+" rtspPort="+rtspPort);
		if(Protocol.keyEventMap.containsKey(keyCode)){
			params.put("key", (String)Protocol.keyEventMap.get(keyCode));
			params.put("port", rtspPort);
			String path = Protocol.getKeyPostUrl();
			try
			{
				Log.e("Remote","path="+path);
				Boolean flag =  HttpConnection.sendGETRequest(path, params, "UTF-8");
				return flag;
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}	
		}
		return false;
		
	}

	public void sendRemoteEvent(String[] paramArrayOfString) {
		// TODO Auto-generated method stub
		String rtspPort = Protocol.getRtspPort();  
		Map<String, String> params = new HashMap<String, String>();	
		if(!paramArrayOfString[0].equals(MOUSE_EVENT_CHECK)){
			params.put("key", MOUSE_MOVE_CODE);
			params.put("port", rtspPort);
			params.put("mposx", paramArrayOfString[0]);
			params.put("mposy", paramArrayOfString[1]);
		}
		else{
			params.put("key", MOUSE_MOVE_CODE);
			params.put("mouseclick", MOUSE_LEFTCLICK_CODE);
			params.put("port", rtspPort);
			params.put("mposx", paramArrayOfString[0]);
			params.put("mposy", paramArrayOfString[1]);
		}

		String path = Protocol.getKeyPostUrl();
		try
		{
			Log.e("Remote","path="+path);
			Boolean flag =  HttpConnection.sendGETRequest(path, params, "UTF-8");			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}	
	}

}
