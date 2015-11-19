package com.ugelapp.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.ugelapp.android.R;
import com.ugelapp.android.data.AppPreferences;
import com.ugelapp.android.data.Tramite;

public class getEstadoTramite {
	
	private URL url;
	private Context context;
	private String id;
	HttpURLConnection conexion;
	JSONObject jObj = null;
	JSONArray node = null;
	private static final String TAG_MAIN = "results";
	private static final String TAG_ASUNTO = "asunto_SOL";
	private static final String TAG_ESTADO = "estado_SOL";
	AppPreferences preferencias;
	
	
	public getEstadoTramite(Context c, String url){
		try{
			this.url = new URL(url);
			this.context = c;
			preferencias = new AppPreferences(context);
			id = preferencias.getValue(c.getResources().getString(R.string.persona_id));
		}catch(MalformedURLException e){
			
		}
	}
	
	public String validarLogin(){
		
		try {
			
			StringBuilder construye = new StringBuilder();

			conexion = (HttpURLConnection) url.openConnection();
			conexion.setRequestMethod("POST");
			conexion.setDoInput(true);
			conexion.setDoOutput(true);	
			
			PrintWriter printer = new PrintWriter(conexion.getOutputStream());
			printer.print(parametros(id));
			printer.close();
			
			InputStreamReader input = new InputStreamReader(conexion.getInputStream());
			BufferedReader buffer = new BufferedReader(input);
			
			String linea;
			
			while((linea=buffer.readLine()) !=null){
				construye.append(linea);
			}			
			
			Log.i("json respues", construye.toString());
			
			return construye.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	} 
	
	
	public ArrayList<Tramite> leer(){
		
		String json = validarLogin();
		ArrayList<Tramite> listramites = new ArrayList<Tramite>();
		try{
			
			jObj = new JSONObject(json);			
			
			if(!jObj.get(TAG_MAIN).toString().equals("-1")){
				
				
				node = jObj.getJSONArray(TAG_MAIN);
				for(int x=0; x<node.length();x++){
					JSONObject item_node = node.getJSONObject(x);
					Tramite t = new Tramite(item_node.getString(TAG_ASUNTO), "", item_node.getString(TAG_ESTADO));											
					listramites.add(t);
				}						
				
				return listramites;				
			}
			
			return null;
			
		}catch(Exception e){
			Log.i("paso 3", "paso 3");
			return null;
		}
		
	}
	
	
	private String parametros(String id){
		 ArrayList<NameValuePair> values = new ArrayList<NameValuePair>();
         values.add(new BasicNameValuePair("id",id));
         
         String param="";

         try {
        	 
        	 for (NameValuePair nvp : values) {
        		 
        		 if (param == "")
					param=nvp.getName() + "=" + URLEncoder.encode(nvp.getValue(),"UTF-8");								
        		  else 
        			param+= "&" + nvp.getName() + "=" + URLEncoder.encode(nvp.getValue(),"UTF-8");
        		 
        	 }
        	 
     	} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
         
         return param;
	}
}	