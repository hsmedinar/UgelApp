package com.ugelapp.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ugelapp.android.data.AlarmManagerBroadcastReceiver;

public class Content extends Activity implements OnClickListener{
	
	ImageButton btncronograma;
	ImageButton btntramite;
	ImageButton btnverificacion;
	ImageButton btninfo;
	ImageButton btnreclamos;
	ImageButton btnresoluciones;
	LinearLayout lycronograma;
	LinearLayout lytramite;
	LinearLayout lyverificacion;
	LinearLayout lyinfo;
	LinearLayout lyreclamos;
	LinearLayout lyresoluciones;
	
	private AlarmManagerBroadcastReceiver alarm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_content);
		
		final TextView txt1 = (TextView) findViewById(R.id.textCronograma);
		final TextView txt2 = (TextView) findViewById(R.id.textVerificacion);
		final TextView txt3 = (TextView) findViewById(R.id.textTramite);
		final TextView txt4 = (TextView) findViewById(R.id.textInfo);
		
		btncronograma = (ImageButton) findViewById(R.id.btncronograma);
		btntramite = (ImageButton) findViewById(R.id.btntramite);
		btnverificacion = (ImageButton) findViewById(R.id.btnverificacion);
		btninfo = (ImageButton) findViewById(R.id.btninfo);
		btnreclamos = (ImageButton) findViewById(R.id.btnreclamos);
		btnresoluciones = (ImageButton) findViewById(R.id.btnresoluciones);
		
		lycronograma = (LinearLayout) findViewById(R.id.lycronograma);
		lytramite = (LinearLayout) findViewById(R.id.lytramite);
		lyverificacion = (LinearLayout) findViewById(R.id.lyverificacion);
		lyinfo = (LinearLayout) findViewById(R.id.lyinfo);
		lyreclamos = (LinearLayout) findViewById(R.id.lyreclamos);
		lyresoluciones = (LinearLayout) findViewById(R.id.lyresoluciones);
		
		btntramite.setOnClickListener(this);
		btncronograma.setOnClickListener(this);
		btnverificacion.setOnClickListener(this);
		btninfo.setOnClickListener(this);
		lycronograma.setOnClickListener(this);
		lytramite.setOnClickListener(this);
		lyverificacion.setOnClickListener(this);
		lyinfo.setOnClickListener(this);
		lyreclamos.setOnClickListener(this);
		lyresoluciones.setOnClickListener(this);
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Bundle extras = getIntent().getExtras();
		
		if(extras!=null){
			  String mensaje= extras.getString("Texto_MEN");
				Toast toast1 = Toast.makeText(this,mensaje, Toast.LENGTH_SHORT);
	          	    toast1.show();	
		}
		  
	// ACTIVA LA ALARMA CADA CIERTO TIEMPO PARA UN MENSAJE
		alarm = new AlarmManagerBroadcastReceiver();
		alarm.SetAlarm(getApplicationContext());
		
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent; 
		switch(v.getId()){
			case R.id.btntramite:
				intent = new Intent(Content.this,Tramite.class);
				startActivity(intent);
				break;
			case R.id.lytramite:
				intent = new Intent(Content.this,Tramite.class);
				startActivity(intent);
				break;
			case R.id.btncronograma:
				intent = new Intent(Content.this,Cronograma.class);
				startActivity(intent);
				break;
			case R.id.lycronograma:
				intent = new Intent(Content.this,Cronograma.class);
				startActivity(intent);
				break;				
			case R.id.btnverificacion:
				intent = new Intent(Content.this,Verificacion.class);
				startActivity(intent);
				break;	
			case R.id.lyverificacion:
				intent = new Intent(Content.this,Verificacion.class);
				startActivity(intent);
				break;					
			case R.id.btninfo:
				intent = new Intent(Content.this,Info.class);
				startActivity(intent);
				break;				 		
			case R.id.lyinfo:
				intent = new Intent(Content.this,Info.class);
				startActivity(intent);
				break;
			case R.id.btnreclamos:
				intent = new Intent(Content.this,Reclamos.class);
				startActivity(intent);
				break;				 		
			case R.id.lyreclamos:
				intent = new Intent(Content.this,Reclamos.class);
				startActivity(intent);
				break;
			case R.id.btnresoluciones:
				intent = new Intent(Content.this,Resoluciones.class);
				startActivity(intent);
				break;				 		
			case R.id.lyresoluciones:
				intent = new Intent(Content.this,Resoluciones.class);
				startActivity(intent);
				break;
				
		}		
	}


}
