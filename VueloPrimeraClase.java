package EmpresaVuelo.Vuelos;

import APIS.Paises.Pais;
import EmpresaVuelo.Vuelos.Aerolineas.Aerolinea;

public class VueloPrimeraClase extends Vuelo{

    //Constructor


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
        float precioKm = 65;
        return precioKm*getDistanciaKm();
    }
}
