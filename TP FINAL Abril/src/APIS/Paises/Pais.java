package APIS.Paises;

import java.util.Iterator;

public class Pais {
    //Atributos
    private String abreviacion;
    private String capital;
    private String moneda;
    private String nombre;
    private String codigoTelefonico;
    private int poblacion;
    private String bandera;
    private String emblema;
    private String idioma; //Ortografía ?
    private int id;

    //Constructor
    public Pais(String abreviacion, String capital, String moneda, String nombre, String codigoTelefonico, int poblacion, String bandera, String emblema, String idioma, int id) {
        this.abreviacion = abreviacion;
        this.capital = capital;
        this.moneda = moneda;
        this.nombre = nombre;
        this.codigoTelefonico = codigoTelefonico;
        this.poblacion = poblacion;
        this.bandera = bandera;
        this.emblema = emblema;
        this.idioma = idioma;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pais{" +
                "abreviacion='" + abreviacion + '\'' +
                ", capital='" + capital + '\'' +
                ", moneda='" + moneda + '\'' +
                ", nombre='" + nombre + '\'' +
                ", codigoTelefonico='" + codigoTelefonico + '\'' +
                ", poblacion=" + poblacion +
                ", bandera='" + bandera + '\'' +
                ", emblema='" + emblema + '\'' +
                ", idioma='" + idioma + '\'' +
                ", id=" + id +
                '}';
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public String getCapital() {
        return capital;
    }

    public String getMoneda() {
        return moneda;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigoTelefonico() {
        return codigoTelefonico;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public String getBandera() {
        return bandera;
    }

    public String getEmblema() {
        return emblema;
    }

    public String getIdioma() {
        return idioma;
    }

    public int getId() {
        return id;
    }
}
