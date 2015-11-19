package com.ugelapp.android;

import com.ugelapp.android.data.AppPreferences;
import com.ugelapp.http.Consultar_Resolucion;
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
import android.widget.TextView;
import android.widget.Toast;

public class Resoluciones extends Activity  implements OnClickListener {
	
	EditText number;
	TextView estado;
	Button btn;
	ProgressDialog progressDialog;
	AppPreferences preferencias;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_resoluciones);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		
		number = (EditText) findViewById(R.id.resolucionNumber);
		estado = (TextView)  findViewById(R.id.txtresolucionestado);
		btn = (Button) findViewById(R.id.resolucionbtn);
		
		preferencias = new AppPreferences(this);		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		btn.setOnClickListener(this);		
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
			case R.id.resolucionbtn:
				consultar(number.getText().toString(),getResources().getString(R.string.RESOLUCIONES_URL));
				break;										
		}		
	}
	
	
	private void consultar(final String number, final String url){
		
		progressDialog = new ProgressDialog(this);
	    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    progressDialog.setMessage("Loading...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
		
		Thread tr = new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Consultar_Resolucion consulta =  new Consultar_Resolucion(url, number);				
				Message msg = new Message();
				msg.obj=consulta.leer(); 
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
				estado.setVisibility(View.VISIBLE);
				estado.setText("Recoger resolucion");
			}else if(valor.equals("0")){
				progressDialog.dismiss();
				estado.setVisibility(View.VISIBLE);
				estado.setText("Pendiente");				
			}else if(valor.equals("-1")){
				progressDialog.dismiss();
				Toast.makeText(Resoluciones.this, getResources().getString(R.string.vacio), Toast.LENGTH_LONG).show();
			}else if(valor.equals("-2")){
				progressDialog.dismiss();
				Toast.makeText(Resoluciones.this, getResources().getString(R.string.error_login_connection), Toast.LENGTH_LONG).show();
			}
			
		}		
	};
	
}
