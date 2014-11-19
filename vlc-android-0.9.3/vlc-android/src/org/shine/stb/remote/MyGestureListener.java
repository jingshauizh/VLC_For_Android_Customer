package org.shine.stb.remote;

import org.videolan.vlc.gui.video.VideoPlayerActivity;

import android.content.Context;
import android.util.Log;  
import android.view.GestureDetector.SimpleOnGestureListener;  
import android.view.MotionEvent;  
import android.widget.Toast;
  
public class MyGestureListener extends SimpleOnGestureListener {  
      
    private static final String TAG = "MyGestureListener";  
    private Context context = null;
    public MyGestureListener(Context inputcontext) {  
    	context = inputcontext;
    }  
  
    /** 
     * 双击的第二下Touch down时触发  
     * @param e 
     * @return 
     */  
    @Override  
    public boolean onDoubleTap(MotionEvent e) {  
        Log.i(TAG, "onDoubleTap : " + e.getAction());
        Toast.makeText(context, "onDoubleTap : " + e.getAction(), 1).show();
        return super.onDoubleTap(e);  
    }  
  
    /** 
     * 双击的第二下 down和up都会触发，可用e.getAction()区分。 
     * @param e 
     * @return 
     */  
    @Override  
    public boolean onDoubleTapEvent(MotionEvent e) {  
        Log.i(TAG, "onDoubleTapEvent : " + e.getAction());  
        Toast.makeText(context, "onDoubleTapEvent : " + e.getAction(), 1).show();
        return super.onDoubleTapEvent(e);  
    }  
  
    /** 
     * down时触发  
     * @param e 
     * @return 
     */  
    @Override  
    public boolean onDown(MotionEvent e) {  
        Log.i(TAG, "onDown : " + e.getAction());  
        Toast.makeText(context, "onDown : " + e.getAction(), 1).show();
        return super.onDown(e);  
    }  
  
    /** 
     * Touch了滑动一点距离后，up时触发。 
     * @param e1 
     * @param e2 
     * @param velocityX 
     * @param velocityY 
     * @return 
     */  
    @Override  
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
            float velocityY) {  
        Log.i(TAG, "onFling e1 : " + e1.getAction() + ", e2 : " + e2.getAction() + ", distanceX : " + velocityX + ", distanceY : " + velocityY);
        Toast.makeText(context, "onFling e1 : " + e1.getAction() + ", e2 : " + e2.getAction() + ", distanceX : " + velocityX + ", distanceY : " + velocityY, 1).show();
        return super.onFling(e1, e2, velocityX, velocityY);  
    }  
  
    /** 
     * Touch了不移动一直 down时触发  
     * @param e 
     */  
    @Override  
    public void onLongPress(MotionEvent e) {  
        Log.i(TAG, "onLongPress : " + e.getAction());  
        Toast.makeText(context, "onLongPress : " + e.getAction(), 1).show();
        super.onLongPress(e);  
    }  
  
    /** 
     * Touch了滑动时触发。  
     * @param e1 
     * @param e2 
     * @param distanceX 
     * @param distanceY 
     * @return 
     */  
    @Override  
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,  
            float distanceY) {  
        Log.i(TAG, "onScroll e1 : " + e1.getAction() + ", e2 : " + e2.getAction() + ", distanceX : " + distanceX + ", distanceY : " + distanceY);  
        Toast.makeText(context, "onScroll e1 : " + e1.getAction() + ", e2 : " + e2.getAction() + ", distanceX : " + distanceX + ", distanceY : " + distanceY, 1).show();
        return super.onScroll(e1, e2, distanceX, distanceY);  
    }  
  
    /** 
     * Touch了还没有滑动时触发  
     * @param e 
     */  
    @Override  
    public void onShowPress(MotionEvent e) {  
        Log.i(TAG, "onShowPress : " + e.getAction()); 
        Toast.makeText(context, "onShowPress : " + e.getAction(), 1).show();
        super.onShowPress(e);  
    }  
  
    @Override  
    public boolean onSingleTapConfirmed(MotionEvent e) {  
        Log.i(TAG, "onSingleTapConfirmed : " + e.getAction());  
        Toast.makeText(context, "onSingleTapConfirmed : " + e.getAction(), 1).show();
        return super.onSingleTapConfirmed(e);  
    }  
  
    @Override  
    public boolean onSingleTapUp(MotionEvent e) {  
        Log.i(TAG, "onSingleTapUp : " + e.getAction()); 
        Toast.makeText(context, "onSingleTapUp : " + e.getAction(), 1).show();
        return super.onSingleTapUp(e);  
    }  
}  