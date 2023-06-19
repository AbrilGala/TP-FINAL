package apis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.TreeMap;

public class Controladora {
    private TreeMap<Integer, Pais> treeMap;
    public Controladora()
    {
        treeMap = new TreeMap<>();
    }
    public void JSONtoJava()throws JSONException
    {
        JSONArray jsonArray = new JSONArray(ConsumoAPI.getInfo());
        int population = 0;
        for(int i=0; i<jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String abbreviation = jsonObject.getString("abbreviation");
            String capital = jsonObject.getString("capital");
            String currency = jsonObject.getString("currency");
            String name = jsonObject.getString("name");
            String phone = jsonObject.getString("phone");
            int id = jsonObject.getInt("id");
            if (jsonObject.has("population"))
            {
                population = jsonObject.getInt("population");
            }
            JSONObject jsonObject1 = jsonObject.getJSONObject("media");
            String flag = jsonObject1.getString("flag");
            String emblem = jsonObject1.getString("emblem");
            String orthographic = jsonObject1.getString("orthographic");
            Pais countries = new Pais(abbreviation, capital, currency, name, phone, population, flag, emblem, orthographic, id);
            treeMap.put(id, countries);
        }
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
