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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ugelapp.android.data.AppPreferences;
import com.ugelapp.http.Registrar_Tramite;
import com.ugelapp.http.getCategoria;

public class Tramite extends Activity implements OnClickListener {
	
	
	ProgressDialog progressDialog;
	AppPreferences preferencias;
	Spinner categoria;
	EditText asunto;
	EditText descripcion;
	String dni;
	String id;
	Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tramite);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		
		categoria  =(Spinner) findViewById(R.id.sp_categoria);
		asunto =(EditText) findViewById(R.id.t_asunto);
		descripcion=(EditText) findViewById(R.id.t_descripcion);
		btn = (Button) findViewById(R.id.btntramite);
		
		preferencias = new AppPreferences(this);
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		btn.setOnClickListener(this);
		ConnectivityManager con = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
		NetworkInfo net = con.getActiveNetworkInfo();
		if(net!=null && net.isConnected()){
			llenaCategoria(getResources().getString(R.string.CATEGORIA_URL));	
		}else{
			Toast.makeText(Tramite.this, getResources().getString(R.string.error_login_connection), Toast.LENGTH_LONG).show();
		}
		
		id = preferencias.getValue(getResources().getString(R.string.persona_id));
		dni = preferencias.getValue(getResources().getString(R.string.persona_dni));
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.btntramite:
				registrar(id,categoria.getSelectedItem().toString(),dni,asunto.getText().toString(),descripcion.getText().toString(),getResources().getString(R.string.TRAMITE_URL));
				break;										
		}		
	}
	
	private void llenaCategoria(final String url){
		
		progressDialog = new ProgressDialog(this);
	    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    progressDialog.setMessage("Loading...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
		
		Thread tr = new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub				
				getCategoria getcat =  new getCategoria(url);				
				Message msg = new Message();
				msg.obj=getcat.leer(); 
				enlace.sendMessage(msg);
				
			}
		});
		tr.start();
		 
	}
	
	private Handler enlace = new Handler(){
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			ArrayList<String> valor = (ArrayList<String>) msg.obj;			
		
			progressDialog.dismiss();
			
			if(!valor.equals(null)){					
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(Tramite.this,android.R.layout.simple_list_item_1,valor);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				categoria.setAdapter(adapter);
			}
			
		}		
	};
	
	
	private void registrar(final String id,final String cat,final String dni,final String asunto,final String descripcion , final String url){
		
		progressDialog = new ProgressDialog(this);
	    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    progressDialog.setMessage("Loading...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
		
		Thread tr = new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Registrar_Tramite registra =  new Registrar_Tramite(url, id, cat, dni, asunto, descripcion);				
				Message msg = new Message();
				msg.obj=registra.leer(); 
				result_registro.sendMessage(msg);
				
			}
		});
		tr.start();
		 
	}
	
	private Handler result_registro = new Handler(){
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub			
			String valor = (String) msg.obj;			
			
			if(valor.equals("1")){
				progressDialog.dismiss();				
				
			}else if(valor.equals("-2")){
				progressDialog.dismiss();
				Toast.makeText(Tramite.this, getResources().getString(R.string.error_login_connection), Toast.LENGTH_LONG).show();
			}
			
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
