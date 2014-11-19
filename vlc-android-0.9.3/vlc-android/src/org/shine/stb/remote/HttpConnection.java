/***********************************************************************
* Author: Jason.Zhang
*
*
*
*********************************************************************************/


package org.shine.stb.remote;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class HttpConnection {

	/**
	 * ±£´æÊý¾Ý
	 * @param title ±êÌâ
	 * @param length Ê±³¤
	 * @return
	 */
	public static boolean save(String title, String length) {
		String path = "http://192.168.0.168:8080/web/ManageServlet";
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", title);
		params.put("timelength", length);
		try {
			return sendHttpClientPOSTRequest(path, params, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Í¨¹ýHttpClient·¢ËÍPostÇëÇó
	 * @param path ÇëÇóÂ·¾¶
	 * @param params ÇëÇó²ÎÊý
	 * @param encoding ±àÂë
	 * @return ÇëÇóÊÇ·ñ³É¹¦
	 */
	private static boolean sendHttpClientPOSTRequest(String path, Map<String, String> params, String encoding) throws Exception{
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();//´æ·ÅÇëÇó²ÎÊý
		if(params!=null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()){
				pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, encoding);
		HttpPost httpPost = new HttpPost(path);
		httpPost.setEntity(entity);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(httpPost);
		if(response.getStatusLine().getStatusCode() == 200){
			return true;
		}
		return false;
	}
	/**
	 * ·¢ËÍPostÇëÇó
	 * @param path ÇëÇóÂ·¾¶
	 * @param params ÇëÇó²ÎÊý
	 * @param encoding ±àÂë
	 * @return ÇëÇóÊÇ·ñ³É¹¦
	 */
	private static boolean sendPOSTRequest(String path, Map<String, String> params, String encoding) throws Exception{
		//  title=liming&timelength=90
		StringBuilder data = new StringBuilder();
		if(params!=null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()){
				data.append(entry.getKey()).append("=");
				data.append(URLEncoder.encode(entry.getValue(), encoding));
				data.append("&");
			}
			data.deleteCharAt(data.length() - 1);
		}
		byte[] entity = data.toString().getBytes();//Éú³ÉÊµÌåÊý¾Ý
		HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);//ÔÊÐí¶ÔÍâÊä³öÊý¾Ý
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(entity);
		if(conn.getResponseCode() == 200){
			return true;
		}
		return false;
	}
	/**
	 * ·¢ËÍGETÇëÇó
	 * @param path ÇëÇóÂ·¾¶
	 * @param params ÇëÇó²ÎÊý
	 * @param encoding ±àÂë
	 * @return ÇëÇóÊÇ·ñ³É¹¦
	 */
	public static boolean sendGETRequest(String path, Map<String, String> params, String ecoding) throws Exception{
		// http://192.168.1.100:8080/web/ManageServlet?title=xxx&timelength=90
		StringBuilder url = new StringBuilder(path);
		url.append("?");
		for(Map.Entry<String, String> entry : params.entrySet()){
			url.append(entry.getKey()).append("=");
			url.append(URLEncoder.encode(entry.getValue(), ecoding));
			url.append("&");
		}
		url.deleteCharAt(url.length() - 1);
		Log.e("Remote","sendGETRequest url="+url);
		HttpURLConnection conn = (HttpURLConnection)new URL(url.toString()).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode() == 200){
			return true;
		}
		return false;
	}
	
	

}


