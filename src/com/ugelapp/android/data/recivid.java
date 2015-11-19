package com.ugelapp.android.data;


public class recivid {
  
    	int id_MEN;
        String texto_NOT;			 	 	 	 	 	 	
		String fini_NOT;			 	 	 	 	 	 	
		String ffin_NOT;			 	 	 	 	 	 	
		

    public recivid() {
    }

    public recivid(int id_MEN, String texto_NOT, String fini_NOT, String ffin_NOT) {
        this.id_MEN = id_MEN;
        this.texto_NOT = texto_NOT;
        this.fini_NOT = fini_NOT;
        this.ffin_NOT = ffin_NOT;
    }

    public int getId_MEN() {
        return id_MEN;
    }

    public void setId_MEN(int id_MEN) {
        this.id_MEN = id_MEN;
    }

    public String getTexto_MEN() {
        return texto_NOT;
    }

    public void setTexto_MEN(String texto_MEN) {
        this.texto_NOT = texto_NOT;
    }

    public String getInicio_MEN() {
        return fini_NOT;
    }

    public void setInicio_MEN(String inicio) {
        this.fini_NOT = fini_NOT;
    }

    public String getFin_MEN() {
        return ffin_NOT;
    }

    public void setFin_MEN(String fin) {
        this.ffin_NOT = ffin_NOT;
    }
        
}
