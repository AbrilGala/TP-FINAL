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

    //Funcion que busca un vuelo segun su pais de origen
    public HashSet<Vuelo> buscarVuelo (String paisOrigen){
        HashSet<Vuelo> vuelosMismoOrigen = new HashSet<>();
        Iterator it = vuelos.iterator();
        while (it.hasNext()){
            Vuelo vuelo = (Vuelo) it.next();
            if (vuelo.getOrigen().equalsIgnoreCase(paisOrigen)) {
                vuelosMismoOrigen.add(vuelo);
            }
        }
        return vuelosMismoOrigen;
    }

    //Funcion que busca los vuelos que se encuentran disponibles
    public HashSet<Vuelo> buscarVuelo (boolean disponibilidad){
        HashSet<Vuelo> vuelosDisponibles = new HashSet<>();
        Iterator it = vuelos.iterator();
        while (it.hasNext()){
            Vuelo vuelo = (Vuelo) it.next();
            if (vuelo.isDisponibilidad() == disponibilidad) {
                vuelosDisponibles.add(vuelo);
            }
        }
        return vuelosDisponibles;
    }


}
