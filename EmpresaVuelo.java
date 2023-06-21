package EmpresaVuelo;

import APIS.Paises.Pais;
import EmpresaVuelo.Alojamiento.Alojamiento;
import EmpresaVuelo.Excepciones.ClavePaisInexistenteException;
import EmpresaVuelo.Excepciones.DisponibilidadAgotadaException;
import EmpresaVuelo.Excepciones.PaisInexistenteException;
import EmpresaVuelo.Reservas.Reserva;
import EmpresaVuelo.Vuelos.Aerolineas.Aerolinea;
import EmpresaVuelo.Vuelos.Vuelo;
import EmpresaVuelo.Vuelos.VueloEconomico;
import EmpresaVuelo.Vuelos.VueloEjecutivo;
import EmpresaVuelo.Vuelos.VueloPrimeraClase;
import Persona.Usuario.JsonUtiles;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

public class EmpresaVuelo {
    //Atributos
    private HashSet<Vuelo> vuelos;
    private HashSet<Alojamiento> alojamientos;
    private TreeMap<String, Pais> paises; //Cargado desde un JSON

    //Constructor
    public EmpresaVuelo() {
        vuelos = new HashSet<>();
        alojamientos = new HashSet<>();
        paises = new TreeMap<>();
    }

    public String toStringVuelo() {
        String info = "";
        Iterator it = vuelos.iterator();
        while(it.hasNext())
        {
            Vuelo aux = (Vuelo) it.next();
            info += "\n"+aux.toString();
        }
        return info;
    }

    public String toStringAlojamiento()
    {
        String info = "";
        Iterator it = alojamientos.iterator();
        while(it.hasNext())
        {
            Alojamiento aux = (Alojamiento) it.next();
            info += "\n"+aux.toString();
        }
        return info;
    }

