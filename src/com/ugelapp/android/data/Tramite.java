package com.ugelapp.android.data;

public class Tramite {
	String asunto;
	String fecha;
	String estado;
	
	public Tramite(String asunto,String fecha,String estado){
		this.asunto=asunto;
		this.fecha=fecha;
		this.estado=estado;
	}
	
	public String getAsunto(){
		return asunto;
	}

	public String getFecha(){
		return fecha;
	}
	
	public String getEstado(){
		return estado;
	}

	
	public void setAsunto(String a){
		asunto = a;
	}

	public void setFecha(String f){
		fecha = f;
	}

	public void setEstado(String e){
		estado = e;
	}
}