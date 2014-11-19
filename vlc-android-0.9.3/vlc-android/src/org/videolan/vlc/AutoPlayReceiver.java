package org.videolan.vlc;
import org.shine.stb.remote.Protocol;
import org.videolan.vlc.gui.MainActivity;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AutoPlayReceiver extends BroadcastReceiver {
	
	private static final String TAG = "AutoPlayReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		String rtspurl = intent.getStringExtra("rtspurl");
		Log.i(TAG, "remote rtspurl="+rtspurl);
		
       VLCCallbackTask task = new VLCCallbackTask(context,rtspurl)
       {
           @Override
           public void run() {
        	 String rtspUrl = this.getRtspUrl();
             AudioServiceController c = AudioServiceController.getInstance();
             Protocol.setRtspUrl(rtspUrl);  
             c.load(rtspUrl, false);
           }
       };
       task.execute();
	}

}