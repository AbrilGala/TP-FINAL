package APIS.Paises;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public static Pais JSONToPais(JSONObject jsonObject) throws JSONException
    {
        String nombre = jsonObject.getString("nombre");
        String idioma = jsonObject.getString("idioma");
        String capital = jsonObject.getString("capital");
        String continente = jsonObject.getString("continente");
        String moneda = jsonObject.getString("moneda");
        int poblacion = jsonObject.getInt("poblacion");
        String bandera = jsonObject.getString("nombre");
        int id = jsonObject.getInt("id");
        Pais pais = new Pais(nombre, idioma, capital, continente, moneda, poblacion, bandera, id);
        return pais;
    }
    public JSONObject paisToJSON() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombre", nombre);
        jsonObject.put("idioma", idioma);
        jsonObject.put("capital", capital);
        jsonObject.put("continente", continente);
        jsonObject.put("moneda", moneda);
        jsonObject.put("poblacion", poblacion);
        jsonObject.put("bandera", bandera);
        jsonObject.put("id", id);
        return jsonObject;
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
