/*****************************************************************************
 * VideoListAdapter.java
 *****************************************************************************
 * Copyright © 2011-2012 VLC authors and VideoLAN
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

package org.videolan.vlc.gui.video;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.videolan.libvlc.Media;
import org.videolan.vlc.R;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import org.videolan.vlc.remote.BitmapCacheV;


import android.content.Context;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContentListAdapterV extends BaseAdapter implements Comparator<Media> {

	private Context context;                        //运行上下文   
    private List<Map<String, Object>> listItems;    //商品信息集合   
    private LayoutInflater listContainer;           //视图容器     
    public final class ListItemView{                //自定义控件集合     
            public ImageView image;     
            public TextView title;
            public RelativeLayout itemLayout;
            public TextView info;   
            public CheckBox check;   
            public ImageView detail;          
     } 
    
	private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    
    
       
       
    public ContentListAdapterV(Context context, List<Map<String, Object>> listItems) {   
        this.context = context;            
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文   
        this.setListItems(listItems);
        mQueue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(mQueue, new BitmapCacheV());
    }   
  
    public int getCount() {   
        // TODO Auto-generated method stub   
        return getListItems().size();   
    }   
  
    public Object getItem(int arg0) {   
        // TODO Auto-generated method stub   
        return getListItems().get(arg0);   
    }   
  
    public long getItemId(int arg0) {   
        // TODO Auto-generated method stub   
        return arg0;   
    }
          
    /**  
     * ListView Item设置  
     */  
    public View getView(int position, View convertView, ViewGroup parent) {   
    	
        // TODO Auto-generated method stub   
        Log.e("method", "getView");   
        final int selectID = position;   
        //自定义视图   
        final ListItemView  listItemView ;   
        if (convertView == null) {   
            listItemView = new ListItemView();    
            //获取list_item布局文件的视图   
            convertView = listContainer.inflate(R.layout.vstb_god_grid_item, null);   
            //获取控件对象   
            listItemView.image = (ImageView)convertView.findViewById(R.id.imageItem);   
            listItemView.title = (TextView)convertView.findViewById(R.id.titleItem);   
            listItemView.info = (TextView)convertView.findViewById(R.id.infoItem);   
            listItemView.detail= (ImageView)convertView.findViewById(R.id.detailItem);
            listItemView.detail.setAlpha(160); 
            listItemView.itemLayout = (RelativeLayout)convertView.findViewById(R.id.layout_item); 
              
            //设置控件集到convertView   
            convertView.setTag(listItemView);
        }else {   
            listItemView = (ListItemView)convertView.getTag();   
        }
        
        
        listItemView.title.setText((String) getListItems().get(position)   
                .get("title"));   
        listItemView.info.setText((String) getListItems().get(position).get("info")); 
        String imageUrl = (String) getListItems().get(position).get("image");
		ImageListener listener = ImageLoader.getImageListener(listItemView.image, android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);
        mImageLoader.get(imageUrl, listener);
		return convertView;
    }   
    
	@Override
	public int compare(Media lhs, Media rhs) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Map<String, Object>> getListItems() {
		return listItems;
	}

	public void setListItems(List<Map<String, Object>> listItems) {
		this.listItems = listItems;
	}
}

