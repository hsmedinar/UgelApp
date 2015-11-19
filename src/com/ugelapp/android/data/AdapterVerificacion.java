package com.ugelapp.android.data;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ugelapp.android.R;

public class AdapterVerificacion extends BaseAdapter {
	
	Context context;
	ArrayList<Tramite> tramites;
	
	public AdapterVerificacion(Context context,ArrayList<Tramite> tramites){
		this.context=context;
		this.tramites=tramites;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tramites.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tramites.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
			Tramite t = (Tramite) tramites.get(position);
		
        	LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.row_list_verificacion, null);

            
            TextView nameTramite = (TextView) convertView.findViewById(R.id.nameTramite);
            TextView date = (TextView) convertView.findViewById(R.id.dateTramite);
            ImageView icon = (ImageView) convertView.findViewById(R.id.icon_state);  
            
            if(t.getEstado().equals("0"))
            	icon.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icon_ready));
            else
            	icon.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icon_wait));
            
            nameTramite.setText(t.getAsunto());            
            date.setText(convert(System.currentTimeMillis()));
            return convertView;
	}

	
	private String convert(long date){
		  
        //SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm.ss");  
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
        long valor = date;
        try {
        	 Date currentDate = new Date(valor);
        	 return sdf.format(currentDate);
        	
		} catch (Exception e) {			
			Log.i("converti a date", e.getMessage());			
		}
        
        return "";
	}


}
