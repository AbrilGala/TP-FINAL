package EmpresaVuelo.Vuelos;

import EmpresaVuelo.Vuelos.Aerolineas.Aerolinea;

public class VueloPrimeraClase extends Vuelo{

    //Constructor
    public VueloPrimeraClase(int cantPasajeros, int capacidadMax, boolean disponibilidad, String origen, String destino, float cantidadHoras, float distanciaKm, Aerolinea aerolinea, int id, float precio) {
        super(cantPasajeros, capacidadMax, disponibilidad, origen, destino, cantidadHoras, distanciaKm, aerolinea, id, precio);
    }

    //Metodos
    @Override
    public String toString() {
        return super.toString()+"\ntipo vuelo: Vuelo Primera Clase";
    }

    //Funcion que calcula el precio de un vuelo de primera clase segun su distancia en km
    @Override
    public float calcularPrecio() {
        float precioKm = 65;
        return precioKm*getDistanciaKm();
    }
}
