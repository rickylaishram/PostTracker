package com.rickylaishram.posttracker;

import java.util.Vector;

import com.rickylaishram.posttrack.dataclass.ItemClass;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

public class AdapterItemsList extends ArrayAdapter<ItemClass>{
	
	Context context; 
    int layoutResourceId;    
    Vector<ItemClass> data;
    Holder holder;
	
	public AdapterItemsList(Context context, int layoutResourceId, Vector<ItemClass> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId 	= layoutResourceId;
        this.context 			= context;
        this.data 				= data;
    }
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        holder = new Holder();
        
        if(row == null) {
	        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	        row = inflater.inflate(layoutResourceId, parent, false);
	        
	        holder.name 	= (TextView) row.findViewById(R.id.name);
	        holder.code		= (TextView) row.findViewById(R.id.code);
	        holder.status	= (FrameLayout) row.findViewById(R.id.status);
	        
	        row.setTag(holder);
        } else {
        	holder = (Holder) convertView.getTag();
        }
        
        final ItemClass item = data.elementAt(position);
        holder.name.setText(item.name);
        holder.code.setText(item.id);
        
        if(item.confimed == 0) {
        	holder.status.setBackgroundResource(android.R.color.holo_red_light);
        } else {
        	if(item.new_exist == 1) {
        		holder.status.setBackgroundResource(android.R.color.holo_green_light);
        	} else {
        		holder.status.setBackgroundResource(android.R.color.holo_blue_bright);
        	}
        }
        
		return row;
	}
	
	
	
	static class Holder {
		public FrameLayout status;
        public TextView name;
		public TextView code;
    }
}
