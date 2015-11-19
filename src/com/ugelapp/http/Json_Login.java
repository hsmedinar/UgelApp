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

import com.ugelapp.android.R;
import com.ugelapp.android.data.AppPreferences;

public class Json_Login {
	
	private URL url;
	private String login;
	private String pass;
	private Context context;
	HttpURLConnection conexion;
	JSONObject jObj = null;
	JSONArray node = null;
	private static final String TAG_MAIN = "results";
	private static final String TAG_CODE_ID = "id_PER";
	private static final String TAG_CODE_DNI = "dni_PER";
	private static final String TAG_CODE_TIPO = "tipo_PER";
	AppPreferences preferencias;
	
	public Json_Login(Context c, String url,String login,String pass){
		try{
			this.url = new URL(url);
			this.login = login;
			this.pass = pass;	
			this.context = c;
			preferencias = new AppPreferences(context);
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
			printer.print(parametros(login,pass));
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
		String json = validarLogin();
		
		try{
			
			jObj = new JSONObject(json);
			
			
			if(!jObj.get(TAG_MAIN).toString().equals("-1")){
				node = jObj.getJSONArray(TAG_MAIN);
				for(int x=0; x<node.length();x++){
					JSONObject item_node = node.getJSONObject(x);					
					result = item_node.getString(TAG_CODE_ID);
					preferencias.saveValue(context.getResources().getString(R.string.persona_dni), item_node.getString(TAG_CODE_DNI));
					preferencias.saveValue(context.getResources().getString(R.string.persona_tipo), item_node.getString(TAG_CODE_TIPO));
				}						
				return result;
			}else{
				return result;
			}
			
		}catch(Exception e){
			return "-2";
		}
		
	}
	
	
	private String parametros(String user,String pass){
		 ArrayList<NameValuePair> values = new ArrayList<NameValuePair>();
         values.add(new BasicNameValuePair("user",user));
         values.add(new BasicNameValuePair("pass",pass));	
         
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
