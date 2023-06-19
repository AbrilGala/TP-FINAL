package EmpresaVuelo;

import EmpresaVuelo.Alojamiento.Alojamiento;
import EmpresaVuelo.Vuelos.Vuelo;

import java.util.HashSet;
import java.util.Iterator;

public class EmpresaVuelo {
    private HashSet<Vuelo> vuelos;
    private HashSet<Alojamiento> alojamientos;


    //Constructor


    //Metodos

    //Funcion que busca un vuelo segun su pais de origen y destino
    public HashSet<Vuelo> buscarVuelo (String paisOrigen,String paisDestino){
        HashSet<Vuelo> vuelosDisponibles = new HashSet<>();
        Iterator it = vuelos.iterator();
        while (it.hasNext()){
            Vuelo vuelo = (Vuelo) it.next();
            if (vuelo.isDisponibilidad() && vuelo.getOrigen().equalsIgnoreCase(paisOrigen) && vuelo.getDestino().equalsIgnoreCase(paisDestino)) {
                vuelosDisponibles.add(vuelo);
            }
        }
        return vuelosDisponibles;
    }
    
   
    //Funcion que busca vuelos por rango de precio
    public HashSet<Vuelo> buscarVuelo (float precioMin,float precioMax){
        HashSet<Vuelo> vuelosDisponibles = new HashSet<>();
        Iterator it = vuelos.iterator();
        while (it.hasNext()){
            Vuelo vuelo = (Vuelo) it.next();
            if (vuelo.isDisponibilidad() && precioMin<= vuelo.getPrecio() && vuelo.getPrecio()<=precioMax) {
                vuelosDisponibles.add(vuelo);
            }
        }
        return vuelosDisponibles;
    }


}
