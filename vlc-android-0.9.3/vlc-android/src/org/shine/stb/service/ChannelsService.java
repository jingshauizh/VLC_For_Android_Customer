package org.shine.stb.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.shine.stb.model.Channel;
public class ChannelsService {
	private static String configPath;
	private static RequestQueue mQueue ; 
	
	
	
	/**
	 * 获取最新的视频资讯
	 * @return
	 * @throws Exception
	 */
	public static List<Channel> getJSONLastChannelsV(Response.Listener<JSONArray> listener) throws Exception{
		String path = ChannelsService.getConfigPath();
		
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(path,
				//listener.
        new Response.Listener<JSONArray>() {  
            @Override  
            public void onResponse(JSONArray response) {  
                Log.d("TAG", response.toString());
                List<Channel> channelList = new ArrayList<Channel>();        		
        		JSONArray array = response;
        		for(int i = 0 ; i < array.length() ; i++){
        			JSONObject jsonObject;
					try {
						jsonObject = array.getJSONObject(i);
						Channel channel = new Channel(
	        					jsonObject.getString("image"), 
	        					jsonObject.getString("title"), 
	        					jsonObject.getString("info"),
	        					jsonObject.getString("detail"),
	        					jsonObject.getString("rtspurl"));
	        			channelList.add(channel);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			
        		}
        		//return channelList;
            }  
        }, new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
                Log.e("TAG", error.getMessage(), error);  
            }  
        });
		getmQueue().add(jsonArrayRequest);
		return null;
	}

	/**
	 * 获取最新的视频资讯
	 * @return
	 * @throws Exception
	 */
	public static List<Channel> getJSONLastChannels() throws Exception{
		String path = ChannelsService.getConfigPath();
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode() == 200){
			InputStream inStream = conn.getInputStream();
			return parseJSON(inStream);
		}
		return null;
	}
	/**
	 * 解析JSON数据
	 * @param inStream
	 * @return
	 */
	private static List<Channel> parseJSON(InputStream inStream) throws Exception{
		List<Channel> channelList = new ArrayList<Channel>();
		byte[] data = StreamTool.read(inStream);
		String json = new String(data);
		JSONArray array = new JSONArray(json);
		for(int i = 0 ; i < array.length() ; i++){
			JSONObject jsonObject = array.getJSONObject(i);
			Channel channel = new Channel(
					jsonObject.getString("image"), 
					jsonObject.getString("title"), 
					jsonObject.getString("info"),
					jsonObject.getString("detail"),
					jsonObject.getString("rtspurl"));
			channelList.add(channel);
		}
		return channelList;
	}
	public static String getConfigPath() {
		return configPath;
	}
	public static void setConfigPath(String configPath) {
		ChannelsService.configPath = configPath;
	}

	public static RequestQueue getmQueue() {
		return mQueue;
	}

	public static void setmQueue(RequestQueue mQueue) {
		ChannelsService.mQueue = mQueue;
	}

	
}
