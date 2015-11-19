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

import com.ugelapp.android.R;
import com.ugelapp.android.data.AppPreferences;

public class Consultar_Resolucion {
	
	private URL url;
	private String numero;
	HttpURLConnection conexion;
	JSONObject jObj = null;
	JSONArray node = null;
	private static final String TAG_MAIN = "results";
	private static final String TAG_RES_ASUNTO = "estado_RES";	
	AppPreferences preferencias;
	
	public Consultar_Resolucion(String url,String numero){
		try{
			this.url = new URL(url);
			this.numero = numero;
		}catch(MalformedURLException e){
			
		}
	}
	
	private String sendData(){
		
		try {
			
			StringBuilder construye = new StringBuilder();
			
					
			conexion = (HttpURLConnection) url.openConnection();
			conexion.setRequestMethod("POST");
			conexion.setDoInput(true);
			conexion.setDoOutput(true);	
			
			PrintWriter printer = new PrintWriter(conexion.getOutputStream());
			printer.print(parametros(numero));
			printer.close();
			
			InputStreamReader input = new InputStreamReader(conexion.getInputStream());
			BufferedReader buffer = new BufferedReader(input);
			
			String linea;
			
			while((linea=buffer.readLine()) !=null){
				construye.append(linea);
			}
			
			//Log.i("resultado ugel urlconection", construye.toString());
			
			return construye.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	} 
	
	
	public String leer(){
		
		String result="-1";
		String json = sendData();
		
		try{
			
			jObj = new JSONObject(json);
			
			
			if(!jObj.get(TAG_MAIN).toString().equals("-1")){
				node = jObj.getJSONArray(TAG_MAIN);
				for(int x=0; x<node.length();x++){
					JSONObject item_node = node.getJSONObject(x);					
					result = item_node.getString(TAG_RES_ASUNTO);					
				}						
				return result;
			}else{
				return result;
			}
			
		}catch(Exception e){
			return "-2";
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
