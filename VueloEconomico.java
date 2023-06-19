package EmpresaVuelo.Vuelos;

import EmpresaVuelo.Vuelos.Aerolineas.Aerolinea;

public class VueloEconomico extends Vuelo{

    //Constructor
    public VueloEconomico(int cantPasajeros, int capacidadMax, boolean disponibilidad, String origen, String destino, float cantidadHoras, float distanciaKm, Aerolinea aerolinea, int id, float precio) {
        super(cantPasajeros, capacidadMax, disponibilidad, origen, destino, cantidadHoras, distanciaKm, aerolinea, id, precio);
    }

    public VueloEconomico() {

    }

    @Override
    public String toString() {
        return super.toString()+"\ntipo vuelo: Vuelo Económico";
    }

    @Override
    public float calcularPrecio() {
        float precioKm = 20;
        return getDistanciaKm()*precioKm;
    }

}
