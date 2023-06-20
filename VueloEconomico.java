package EmpresaVuelo.Vuelos;

import APIS.Paises.Pais;
import EmpresaVuelo.Vuelos.Aerolineas.Aerolinea;

public class VueloEconomico extends Vuelo{

    //Constructor
    public VueloEconomico(int cantPasajeros, int capacidadMax, boolean disponibilidad, Pais origen, Pais destino, double cantidadHoras, double distanciaKm, Aerolinea aerolinea, int id, double precio, String fechaDeLlegada, String fechaDeSalida) {
        super(cantPasajeros, capacidadMax, disponibilidad, origen, destino, cantidadHoras, distanciaKm, aerolinea, id, precio, fechaDeLlegada, fechaDeSalida);
    }

    public VueloEconomico() {

    }

    @Override
    public String toString() {
        return super.toString()+"\ntipo vuelo: Vuelo Económico";
    }

    @Override
    public double calcularPrecio() {
        float precioKm = 20;
        return getDistanciaKm()*precioKm;
    }

}
