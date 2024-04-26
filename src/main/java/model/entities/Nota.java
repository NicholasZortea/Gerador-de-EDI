package model.entities;

import java.util.HashMap;

public class Nota {

    private String numeroNota;
    private String tipoNota;
    private String data;
    private HashMap<String, String> mapParaTipos = new HashMap<>();
    
    public Nota(String numeroNota, String tipoNota, String data) {
        mapParaTipos.put("Nota de compra", "5700");
        mapParaTipos.put("Nota de venda", "5400");
        this.numeroNota = numeroNota;
        this.tipoNota = tipoNota;
        System.out.println("tipo que chegou: " + tipoNota);
        this.data = data.replace("/", "");
    }
    
    public String getCodigoNota(){
        return mapParaTipos.get(tipoNota);
    }

    public String getNumeroNota() {
        return numeroNota;
    }

    public String getData() {
        return data;
    }
    
    
}
