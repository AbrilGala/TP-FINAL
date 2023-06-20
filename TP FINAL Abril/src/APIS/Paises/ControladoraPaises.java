package APIS.Paises;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.TreeMap;

public class ControladoraPaises {
    //Atributo
    private TreeMap<Integer, Pais> treeMap;

    //Constructor
    public ControladoraPaises()
    {
        treeMap = new TreeMap<>();
    }

    //Funcion que baja la información de un JSON to Java
    public void JSONtoJava()throws JSONException
    {
        JSONArray jsonArray = new JSONArray(ConsumoAPI.getInfo());
        int poblacion = 0;
        for(int i=0; i<jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String abreviacion = jsonObject.getString("abbreviation");
            String capital = jsonObject.getString("capital");
            String moneda = jsonObject.getString("currency");
            String nombre = jsonObject.getString("name");
            String codigoTelefonico = jsonObject.getString("phone");
            int id = jsonObject.getInt("id");
            if (jsonObject.has("poblacion"))
            {
                poblacion = jsonObject.getInt("population");
            }
            JSONObject jsonObject1 = jsonObject.getJSONObject("media");
            String bandera = jsonObject1.getString("flag");
            String emblema = jsonObject1.getString("emblem");
            String idioma = jsonObject1.getString("orthographic");
            Pais unPais = new Pais(abreviacion, capital, moneda, nombre, codigoTelefonico, poblacion, bandera, emblema, idioma, id);
            treeMap.put(id, unPais);
        }
    }

    public StringBuilder mostrar()
    {
        StringBuilder info = new StringBuilder();
        Pais unPais;
        Iterator it = treeMap.entrySet().iterator();
        while (it.hasNext()){
            unPais = (Pais) it.next();
            info.append(unPais.toString()).append("\n");
        }
        return info;
    }
}
