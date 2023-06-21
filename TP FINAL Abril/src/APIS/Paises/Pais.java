package APIS.Paises;

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


    //Constructores:
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

    public Pais() {
        nombre = "";
        capital = "";
        continente = "";
        moneda = "";
        poblacion = 0;
        bandera = "";
        idioma = "";
        id = 0;
    }

    @Override
    public String toString() {
        return "Pais{" +
                "nombre='" + nombre + '\'' +
                ", idioma='" + idioma + '\'' +
                ", capital='" + capital + '\'' +
                ", continente='" + continente + '\'' +
                ", moneda='" + moneda + '\'' +
                ", poblacion=" + poblacion +
                ", bandera='" + bandera + '\'' +
                ", id=" + id +
                '}';
    }


    //Getters:

    public String getNombre() {
        return nombre;
    }

    public String getMoneda() {
        return moneda;
    }

    public String getCapital() {
        return capital;
    }

    public String getContinente() {
        return continente;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public String getBandera() {
        return bandera;
    }

    public String getIdioma() {
        return idioma;
    }

    public int getId() {
        return id;
    }

    //JSON:

    /**
     * El método JSONToPais permite deserializar la información de un país y almacenarlo en un objeto Pais
     * @param jsonObject es el objeto JSON de un pais
     * @return el Pais ya cargado desde JSON
     * @throws JSONException lanzado por el JSONObject
     * @author Nahuel Moron
     */
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


    /**
     * El método paisToJSON permite serializar la información de un Pais hacia un objeto JSON
     * @return el JSONObject creado y cargado
     * @throws JSONException lanzado por el JSONObject
     * @author Nahuel Moron
     */
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


    /**
     * Función que verifica si un país es igual a otro
     * @param obj es el objeto/pais a comparar
     * @return true si los paises son iguales, de lo contrario retorna false
     */
    @Override
    public boolean equals(Object obj) {
        boolean esIgual = false;
        if (obj != null){
            if (obj instanceof Pais){
                Pais aux = (Pais) obj;
                if (aux.getNombre().equalsIgnoreCase(getNombre())  && aux.getIdioma().equalsIgnoreCase(getIdioma()) && aux.getId()==getId() && aux.getMoneda().equalsIgnoreCase(getMoneda())){
                    esIgual = true;
                }
            }
        }
        return esIgual;
    }
}
