package org.videolan.vlc.gui.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.shine.stb.model.Channel;
import org.shine.stb.remote.Protocol;

import org.shine.stb.service.ChannelsService;

import org.videolan.android.ui.SherlockGridFragment;
import org.videolan.vlc.AudioServiceController;
import org.videolan.vlc.VLCCallbackTask;
import org.videolan.vlc.interfaces.ISortable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import com.android.volley.RequestQueue;


public class ContentListFragmentV extends SherlockGridFragment implements ISortable{

	private ContentListAdapterV mVideoAdapter;
	private List<Map<String, Object>> listItems; 
	private RequestQueue mQueue ;  
	
	
    private String[] imgeIDs = {"http://192.168.2.104/iod/channels/game.png",    
    		"http://192.168.2.104/iod/channels/game.png", 
    		"http://192.168.2.104/iod/channels/game.png",   
           };   
    private String[] goodsNames = {"蛋糕", "礼物",
            "邮票"};
    private String[] goodsDetails = {
            "蛋糕：好好吃。",    
            "礼物：礼轻情重。",    
            "邮票：环游世界。"    
           }; 
	
	@Override
	public void sortBy(int sortby) {
		// TODO Auto-generated method stub
		
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mVideoAdapter = new ContentListAdapter(getActivity(), this);
        listItems = getListItems();
        mVideoAdapter = new ContentListAdapterV(getActivity(), listItems); //创建适配器   
        setListAdapter(mVideoAdapter);
        mQueue = Volley.newRequestQueue(getActivity());  
        updateFragmentList();
    }
    
    private void updateFragmentList(){    	       
         SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
         String remoteconfigPath = sharedPrefs.getString("set_remote_host_value", null);      
         
         String configPath =remoteconfigPath;
			JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(configPath,
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
	        		
	        		List<Channel> channels = channelList;
	        		List<Map<String, Object>> listItemsarr = new ArrayList<Map<String, Object>>();   
	 		        for(int i = 0; i < channels.size(); i++) {   
	 		            Map<String, Object> map = new HashMap<String, Object>();    
	 		            map.put("image", channels.get(i).getImagePath());               //图片资源   
	 		            map.put("title", channels.get(i).getTitle());              //物品标题   
	 		            map.put("info", channels.get(i).getInfo());     //物品名称   
	 		            map.put("detail", channels.get(i).getDetail()); //物品详情  
	 		            map.put("rtspurl", channels.get(i).getRtspurl()); //物品详情  
	 		            listItemsarr.add(map);
	 		        }      
	 		        listItems = listItemsarr; 
	 		        mVideoAdapter.setListItems(listItems);
	 				mVideoAdapter.notifyDataSetChanged();	
	            }  
	        }, new Response.ErrorListener() {  
	            @Override  
	            public void onErrorResponse(VolleyError error) {  
	                Log.e("TAG", error.getMessage(), error);  
	            }  
	        });
			mQueue.add(jsonArrayRequest);
    }
    
    @Override
    public void onResume() {
       Log.e("DEBUG", "onResume of Fragment");
       super.onResume();
       updateFragmentList();
    }
    
    @Override
    public void onGridItemClick(GridView l, View v, int position, long id) {
    	Log.e("", "position="+position);
    	showDetailInfo(position);
     
    }
    
    private void showDetailInfo(int clickID) {
    	String rtspurlstr = (String) listItems.get(clickID).get("rtspurl");
    	 VLCCallbackTask task = new VLCCallbackTask(getActivity(),rtspurlstr)
         {
             @Override
             public void run() {
               String rtspurl = this.getRtspUrl();
               AudioServiceController c = AudioServiceController.getInstance();
               String s = rtspurl; 
               Log.e("VLCCallbackTask", "rtspurl="+rtspurl);
               Protocol.setRtspUrl(s); 
               c.load(s, false);
             }
         };
         task.execute();
    }
   
    
    private List<Map<String, Object>> getListItems() {   
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();   
        for(int i = 0; i < goodsNames.length; i++) {   
            Map<String, Object> map = new HashMap<String, Object>();    
            map.put("image", imgeIDs[i]);               //图片资源   
            map.put("title", "name:");              //物品标题   
            map.put("info", goodsNames[i]);     //物品名称   
            map.put("detail", goodsDetails[i]); //物品详情  
            map.put("rtspurl", "rtsp://192.168.4.108:10564"); //物品详情  
            listItems.add(map);
        }      
        return listItems;   
    }   



	public void onContextPopupMenu(View v, int position) {
		// TODO Auto-generated method stub
		
	}
	
	private class RequestChannelsTask extends AsyncTask<String, Void, Integer>
	{
		@Override
		protected Integer doInBackground(String[] paramArrayOfString)
		{
			// TODO Auto-generated method stub
			try
			{
				String configPath = paramArrayOfString[0];
				ChannelsService.setConfigPath(configPath);
				ChannelsService.setmQueue(mQueue);
				List<Channel> channels = ChannelsService.getJSONLastChannels();
			    List<Map<String, Object>> listItemsarr = new ArrayList<Map<String, Object>>();   
		        for(int i = 0; i < channels.size(); i++) {   
		            Map<String, Object> map = new HashMap<String, Object>();    
		            map.put("image", channels.get(i).getImagePath());               //图片资源   
		            map.put("title", channels.get(i).getTitle());              //物品标题   
		            map.put("info", channels.get(i).getInfo());     //物品名称   
		            map.put("detail", channels.get(i).getDetail()); //物品详情  
		            map.put("rtspurl", channels.get(i).getRtspurl()); //物品详情  
		            listItemsarr.add(map);
		        }      
		        listItems = listItemsarr; 
		        return 1;
			}
			catch (Exception e)
			{
				return null;
			}
			

		}
		@Override
		protected void onPostExecute(Integer result) {			  
			mVideoAdapter.setListItems(listItems);
			mVideoAdapter.notifyDataSetChanged();		      
		}
	}




}
