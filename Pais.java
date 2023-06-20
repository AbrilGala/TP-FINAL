package APIS.Paises;

import java.util.Iterator;

public class Pais {
    //Atributos
    private String nombre;
    private String idioma;
    private String capital;
    private String continente;
    private String moneda;
    private int poblacion;
    private String bandera; //URL
    private int id;

    //Constructor


    public Pais(String nombre, String idioma, String capital, String continente, String moneda, int poblacion, String bandera, int id) {
        this.nombre = nombre;
        this.idioma = idioma;
        this.capital = capital;
        this.continente = continente;
        this.moneda = moneda;
        this.poblacion = poblacion;
        this.bandera = bandera;
        this.id = id;
    }

    @Override
    public String toString() {
        return "\nPais{" +
                "\nNombre='" + nombre + "\n" +
                "Idioma='" + idioma + "\n" +
                "Capital='" + capital + "\n" +
                "Continente='" + continente + "\n" +
                "Moneda='" + moneda + "\n" +
                "Poblacion=" + poblacion + "\n" +
                "Bandera='" + bandera + "\n" +
                "ID=" + id +
                '}';
    }

    //Getters:

    public String getNombre() {
        return nombre;
    }
    public String getIdioma() {
        return idioma;
    }
    public String getCapital() {
        return capital;
    }
    public String getContinente() {
        return continente;
    }
    public String getMoneda() {
        return moneda;
    }
    public int getPoblacion() {
        return poblacion;
    }
    public String getBandera() {
        return bandera;
    }
    public int getId() {
        return id;
    }
}
