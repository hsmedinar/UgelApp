package com.ugelapp.android.data;

import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.ugelapp.android.Content;
import com.ugelapp.android.R;
import com.ugelapp.http.JSONManager;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

	private static final int NOTIF_ALERTA_ID = 1;
	Context contexto;
	recivid recid;
	String url2="http://zeniasoft.com/ugel/android/mensaje_android.php";
	public static int situacion=0;

	@Override
	public void onReceive(Context context, Intent intent) {
		 
		 PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
         PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
         wl.acquire();
         Bundle extras = intent.getExtras();
         contexto=context;
         
         if(extras != null ){
        	
            		  new asyncenviar().execute();
           
           
         }
      wl.release();
   } 
	
	
	public void SetAlarm(Context context){
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 30 seconds
        //am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 30 , pi);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 86400000 , pi);        
    }

	
    public void CancelAlarm(Context context){
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

   
    
 // CLASE ASINCRONA DE ENVIO DE POSICION   
    class asyncenviar extends AsyncTask< String, String, String > {
   	 
        protected void onPreExecute() {
        	
        }
 
		protected String doInBackground(String... params) {
		
			try{	
			JSONObject jdata=JSONManager.getJSONfromURL(url2);
			
			recid= new recivid();
			
			recid.setTexto_MEN(jdata.getJSONArray("results").getJSONObject(0).getString("texto_NOT"));
			recid.setInicio_MEN(jdata.getJSONArray("results").getJSONObject(0).getString("fini_NOT"));
			recid.setFin_MEN(jdata.getJSONArray("results").getJSONObject(0).getString("ffin_NOT"));		
			
			Log.i("validacion noti", "retorna ok");
			Log.i("validacion noti", jdata.getJSONArray("results").getJSONObject(0).getString("texto_NOT"));
			Log.i("validacion noti", jdata.getJSONArray("results").getJSONObject(0).getString("fini_NOT"));
			Log.i("validacion noti", jdata.getJSONArray("results").getJSONObject(0).getString("ffin_NOT"));
				return "ok";
			}catch(Exception e){
					return "err";
			}
        }
		
		protected void onPostExecute(String result) {
           
			
           if (result.equals("ok")){
        		NotificationCompat.Builder mBuilder =
				        new NotificationCompat.Builder(contexto)
				        .setSmallIcon(R.drawable.ic_launcher)
				        .setSmallIcon(R.drawable.ic_launcher)
				        .setContentTitle("NUEVO MENSAJE")
				        .setContentText(recid.getTexto_MEN())
				        .setContentInfo(recid.getFin_MEN())
				        .setTicker("UGEL");
        		Vibrator vibrator =(Vibrator) contexto.getSystemService(Context.VIBRATOR_SERVICE);
        	    vibrator.vibrate(200);
				Intent notIntent = 	new Intent(contexto, Content.class);
				notIntent.putExtra("Texto_MEN", recid.getTexto_MEN());
				PendingIntent contIntent = PendingIntent.getActivity(contexto, 0, notIntent, 0);
				
			    mBuilder.setContentIntent(contIntent);
				
				NotificationManager mNotificationManager =(NotificationManager)contexto.getSystemService(Context.NOTIFICATION_SERVICE);

				mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
        	   
        	   
   			}else{
   				
   					//Toast toast1 = Toast.makeText(contexto.getApplicationContext(),"OCURRIO UN ERROR", Toast.LENGTH_SHORT);
   	           	    //toast1.show();	
   				
           }
          }
	    }
      }
