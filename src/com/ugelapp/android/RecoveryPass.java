package com.ugelapp.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RecoveryPass extends Activity implements OnClickListener {
	
	Button btnrecover;
	Button btncancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_recover);		
		btnrecover = (Button) findViewById(R.id.btnrecover);
		btncancel = (Button) findViewById(R.id.btncancel);
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		btnrecover.setOnClickListener(this);
		btncancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch(v.getId()){
			case R.id.btnrecover:
				intent = new Intent(this,Login.class);
				startActivity(intent);
				break;
			case R.id.btncancel:
				intent = new Intent(this,Login.class);
				startActivity(intent);
				break;
			
		}		
	}

}
