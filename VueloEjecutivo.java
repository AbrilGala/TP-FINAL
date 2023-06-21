package Empresa.Vuelos;

import Apis.Pais;
import org.json.JSONException;
import org.json.JSONObject;

public class VueloEjecutivo extends Vuelo{

    //Constructor
    public VueloEjecutivo()
    {

    }
    public VueloEjecutivo(int cantPasajeros, int capacidadMax, boolean disponibilidad, Pais origen, Pais destino, double cantidadHoras, double distanciaKm, Aerolinea aerolinea, int id, double precio, String fechaDeLlegada, String fechaDeSalida) {
        super(cantPasajeros, capacidadMax, disponibilidad, origen, destino, cantidadHoras, distanciaKm, aerolinea, id, precio, fechaDeLlegada, fechaDeSalida);
    }


    //Metodos

    @Override
    public String toString() {
        return super.toString()+"\ntipo vuelo: Vuelo Ejecutivo ";
    }

    @Override
    public double calcularPrecio() {
        double precioKm = 30;
        return precioKm*getDistanciaKm();
    }
    @Override
    public JSONObject vueloToJSON() throws JSONException
    {
        JSONObject jsonObject = super.vueloToJSON();
        String tipovuelo = "Ejecutivo";
        jsonObject.put("tipoVuelo", tipovuelo);
        return jsonObject;
    }
}
