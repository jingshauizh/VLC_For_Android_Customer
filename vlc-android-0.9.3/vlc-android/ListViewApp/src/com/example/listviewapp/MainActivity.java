package com.example.listviewapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;   
import java.util.HashMap;   
import java.util.List;   
import java.util.Map;   
  
import android.app.Activity;   
import android.app.AlertDialog;   
import android.content.DialogInterface;   
import android.os.Bundle;   
import android.view.View;   
import android.view.View.OnClickListener;   
import android.widget.ArrayAdapter;   
import android.widget.ImageButton;   
import android.widget.ListView;   
  
public class MainActivity extends Activity {   
       
    private ListView listView;   
    private ImageButton imgbt_sum;   
    private ListViewAdapter listViewAdapter;   
    private List<Map<String, Object>> listItems;   
    private Integer[] imgeIDs = {R.drawable.cake,    
            R.drawable.gift, R.drawable.letter,   
            R.drawable.love, R.drawable.mouse,   
            R.drawable.music};   
    private String[] goodsNames = {"����", "����",    
            "��Ʊ", "����", "���", "����CD"};   
    private String[] goodsDetails = {   
            "���⣺�úóԡ�",    
            "����������ء�",    
            "��Ʊ���������硣",    
            "���ģ����綼�а���",   
            "��꣺��Ӧ���ݡ�",   
            "����CD���������֡�"};   
       
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState);   
        setContentView(R.layout.activity_main);   
           
        listView = (ListView)findViewById(R.id.list_goods);    
        imgbt_sum = (ImageButton) findViewById(R.id.imgbt_sum);   
        imgbt_sum.setOnClickListener(new ClickEvent());   
        listItems = getListItems();   
        listViewAdapter = new ListViewAdapter(this, listItems); //����������   
        listView.setAdapter(listViewAdapter);   
    }   
       
    /**  
     * ��ʼ����Ʒ��Ϣ  
     */  
    private List<Map<String, Object>> getListItems() {   
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();   
        for(int i = 0; i < goodsNames.length; i++) {   
            Map<String, Object> map = new HashMap<String, Object>();    
            map.put("image", imgeIDs[i]);               //ͼƬ��Դ   
            map.put("title", "��Ʒ���ƣ�");              //��Ʒ����   
            map.put("info", goodsNames[i]);     //��Ʒ����   
            map.put("detail", goodsDetails[i]); //��Ʒ����   
            listItems.add(map);   
        }      
        return listItems;   
    }   
       
    class ClickEvent implements OnClickListener{   
  
        @Override  
        public void onClick(View v) {   
            // TODO Auto-generated method stub   
            String goodsList = "";   
            for(int i = 0; i < listItems.size(); i++) {   
                goodsList += listViewAdapter.hasChecked(i)? goodsNames[i] + "  ": "";   
            }   
            new AlertDialog.Builder(MainActivity.this)   
            .setTitle("�����嵥��")   
            .setMessage("��ã���ѡ����������Ʒ��\n" + goodsList)   
            .setPositiveButton("ȷ��", null)   
            .show();   
        }   
           
    }   
}   