    public JSONArray vuelosToJSON() throws Exception
    {
        Iterator it = vuelos.iterator();
        JSONArray jsonArray = new JSONArray();
        while(it.hasNext())
        {
            Vuelo aux = (Vuelo) it.next();
            JSONObject jsonObject = aux.vueloToJson();
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
    public void JSONtoVuelo() throws Exception
    {
        JSONArray jsonArray= new JSONArray(JsonUtiles.leer("vuelos"));
        for(int i = 0; i<jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Vuelo aux = Vuelo.JSONAvuelos(jsonObject);
            vuelos.add(aux);
        }
    }
    public JSONArray alojamientoToJSON() throws Exception
    {
        Iterator it = alojamientos.iterator();
        JSONArray jsonArray = new JSONArray();
        while(it.hasNext())
        {
            Alojamiento aux = (Alojamiento) it.next();
            JSONObject jsonObject = aux.alojamientoToJSON();
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
    public void JSONtoAlojamiento() throws Exception
    {
        JSONArray jsonArray = new JSONArray(JsonUtiles.leer("alojamientos"));
        for(int i = 0; i<jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int capacidad = jsonObject.getInt("capacidad");
            String nombre = jsonObject.getString("nombre");
            boolean disponibilidad = jsonObject.getBoolean("disponibilidad");
            String ciudad = jsonObject.getString("ciudad");
            String pais = jsonObject.getString("pais");
            double direccion = jsonObject.getInt("direccion");
            String servicios = jsonObject.getString("descripcionServicios");
            double precio = jsonObject.getDouble("precio");
            int cantidadServicios = jsonObject.getInt("cantidadServicios");
            int cantidadEstrellas = jsonObject.getInt("cantidadEstrellas");
            Alojamiento aux = new Alojamiento(capacidad, nombre, disponibilidad, ciudad, pais, direccion, servicios, precio, cantidadServicios, cantidadEstrellas);
            alojamientos.add(aux);
        }
    }
    //Metodos:

    /**
     * La siguiente función permite buscar los vuelos por la clase
     *
     * @param paisOrigen  es el nombre del pais origen que manda el usuario,
     * @param paisDestino es el nombre del pais destino que manda el usuario
     * @return la coleccion de vuelos encontrados, de lo contrario retorna null
     * @author MateoCuevas
     */
    public HashSet<Vuelo> buscarVuelo(Pais paisOrigen, Pais paisDestino) {
        HashSet<Vuelo> vuelosDisponibles = new HashSet<>();
        Iterator it = vuelos.iterator();
        while (it.hasNext()) {
            Vuelo vuelo = (Vuelo) it.next();
            if (vuelo.isDisponibilidad() && vuelo.getOrigen().equals(paisOrigen) && vuelo.getDestino().equals(paisDestino)) {
                vuelosDisponibles.add(vuelo);
            }
        }
        return vuelosDisponibles;
    }

    /**
     * La siguiente función permite buscar los vuelos por la clase
     *
     * @param precioMin es el precio minimo que manda el usuario,
     * @param precioMax es el precio maximo que manda el usuario,
     * @param setVuelos es la coleccion con los vuelos
     * @return la coleccion de vuelos encontrados, de lo contrario retorna null
     * @author MateoCuevas
     */
    public HashSet<Vuelo> buscarVuelo(float precioMin, float precioMax, HashSet<Vuelo> setVuelos) {
        HashSet<Vuelo> vuelosDisponibles = new HashSet<>();
        Iterator it = setVuelos.iterator();
        while (it.hasNext()) {
            Vuelo vuelo = (Vuelo) it.next();
            if (vuelo.isDisponibilidad() && precioMin <= vuelo.getPrecio() && vuelo.getPrecio() <= precioMax) {
                vuelosDisponibles.add(vuelo);
            }
        }
        return vuelosDisponibles;
    }

    /**
     * La siguiente función permite buscar los vuelos por la clase
     *
     * @param clase     es el nombre de la clase que manda el usuario,
     * @param setVuelos es la coleccion con los vuelos
     * @return la coleccion de vuelos encontrados, de lo contrario retorna null
     * @author MateoCuevas
     */
    /*public HashSet<Vuelo> buscarVuelo(String clase, HashSet<Vuelo> setVuelos) {
        HashSet<Vuelo> vuelosDisponibles = new HashSet<>();
        Iterator it = setVuelos.iterator();
        while (it.hasNext()) {
            Vuelo vuelo = (Vuelo) it.next();
            if (vuelo.isDisponibilidad()) {
                if(vuelo instanceof clase)
                vuelosDisponibles.add(vuelo);
            }
        }
        return vuelosDisponibles;
    }

     */


    /**
     * La siguiente función permite buscar los vuelos por la aerolinea
     *
     * @param aerolinea es el nombre de la aerolinea que manda el usuario,
     * @param setVuelos es la coleccion con los vuelos
     * @return la coleccion de vuelos encontrados, de lo contrario retorna null
     * @author MateoCuevas & Abril Galarraga
     */
    public HashSet<Vuelo> buscarVuelo(Aerolinea aerolinea, HashSet<Vuelo> setVuelos) {
        HashSet<Vuelo> vuelosDisponibles = new HashSet<>();
        Iterator<Vuelo> it = setVuelos.iterator();
        while (it.hasNext()) {
            Vuelo vuelo = (Vuelo) it.next();
            if (vuelo.isDisponibilidad() && vuelo.getAerolinea() == aerolinea) {
                vuelosDisponibles.add(vuelo);
            }
        }
        return vuelosDisponibles;
    }


    /**
     * La siguiente función permite buscar los vuelos por el id
     *
     * @param id        es el id que manda el usuario,
     * @param setVuelos es la coleccion con los vuelos
     * @return el vuelo encontrado, de lo contrario retorna null
     * @author MateoCuevas
     */
    public Vuelo buscarVuelo(int id, HashSet<Vuelo> setVuelos) {
        Vuelo vueloAReservar = null;
        Iterator it = setVuelos.iterator();
        while (it.hasNext()) {
            Vuelo vuelo = (Vuelo) it.next();
            if (vuelo.isDisponibilidad() && vuelo.getId() == id) {
                vueloAReservar = vuelo;
            }
        }
        return vueloAReservar;
    }


    /**
     * VerificarPais es una función que nos permite saber si un pais existe o no
     *
     * @param pais nombre del pais a verificar si existe
     * @return true si el pais existe, de lo contrario retorna false
     * @author Abril Galarraga
     */
    public boolean verificarPais(String pais) {
        boolean existe = false;
        Iterator it = paises.entrySet().iterator();
        while (it.hasNext()) {
            Pais aux = (Pais) it.next();
            if (aux.getNombre().equalsIgnoreCase(pais)) {
                existe = true;
            }
        }
        return existe;
    }

    /**
     * La siguiente función permite visualizar al usuario la información de un país solicitado a través de su nombre
     *
     * @param nombrePaisBuscado es el nombre del país que el usuario desea buscar para obtener su información
     * @return el país encontrado, de lo contrario retorna null
     * @author Abril Galarraga
     */
    public Pais buscarPaisPorNombre(String nombrePaisBuscado) {
        Pais buscado = null;
        Iterator it = paises.entrySet().iterator();
        while (it.hasNext()) {
            Pais aux = (Pais) it.next();
            if (aux.getNombre().equalsIgnoreCase(nombrePaisBuscado)) {
                buscado = aux;
            }
        }
        return buscado;
    }

    /**
     * La siguiente función permite visualizar al usuario la información de un país solicitado a través de su clave
     *
     * @param claveDelPais es la abreviacion de un pais ingresado por el usuario
     * @return el pais encontrado, de lo contrario retorna null
     * @throws ClavePaisInexistenteException en caso de que la clave/abreviacion del país ingresado sea inexistente
     * @author Abril Galarraga
     */
    public Pais buscarPaisPorClave(String claveDelPais) throws ClavePaisInexistenteException {
        Pais buscado = null;
        if (paises.containsKey(claveDelPais)) {
            buscado = paises.get(claveDelPais);
        } else {
            throw new ClavePaisInexistenteException();
        }
        return buscado;
    }

    /**
     * La función buscarAlojamiento permite al usuario filtrar su búsqueda de alojamientos estableciendo una condicion específica
     *
     * @param precioMax es el precio máximo a filtrar
     * @return una coleccion de alojamientos que tengan un precio menor o igual al establecido por el usuario
     * @author AbrilGalarraga
     */
    public HashSet<Alojamiento> buscarAlojamiento(float precioMax) {
        HashSet<Alojamiento> alojamientoPrecioMax = new HashSet<>();
        Iterator<Alojamiento> it = alojamientos.iterator();
        while (it.hasNext()) {
            Alojamiento aux = (Alojamiento) it.next();
            if (aux.getPrecio() <= precioMax) { //Si el alojamiento actual no supera el precio maximo, lo agrega al set
                alojamientoPrecioMax.add(aux);
            }
        }
        return alojamientoPrecioMax;
    }

    /**
     * La función buscarAlojamiento permite al usuario filtrar su búsqueda de alojamientos estableciendo una condicion específica
     *
     * @param capacidadMin es la capacidad minima que deben tener los alojamientos
     * @return una coleccion de alojamientos que tengan una capacidad mayor o igual a la solicitada
     * @author AbrilGalarraga
     */
    public HashSet<Alojamiento> buscarAlojamiento(int capacidadMin) {
        HashSet<Alojamiento> alojamientosAptos = new HashSet<>();
        Iterator<Alojamiento> it = alojamientos.iterator();
        while (it.hasNext()) {
            Alojamiento aux = (Alojamiento) it.next();
            if (aux.getCapacidad() >= capacidadMin) { //Si el alojamiento actual cumple con la capacidad mínima solicitada, lo agrega al set
                alojamientosAptos.add(aux);
            }
        }
        return alojamientosAptos;
    }

    /**
     * La función buscarAlojamiento permite al usuario filtrar su búsqueda de alojamientos estableciendo una condicion específica
     *
     * @param pais es el país donde se sitúa el alojamiento buscado
     * @return una coleccion de alojamientos que se encuentren dentro del país solicitado
     * @throws PaisInexistenteException si el pais ingresado para filtrar la busqueda es inexistente
     * @author AbrilGalarraga
     */
    public HashSet<Alojamiento> buscarAlojamiento(String pais) throws PaisInexistenteException {
        HashSet<Alojamiento> alojamientosDisponibles = new HashSet<>();
        if (verificarPais(pais)) {
            Iterator<Alojamiento> it = alojamientos.iterator();
            while (it.hasNext()) {
                Alojamiento aux = (Alojamiento) it.next();
                if (aux.getPais().equalsIgnoreCase(pais)) { //Si el alojamiento actual se encuentra en el pais solicitado, lo agrega al set
                    alojamientosDisponibles.add(aux);
                }
            }
        } else {
            throw new PaisInexistenteException();
        }
        return alojamientosDisponibles;
    }

    public Reserva reservar(Vuelo vueloAReservar, int cantPasajerosMenores, int cantPasajerosMayores) throws DisponibilidadAgotadaException {
        //Creo una reserva
        Reserva<Vuelo> reserva = null;
        Date diaActual = new Date(); //Fecha de realización de la reserva
        String fecha = String.valueOf(diaActual);

        if (vueloAReservar.verificarDisponibilidad()) {

            int totalPasajeros = cantPasajerosMayores + cantPasajerosMenores;
            if (vueloAReservar.getCantPasajeros() + totalPasajeros <= vueloAReservar.getCapacidadMax()) { //Verifica si el vuelo tiene la cantidad de lugares necesarios para poder establecer la reserva
                vueloAReservar.aumentarCantPasajeros(totalPasajeros); //Incrementa la cantidad de pasajeros del vuelo
                reserva = new Reserva<>(vueloAReservar, vueloAReservar.getId(), fecha, vueloAReservar.getFechaDeLlegada(), vueloAReservar.getFechaDeSalida(), cantPasajerosMenores, cantPasajerosMayores);
            }
        } else{

            throw new DisponibilidadAgotadaException("No hay mas disponibilidad");
        }
        return reserva;
    }
    public void cargarVuelos() ///PARA CARGAR VUELOS POR PARAMETROS
    {
        /*Vuelo vuelo1 = new VueloEjecutivo(20, 30, true, "Buenos Aires", "New York", 7, 8000, Aerolinea.AEROLINEAS_ARGENTINAS, 1, 32000, "31/10/2023", "30/10/2023");
        Vuelo vuelo = new VueloPrimeraClase(40, 50, true, "Madrid", "Destino", 10, 8000, Aerolinea.AIR_EUROPA, 2, 40000, "19/5/2023", "17/5/2023");
        vuelos.add(vuelo);
        vuelos.add(vuelo1);

         */
    }
    public void cargarAlojamientos() ///PARA CARGAR ALOJAMIENTOS POR PARAMETROS
    {
        Alojamiento alojamiento = new Alojamiento(5, "AC", true, "mdp", "arg", 223, "aaaa", 7000, 2, 3);
        alojamientos.add(alojamiento);
    }
}
