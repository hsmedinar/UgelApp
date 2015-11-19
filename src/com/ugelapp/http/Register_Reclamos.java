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

import com.ugelapp.android.data.AppPreferences;

public class Register_Reclamos {
	
	private URL url;
	private String id;
	private String dni;
	private String asunto;
	private String descripcion;
	HttpURLConnection conexion;
	JSONObject jObj = null;
	JSONArray node = null;
	private static final String TAG_MAIN = "results";	
	AppPreferences preferencias;
	
	public Register_Reclamos(String url,String id,String dni,String asunto,String descripcion){
		try{
			this.url = new URL(url);
			this.id = id;
			this.dni = dni;	
			this.asunto = asunto;
			this.descripcion = descripcion;
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
			printer.print(parametros(id,dni,asunto,descripcion));
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
		
		String json = sendData();
		
		try{
			
			jObj = new JSONObject(json);								
			return jObj.get(TAG_MAIN).toString();
			
		}catch(Exception e){
			return "-2";
		}
		
	}
	
	
	private String parametros(String id,String dni, String asunto, String descripcion){
		 ArrayList<NameValuePair> values = new ArrayList<NameValuePair>();
         values.add(new BasicNameValuePair("id",id));
         values.add(new BasicNameValuePair("dni",dni));	
         values.add(new BasicNameValuePair("asunto",asunto));
         values.add(new BasicNameValuePair("description",descripcion));
         
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
