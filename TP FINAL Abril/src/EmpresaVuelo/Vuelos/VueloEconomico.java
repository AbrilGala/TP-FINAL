package EmpresaVuelo.Vuelos;

import EmpresaVuelo.Vuelos.Aerolineas.Aerolinea;

public class VueloEconomico extends Vuelo{

    //Constructor


    public VueloEconomico(int cantPasajeros, int capacidadMax, boolean disponibilidad, String origen, String destino, double cantidadHoras, double distanciaKm, Aerolinea aerolinea, int id, double precio, String fechaDeLlegada, String fechaDeSalida) {
        super(cantPasajeros, capacidadMax, disponibilidad, origen, destino, cantidadHoras, distanciaKm, aerolinea, id, precio, fechaDeLlegada, fechaDeSalida);
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
