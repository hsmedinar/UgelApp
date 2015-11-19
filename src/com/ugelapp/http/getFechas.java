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

import com.ugelapp.android.data.AccessModel;

import android.content.Context;
import android.util.Log;

public class getFechas  {
	
	private URL url;
	private String tipo;
	private String year;
	private Context context;
	private AccessModel model;
	HttpURLConnection conexion;
	JSONObject jObj = null;
	JSONArray node = null;
	private static final String TAG_MAIN = "results";
	private static final String TAG_CODE_ID = "fechas";

	
	
	public getFechas(Context c, String url,String tipo,String year){
		try{
		
			
			this.url = new URL(url);
			this.context = c;
			this.year = year;
			this.tipo =	tipo;	
			model = new AccessModel(c);
			
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
			printer.print(parametros(tipo,year));
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
	
	
	public int leer(){
		
		String[] valores = null;
		String json = validarLogin();
		
		try{
			
			jObj = new JSONObject(json);
			
			
			if(!jObj.get(TAG_MAIN).toString().equals("-1")){
				
				
				node = jObj.getJSONArray(TAG_MAIN);
				for(int x=0; x<node.length();x++){
					JSONObject item_node = node.getJSONObject(x);					
					valores = item_node.getString(TAG_CODE_ID).split(" ");					
				}						
				
				model.deletePagosDay();
				
				for (int i = 0; i < valores.length; i++) {					
					model.registerFechaPago(Integer.parseInt(valores[i]), i + 1, Integer.parseInt(year));
		        }
		        
				
				return 1;				
			}
			
			return -1;
			
		}catch(Exception e){
			Log.i("paso 3", "paso 3");
			return -2;
		}
		
	}
	
	
	private String parametros(String tipo,String year){
		 ArrayList<NameValuePair> values = new ArrayList<NameValuePair>();
         values.add(new BasicNameValuePair("tipo",tipo));
         values.add(new BasicNameValuePair("year",year));
         
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

