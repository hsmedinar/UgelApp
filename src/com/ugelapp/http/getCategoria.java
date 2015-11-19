package com.ugelapp.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import android.util.Log;

import com.ugelapp.android.data.AppPreferences;

public class getCategoria {
	
	private URL url;
	private String numero;
	HttpURLConnection conexion;
	JSONObject jObj = null;
	JSONArray node = null;
	private static final String TAG_MAIN = "results";
	private static final String TAG_CATEGORIA = "nombre_CAT";	
	AppPreferences preferencias;
	
	public getCategoria(String url){
		try{
			this.url = new URL(url);
		}catch(MalformedURLException e){
			
		}
	}
	
	private String getData(){
		
		StringBuilder builder = new StringBuilder();
        try 
        {
        	conexion = (HttpURLConnection) url.openConnection();
        	InputStreamReader input = new InputStreamReader(conexion.getInputStream());	
        	BufferedReader buffer = new BufferedReader(input);
        	String linea;
        	
        	while((linea=buffer.readLine()) != null){
        		builder.append(linea);        		
        	}
        	        	
            return builder.toString();
        } 
        catch (IOException e) 
        {
            //throw new RuntimeException(e);
        	Log.i("error json", e.getMessage());
        	return builder.toString();
        }
	} 
	
	
	public ArrayList<String> leer(){
		
		ArrayList<String> result= new ArrayList<String>();
		
		String json = getData();
		try{
			
			jObj = new JSONObject(json);
			if(!jObj.get(TAG_MAIN).toString().equals("-1")){
				node = jObj.getJSONArray(TAG_MAIN);
				for(int x=0; x<node.length();x++){
					JSONObject item_node = node.getJSONObject(x);					
					result.add(item_node.getString(TAG_CATEGORIA));					
				}
			
				return result;
			}
			
			return result;			
		}catch(Exception e){
			return null;
		}
		
	}
	
	
	private String parametros(String numero){
		 ArrayList<NameValuePair> values = new ArrayList<NameValuePair>();
         values.add(new BasicNameValuePair("number",numero));
         
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
