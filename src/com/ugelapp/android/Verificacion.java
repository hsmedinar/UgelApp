package com.ugelapp.android;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.ugelapp.android.data.AdapterVerificacion;
import com.ugelapp.android.data.Tramite;
import com.ugelapp.http.getEstadoTramite;

public class Verificacion extends Activity {
	
	ListView lista;
	
	String[] tramites;
	ProgressDialog progressDialog;
	AdapterVerificacion ada;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_verificacion);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		
		lista = (ListView) findViewById(R.id.listaTramiteStatus);
		tramites = getResources().getStringArray(R.array.name_tramite);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		ConnectivityManager con = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
		NetworkInfo net = con.getActiveNetworkInfo();
		if(net!=null && net.isConnected()){
			consultar(getResources().getString(R.string.ESTADO_TRAMITE_URL));	
		}else{
			Toast.makeText(Verificacion.this, getResources().getString(R.string.error_login_connection), Toast.LENGTH_LONG).show();
		}
				
		
	}
	
	
	private void consultar(final String url){
		
		//progressDialog = new ProgressDialog(this);
	    //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    //progressDialog.setMessage("Loading...");
	    //progressDialog.setCancelable(false);
	    //progressDialog.show();
		
		Thread tr = new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getEstadoTramite tramites = new getEstadoTramite(getApplicationContext(),url);
				ArrayList<Tramite> t = tramites.leer();
				AdapterVerificacion adapter = new AdapterVerificacion(Verificacion.this,t);
				Message msg = new Message();
				msg.obj=adapter; 
				enlace.sendMessage(msg);
				
			}
		});
		tr.start();
		 
	}
	
	private Handler enlace = new Handler(){
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub			
			if(msg.obj!=null){
				//progress.dismiss();				
			}
			
			ada = (AdapterVerificacion)  msg.obj;
			lista.setAdapter(ada);	
			
		}		
	};
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
	    	case android.R.id.home:
	    		Intent i = new Intent(this,Content.class);	        
	    		startActivity(i);
	    		return true;
	    	default: return super.onOptionsItemSelected(item);  
	    }
	}	

}
