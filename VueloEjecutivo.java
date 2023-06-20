package EmpresaVuelo.Vuelos;

import APIS.Paises.Pais;
import EmpresaVuelo.Vuelos.Aerolineas.Aerolinea;

public class VueloEjecutivo extends Vuelo{

    //Constructor

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
       float precioKm = 30;
       return precioKm*getDistanciaKm();
    }
}
