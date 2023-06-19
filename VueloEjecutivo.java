package EmpresaVuelo.Vuelos;

import EmpresaVuelo.Vuelos.Aerolineas.Aerolinea;

public class VueloEjecutivo extends Vuelo{

    //Constructor
    public VueloEjecutivo(int cantPasajeros, int capacidadMax, boolean disponibilidad, String origen, String destino, float cantidadHoras, float distanciaKm, Aerolinea aerolinea, int id, float precio) {
        super(cantPasajeros, capacidadMax, disponibilidad, origen, destino, cantidadHoras, distanciaKm, aerolinea, id, precio);
    }

    //Metodos

    @Override
    public String toString() {
        return super.toString()+"\ntipo vuelo: Vuelo Ejecutivo ";
    }

    @Override
    public float calcularPrecio() {
       float precioKm = 30;
       return precioKm*getDistanciaKm();
    }
}
