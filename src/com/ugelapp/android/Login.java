package com.ugelapp.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.backup.BackupManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ugelapp.android.data.AppPreferences;
import com.ugelapp.http.Json_Login;


public class Login extends Activity implements OnClickListener {
	
	Button btnrecover;
	Button btnlogin;
	EditText login;
	EditText pass;
	CheckBox ckrecordar;
	ProgressDialog progressDialog;
	AppPreferences preferencias;	

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnrecover = (Button) findViewById(R.id.btnrecover);
		btnlogin = (Button) findViewById(R.id.btnlogin);
		login = (EditText) findViewById(R.id.txtuser);
		pass = (EditText) findViewById(R.id.txtpass);
		ckrecordar = (CheckBox) findViewById(R.id.ckrecordar);
		
		preferencias = new AppPreferences(this);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		btnrecover.setOnClickListener(this);
		btnlogin.setOnClickListener(this);		
		
		if(!preferencias.getValue(getResources().getString(R.string.persona_id)).equals("")){
			//Log.i("login guardado", "ingreso");
			login();
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btnrecover:
				Intent intent = new Intent(this,RecoveryPass.class);
				startActivity(intent);
				break;
		case R.id.btnlogin:
				consultar(login.getText().toString(),pass.getText().toString(),this.getResources().getString(R.string.LOGIN_URL));
				break;										
		}		
	}
	
	private void login(){
		Intent intent = new Intent(this,Content.class);
		startActivity(intent);		
	}
	
	
	
	
	private void consultar(final String login,final String pass, final String url){
		
		progressDialog = new ProgressDialog(this);
	    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    progressDialog.setMessage("Loading...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
		
		Thread tr = new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Json_Login jlogin = new Json_Login(getApplicationContext(),url,login,pass);
				Message msg = new Message();
				msg.obj=jlogin.leer(); 
				enlace.sendMessage(msg);
				
			}
		});
		tr.start();
		 
	}
	
	private Handler enlace = new Handler(){
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub			
			String valor = (String) msg.obj;			
			
			if(!valor.equals("-1") && !valor.equals("-2")){
				progressDialog.dismiss();
				
				if(ckrecordar.isChecked()){
					preferencias.saveValue(getResources().getString(R.string.persona_id), valor);
				}
				
				login();
			}else if(valor.equals("-1")){
				progressDialog.dismiss();
				Toast.makeText(Login.this, getResources().getString(R.string.error_login), Toast.LENGTH_LONG).show();				
			}else if(valor.equals("-2")){
				progressDialog.dismiss();
				Toast.makeText(Login.this, getResources().getString(R.string.error_login_connection), Toast.LENGTH_LONG).show();
			}
			
		}		
	};
	
	
	   

}
