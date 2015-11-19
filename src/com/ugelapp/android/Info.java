package com.ugelapp.android;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class Info extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_info);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
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

}
