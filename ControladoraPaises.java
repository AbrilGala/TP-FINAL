package Apis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.TreeMap;

public class ControladoraPaises {
    //Atributo
    private TreeMap<String, Pais> treeMap;

    //Constructor
    public ControladoraPaises()
    {
        treeMap = new TreeMap<>();
    }

    //Funcion que baja la información de un JSON to Java
    public TreeMap<String, Pais> JSONtoJava()throws JSONException
    {
        JSONArray jsonArray = new JSONArray(ConsumoAPI.getInfo());
        for(int i=0; i<jsonArray.length(); i++)
        {
            JSONObject object = jsonArray.getJSONObject(i);
            String nombre = object.getString("name");
            String capital = "";
            if(object.has("capital"))
            {
                capital = object.getString("capital");
            }
            String continente = object.getString("region");
            int poblacion = object.getInt("population");

            String moneda = "";
            if(object.has("currencies"))
            {
                JSONArray arrayMoneda = object.getJSONArray("currencies");
                JSONObject objectMoneda = arrayMoneda.getJSONObject(0);
                moneda = objectMoneda.getString("name");
            }
            JSONArray arrayLenguaje = object.getJSONArray("languages");
            JSONObject objectLenguaje = arrayLenguaje.getJSONObject(0);
            String idioma = objectLenguaje.getString("name");
            String bandera = object.getString("flag");
            int id = i;
            Pais unPais = new Pais(nombre, idioma, capital, continente, moneda, poblacion, bandera, id);
            treeMap.put(nombre, unPais);
        }
        return treeMap;
    }

    public String mostrar()
    {
        String info = "";
        Pais aux;
        for(int i=1; i<treeMap.size(); i++)
        {
            aux = treeMap.get(i);
            info += aux.toString() + "\n";
        }
        return info;
    }
}
