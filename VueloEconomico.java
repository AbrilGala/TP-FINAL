package Empresa.Vuelos;

import Apis.Pais;
import org.json.JSONException;
import org.json.JSONObject;

public class VueloEconomico extends Vuelo{

    //Constructor
    public VueloEconomico(int cantPasajeros, int capacidadMax, boolean disponibilidad, Pais origen, Pais destino, double cantidadHoras, double distanciaKm, Aerolinea aerolinea, int id, double precio, String fechaDeLlegada, String fechaDeSalida) {
        super(cantPasajeros, capacidadMax, disponibilidad, origen, destino, cantidadHoras, distanciaKm, aerolinea, id, precio, fechaDeLlegada, fechaDeSalida);
    }

    public VueloEconomico() {

    }
    @Override
    public JSONObject vueloToJSON() throws JSONException
    {
        JSONObject jsonObject = super.vueloToJSON();
        String tipovuelo = "Economico";
        jsonObject.put("tipoVuelo", tipovuelo);
        return jsonObject;
    }

    @Override
    public String toString() {
        return super.toString()+"\ntipo vuelo: Vuelo Económico";
    }

    @Override
    public double calcularPrecio() {
        double precioKm = 20;
        return getDistanciaKm()*precioKm;
    }

}