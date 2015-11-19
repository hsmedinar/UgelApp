package com.ugelapp.android;

import com.ugelapp.android.data.AppPreferences;
import com.ugelapp.http.Json_Login;
import com.ugelapp.http.Register_Reclamos;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Reclamos extends Activity implements OnClickListener {
	
	String id;
	String dni;
	EditText asunto;
	EditText descripcion;
	Button btn;
	ProgressDialog progressDialog;
	AppPreferences preferencias;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reclamaciones);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		
		asunto = (EditText) findViewById(R.id.rasunto);
		descripcion = (EditText) findViewById(R.id.rdescripcion);
		btn = (Button) findViewById(R.id.rbtn);
		
		preferencias = new AppPreferences(this);		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		btn.setOnClickListener(this);
		id = preferencias.getValue(getResources().getString(R.string.persona_id));
		dni = preferencias.getValue(getResources().getString(R.string.persona_dni));		
		
	}
	
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.rbtn:
				registrar(id,dni,asunto.getText().toString(),descripcion.getText().toString(),getResources().getString(R.string.RECLAMOS_URL));
				break;										
		}		
	}
	
	
	private void registrar(final String id,final String dni,final String asunto,final String descripcion , final String url){
		
		progressDialog = new ProgressDialog(this);
	    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    progressDialog.setMessage("Loading...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
		
		Thread tr = new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Register_Reclamos registra =  new Register_Reclamos(url, id, dni, asunto, descripcion);				
				Message msg = new Message();
				msg.obj=registra.leer(); 
				enlace.sendMessage(msg);
				
			}
		});
		tr.start();
		 
	}
	
	private Handler enlace = new Handler(){
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub			
			String valor = (String) msg.obj;			
			
			if(valor.equals("1")){
				progressDialog.dismiss();				
				limpiar();
				
			}else if(valor.equals("-2")){
				progressDialog.dismiss();
				Toast.makeText(Reclamos.this, getResources().getString(R.string.error_login_connection), Toast.LENGTH_LONG).show();
			}
			
		}		
	};
	
	
	private void limpiar(){
		
		asunto.setText("");
		descripcion.setText("");
		
	}

}
