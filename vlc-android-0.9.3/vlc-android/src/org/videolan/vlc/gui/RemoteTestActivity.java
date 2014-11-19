package org.videolan.vlc.gui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import org.shine.stb.remote.RemoteEngine;
import org.videolan.vlc.R;

public class RemoteTestActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remote_test_activity);
	}
	
	public void onRemoteUpClick(View v){
		onKeyEvent("19");
	}
	
	public void onRemoteDownClick(View v){
		onKeyEvent("20");
	}
	
	public void onRemoteLeftClick(View v){
		onKeyEvent("21");
	}
	
	public void onRemoteRightClick(View v){
		onKeyEvent("22");
	}
	
	public void onRemoteMenuClick(View v){
		onKeyEvent("82");
	}
	
	private void onKeyEvent(String keyCode){	    
        Toast.makeText(this, "KeyEvent : code = "+keyCode, 1).show();
        RemoteEventTask remoteEventTask  = new RemoteEventTask();
        remoteEventTask.execute(new String[]{String.valueOf(keyCode)});        
	}
	
	private class RemoteEventTask extends AsyncTask<String, Void, Integer>
	{
		protected Integer doInBackground(String[] paramArrayOfString)
		{
			// TODO Auto-generated method stub
			try
			{
				RemoteEngine remoteEngine = RemoteEngine.getInstance();				
				remoteEngine.sendRemoteEvent(paramArrayOfString[0]);
			}
			catch (Exception e)
			{
				return null;
			}
			return null;

		}
	}
}
