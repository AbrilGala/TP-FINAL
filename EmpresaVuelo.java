package EmpresaVuelo;

import EmpresaVuelo.Alojamiento.Alojamiento;
import EmpresaVuelo.Vuelos.Vuelo;

import java.util.HashSet;
import java.util.Iterator;

public class EmpresaVuelo {
    private HashSet<Vuelo> vuelos;
    private HashSet<Alojamiento> alojamientos;
    private TreeMap<String, Pais> paises; //Cargado desde un JSON

    //Constructor
    public EmpresaVuelo (){
        vuelos = new HashSet<>();
        alojamientos = new HashSet<>();
        paises = new TreeMap<>();
    }


    //Metodos
    
/** VerificarPais es una función que nos permite saber si un pais existe o no
     * @author Abril Galarraga
     * @param pais nombre del pais a verificar si existe
     * @return true si el pais existe, de lo contrario retorna false
     */
    public boolean verificarPais (String pais){
        boolean existe = false;
        Iterator it = paises.entrySet().iterator();
        while (it.hasNext()){
            Pais aux = (Pais) it.next();
            if(aux.getNombre().equalsIgnoreCase(pais)){
                existe = true;
            }
        }
        return existe;
    }

    
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
    public HashSet<Vuelo> buscarVuelo (float precioMin,float precioMax,HashSet setVuelos){
        HashSet<Vuelo> vuelosDisponibles = new HashSet<>();
        Iterator it = setVuelos.iterator();
        while (it.hasNext()){
            Vuelo vuelo = (Vuelo) it.next();
            if (vuelo.isDisponibilidad() && precioMin<= vuelo.getPrecio() && vuelo.getPrecio()<=precioMax) {
                vuelosDisponibles.add(vuelo);
            }
        }
        return vuelosDisponibles;
    }
    //Funcion que busca vuelos por clase
    public HashSet<Vuelo> buscarVuelo (String clase,HashSet setVuelos){
        HashSet<Vuelo> vuelosDisponibles = new HashSet<>();
        Iterator it = setVuelos.iterator();
        while (it.hasNext()){
            Vuelo vuelo = (Vuelo) it.next();
            if (vuelo.isDisponibilidad() && vuelo.getOrigen().equalsIgnoreCase(clase)) {
                vuelosDisponibles.add(vuelo);
            }
        }
        return vuelosDisponibles;
    }
    
 /** La siguiente función permite visualizar al usuario la información de un país solicitado a través de su nombre
     * @param nombrePaisBuscado es el nombre del país que el usuario desea buscar para obtener su información
     * @author AbrilGalarraga
     * @return el país encontrado, de lo contrario retorna null
     */
    public Pais buscarPaisPorNombre (String nombrePaisBuscado){
        Pais buscado = null;
        Iterator it = paises.entrySet().iterator();
        while (it.hasNext()){
            Pais aux = (Pais) it.next();
            if(aux.getNombre().equalsIgnoreCase(nombrePaisBuscado)){
                buscado = aux;
            }
        }
        return buscado;
    }

    /**
     * La siguiente función permite visualizar al usuario la información de un país solicitado a través de su clave
     * @param claveDelPais es la abreviacion de un pais ingresado por el usuario
     * @return el pais encontrado, de lo contrario retorna null
     * @author AbrilGalarraga
     * @throws ClavePaisInexistenteException en caso de que la clave/abreviacion del país ingresado sea inexistente
     */
    public Pais buscarPaisPorClave (String claveDelPais) throws ClavePaisInexistenteException{
        Pais buscado = null;
        if (paises.containsKey(claveDelPais)){
            buscado = paises.get(claveDelPais);
        } else {
            throw new ClavePaisInexistenteException();
        }
        return buscado;
    }

    /**
     * La función buscarAlojamiento permite al usuario filtrar su búsqueda de alojamientos estableciendo una condicion específica
     * @author AbrilGalarraga
     * @param precioMax es el precio máximo a filtrar
     * @return una coleccion de alojamientos que tengan un precio menor o igual al establecido por el usuario
     */
    public HashSet<Alojamiento> buscarAlojamiento (float precioMax){
        HashSet<Alojamiento> alojamientoPrecioMax = new HashSet<>();
        Iterator<Alojamiento> it = alojamientos.iterator();
        while (it.hasNext()){
            Alojamiento aux = (Alojamiento) it.next();
            if (aux.getPrecio()<=precioMax){ //Si el alojamiento actual no supera el precio maximo, lo agrega al set
                alojamientoPrecioMax.add(aux);
            }
        }
        return alojamientoPrecioMax;
    }

    /**
     * La función buscarAlojamiento permite al usuario filtrar su búsqueda de alojamientos estableciendo una condicion específica
     * @author AbrilGalarraga
     * @param capacidadMin es la capacidad minima que deben tener los alojamientos
     * @return una coleccion de alojamientos que tengan una capacidad mayor o igual a la solicitada
     */
    public HashSet<Alojamiento> buscarAlojamiento (int capacidadMin){
        HashSet<Alojamiento> alojamientosAptos = new HashSet<>();
        Iterator<Alojamiento> it = alojamientos.iterator();
        while (it.hasNext()){
            Alojamiento aux = (Alojamiento) it.next();
            if (aux.getCapacidad()>= capacidadMin){ //Si el alojamiento actual cumple con la capacidad mínima solicitada, lo agrega al set
                alojamientosAptos.add(aux);
            }
        }
        return alojamientosAptos;
    }

    /**
     * La función buscarAlojamiento permite al usuario filtrar su búsqueda de alojamientos estableciendo una condicion específica
     * @author AbrilGalarraga
     * @param pais es el país donde se sitúa el alojamiento buscado
     * @return una coleccion de alojamientos que se encuentren dentro del país solicitado
     * @throws PaisInexistenteException si el pais ingresado para filtrar la busqueda es inexistente
     */
    public HashSet<Alojamiento> buscarAlojamiento (String pais) throws PaisInexistenteException{
        HashSet<Alojamiento> alojamientosDisponibles = new HashSet<>();
        if (verificarPais(pais)){
            Iterator<Alojamiento> it = alojamientos.iterator();
            while (it.hasNext()){
                Alojamiento aux = (Alojamiento) it.next();
                if (aux.getPais().equalsIgnoreCase(pais)){ //Si el alojamiento actual se encuentra en el pais solicitado, lo agrega al set
                    alojamientosDisponibles.add(aux);
                }
            }
        } else {
            throw new PaisInexistenteException();
        }
        return alojamientosDisponibles;
    }

}
