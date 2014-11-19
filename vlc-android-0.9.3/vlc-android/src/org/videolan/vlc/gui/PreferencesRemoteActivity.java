/*****************************************************************************
 * PreferencesActivity.java
 *****************************************************************************
 * Copyright © 2011-2014 VLC authors and VideoLAN
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/

package org.videolan.vlc.gui;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.LibVlcUtil;
import org.videolan.vlc.AudioService;
import org.videolan.vlc.AudioServiceController;
import org.videolan.vlc.BitmapCache;
import org.videolan.vlc.MediaDatabase;
import org.videolan.vlc.R;
import org.videolan.vlc.Util;
import org.videolan.vlc.VLCApplication;
import org.videolan.vlc.gui.audio.AudioUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.text.format.DateFormat;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class PreferencesRemoteActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

    public final static String TAG = "VLC/PreferencesRemoteActivity";

    public final static String NAME = "VlcSharedRemotePreferences";
    public final static String VIDEO_RESUME_TIME = "VideoResumeTime";
    public final static String VIDEO_SUBTITLE_FILES = "VideoSubtitleFiles";
    public final static int RESULT_RESCAN = RESULT_FIRST_USER + 1;
    public final static int RESULT_RESTART = RESULT_FIRST_USER + 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.remote_preferences);

    
        
        
        // Set host
        EditTextPreference setHostPref = (EditTextPreference) findPreference("set_remote_host");
        setHostPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            	 final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(PreferencesRemoteActivity.this);
                 SharedPreferences.Editor editor = sharedPrefs.edit();
                 try {
                     editor.putString("set_remote_host_value", (String)newValue);
                 } catch(NumberFormatException e) {                    
                     editor.putString("set_remote_host_value", "");
                 }
                 editor.commit();
                 return true;
            }
        });
        
        /*
        // Set port
        EditTextPreference setPortPref = (EditTextPreference) findPreference("set_rtsp_port");
        setPortPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            	 final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(PreferencesRemoteActivity.this);
                 SharedPreferences.Editor editor = sharedPrefs.edit();
                 try {
                     editor.putString("set_rtsp_port_value",(String)newValue);
                 } catch(NumberFormatException e) {                    
                     editor.putString("set_rtsp_port_value", "");
                 }
                 editor.commit();
                 return true;
            }
        });
        */
        
        
        
       

        /*** SharedPreferences Listener to apply changes ***/
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPrefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equalsIgnoreCase("set_rtsp_port")
                || key.equalsIgnoreCase("set_remote_host")) {
            Util.updateLibVlcSettings(sharedPreferences);
            LibVLC.restart(this);
        }
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference)
    {
        super.onPreferenceTreeClick(preferenceScreen, preference);
        if (preference!=null)
            if (preference instanceof PreferenceScreen)
                if (((PreferenceScreen)preference).getDialog()!=null)
                    ((PreferenceScreen)preference).getDialog().getWindow().getDecorView()
                    .setBackgroundDrawable(this.getWindow().getDecorView().getBackground()
                            .getConstantState().newDrawable());
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        AudioServiceController.getInstance().bindAudioService(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AudioServiceController.getInstance().unbindAudioService(this);
    }

    private void restartService(Context context) {
        Intent service = new Intent(context, AudioService.class);

        AudioServiceController.getInstance().unbindAudioService(PreferencesRemoteActivity.this);
        context.stopService(service);

        context.startService(service);
        AudioServiceController.getInstance().bindAudioService(PreferencesRemoteActivity.this);
    }
}
