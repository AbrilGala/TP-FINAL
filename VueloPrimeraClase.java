package Empresa.Vuelos;

import Apis.Pais;
import org.json.JSONException;
import org.json.JSONObject;

public class VueloPrimeraClase extends Vuelo{

    //Constructor
    public VueloPrimeraClase()
    {

    }

    public VueloPrimeraClase(int cantPasajeros, int capacidadMax, boolean disponibilidad, Pais origen, Pais destino, double cantidadHoras, double distanciaKm, Aerolinea aerolinea, int id, double precio, String fechaDeLlegada, String fechaDeSalida) {
        super(cantPasajeros, capacidadMax, disponibilidad, origen, destino, cantidadHoras, distanciaKm, aerolinea, id, precio, fechaDeLlegada, fechaDeSalida);
    }

    //Metodos
    @Override
    public String toString() {
        return super.toString()+"\ntipo vuelo: Vuelo Primera Clase";
    }

    //Funcion que calcula el precio de un vuelo de primera clase segun su distancia en km
    @Override
    public double calcularPrecio() {
        double precioKm = 65;
        return precioKm*getDistanciaKm();
    }
    @Override
    public JSONObject vueloToJSON() throws JSONException
    {
        JSONObject jsonObject = super.vueloToJSON();
        String tipovuelo = "Primera clase";
        jsonObject.put("tipoVuelo", tipovuelo);
        return jsonObject;
    }
}
