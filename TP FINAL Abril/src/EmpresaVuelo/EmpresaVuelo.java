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
import Persona.Usuario.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

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

    //Metodos:


    //Sobre JSON:
    /**
     * El metodo vuelosToJSON permite la serialización de un Vuelo y almacenarlo en un archivo JSON
     * @return un arreglo JSON con la información de todos los vuelos
     * @throws Exception lanzado por el JSONObject
     * @author Nahuel Moron
     */
    public JSONArray vuelosToJSON() throws Exception
    {
        Iterator<Vuelo> it = vuelos.iterator();
        JSONArray jsonArray = new JSONArray();
        while(it.hasNext())
        {
            Vuelo aux = (Vuelo) it.next();
            JSONObject jsonObject = aux.vueloToJson();
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }


    /**
     * La función JSONtoVuelo permite deserializar un JSON que contiene un arreglo de vuelos hacia la clase Vuelo de Java, cargando cada vuelo Java en un archivo.
     * @author Nahuel Moron
     * @throws Exception ya que el JSONObject lanza una excepcion comprobada
     */
    public void JSONtoVuelo() throws Exception
    {
        JSONArray jsonArray= new JSONArray(JsonUtiles.leer("vuelos"));
        for(int i = 0; i<jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int cantPasajeros = jsonObject.getInt("cantPasajeros");
            int capacidadMax = jsonObject.getInt("capacidadMax");
            boolean disponibilidad = jsonObject.getBoolean("disponibilidad");
            Pais origen = (Pais) jsonObject.get("origen");
            Pais destino = (Pais) jsonObject.get("destino");
            double cantidadHoras = jsonObject.getInt("cantidadHoras");
            double distanciaKM = jsonObject.getDouble("distanciaKm");
            Aerolinea aerolinea = (Aerolinea) jsonObject.get("aerolinea");
            int id = jsonObject.getInt("id");
            double precio = jsonObject.getDouble("precio");
            String fechaLlegada = jsonObject.getString("fechaDeLlegada");
            String fechaSalida = jsonObject.getString("fechaDeSalida");
            Vuelo aux = new VueloEconomico(cantPasajeros, capacidadMax, disponibilidad, origen, destino, cantidadHoras, distanciaKM, aerolinea, id, precio, fechaLlegada, fechaSalida);
            vuelos.add(aux);
        }
    }

    /**
     * El método alojamientoToJSON() permite serializar los datos de los alojamientos que maneja la empresa hacia un archivo JSON
     * @return un arreglo JSON el cual contiene todos los alojamientos
     * @author Nahuel Moron
     * @throws Exception lanzado por el JSONObject
     */
    public JSONArray alojamientoToJSON() throws Exception
    {
        Iterator<Alojamiento> it = alojamientos.iterator();
        JSONArray jsonArray = new JSONArray();
        while(it.hasNext())
        {
            Alojamiento aux = (Alojamiento) it.next();
            JSONObject jsonObject = aux.alojamientoToJSON();
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    /**
     * La función JSONtoAlojamiento permite deserializar un JSON que contiene un arreglo de alojamientos hacia la clase Alojamiento de Java, cargando cada alojamiento Java en un archivo.
     * @author Nahuel Moron
     * @throws Exception ya que el JSONObject lanza una excepcion comprobada
     */
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
            String direccion = jsonObject.getString("direccion");
            String servicios = jsonObject.getString("descripcionServicios");
            double precio = jsonObject.getDouble("precio");
            int cantidadServicios = jsonObject.getInt("cantidadServicios");
            int cantidadEstrellas = jsonObject.getInt("cantidadEstrellas");
            int id = jsonObject.getInt("id");
            Alojamiento aux = new Alojamiento(capacidad, nombre, disponibilidad, ciudad, pais, direccion, servicios, precio, cantidadServicios, cantidadEstrellas,id);
            alojamientos.add(aux);
        }
    }


    //Busqueda de Vuelos:
    /**
     * La siguiente función permite buscar los vuelos por la clase
     *
     * @param paisOrigen  es el nombre del pais origen que manda el usuario,
     * @param paisDestino es el nombre del pais destino que manda el usuario
     * @return la coleccion de vuelos encontrados, de lo contrario retorna null
     * @author MateoCuevas
     * @author Abril Galarraga
     */
    public HashSet<Vuelo> buscarVuelo(String paisOrigen, String paisDestino) {
        Pais origen = buscarPaisPorNombre(paisOrigen);
        Pais destino = buscarPaisPorNombre(paisDestino);
        HashSet<Vuelo> vuelosDisponibles = new HashSet<>();
        Iterator<Vuelo> it = vuelos.iterator();
        while (it.hasNext()) { //Recorremos la colección de vuelos:
            Vuelo vuelo = (Vuelo) it.next();
            if (vuelo.isDisponibilidad() && vuelo.getOrigen().equals(origen) && vuelo.getDestino().equals(destino)) { //Verifica que el vuelo este disponible y coincida con el origen y destino solicitado
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
    public HashSet<Vuelo> buscarVuelo(double precioMin, double precioMax, HashSet<Vuelo> setVuelos) {
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
     * @author Abril Galarraga
     */
    public HashSet<Vuelo> buscarVuelo(String clase, HashSet<Vuelo> setVuelos) {
        HashSet<Vuelo> vuelosDisponibles = new HashSet<>();
        Iterator it = setVuelos.iterator();

        while (it.hasNext()) {
            Vuelo vuelo = (Vuelo) it.next();
            if (vuelo.isDisponibilidad() ){
                switch (clase){
                    case "Economico", "economico":
                    if (vuelo instanceof VueloEconomico){
                        vuelosDisponibles.add(vuelo);
                    }
                    break;
                    case "Ejecutivo", "ejecutivo":
                        if (vuelo instanceof VueloEjecutivo){
                            vuelosDisponibles.add(vuelo);
                        }
                        break;
                    case "Primera clase", "primera clase", "Primera Clase":
                        if (vuelo instanceof VueloPrimeraClase){
                            vuelosDisponibles.add(vuelo);
                        }
                        break;
                }
            }
        }
        return vuelosDisponibles;
    }


    /**
     * La siguiente función permite buscar los vuelos por la aerolinea
     *
     * @param aerolinea es el nombre de la aerolinea que manda el usuario,
     * @param setVuelos es la coleccion con los vuelos
     * @return la coleccion de vuelos encontrados, de lo contrario retorna null
     * @author MateoCuevas
     * @author Abril Galarraga
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
     * La siguiente función permite buscar un vuelo segun el id ingresado
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

    //Busqueda de Paises
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


    //Busqueda de Alojamientos

    /**
     * La función buscarAlojamiento permite al usuario filtrar su búsqueda de alojamientos estableciendo una condicion específica
     *
     * @param  nombre el nombre del alojamiento a filtrar
     * @return alojamiento con el nombre deseado
     * @author Mateo Cuevas
     */
    public Alojamiento buscarAlojamiento(String nombre,HashSet <Alojamiento> alojamientosFiltrados) {
        Alojamiento alojamientoABuscar = null;
        Iterator<Alojamiento> it = alojamientosFiltrados.iterator();//recorre la coleccion de alojamientos con el destino elegido
        while (it.hasNext()) {
            Alojamiento aux = (Alojamiento) it.next();
            if (aux.getNombre().equalsIgnoreCase(nombre)) { //Si el alojamiento actual tiene el mismo nombre que el buscado, lo retorna
                aux=alojamientoABuscar;
            }
        }
        return alojamientoABuscar;
    }

    /**
     * La función buscarAlojamiento permite al usuario filtrar su búsqueda de alojamientos estableciendo una condicion específica
     *
     * @param precioMax es el precio máximo a filtrar
     * @return una coleccion de alojamientos que tengan un precio menor o igual al establecido por el usuario
     * @author Abril Galarraga
     * @author Mateo Cuevas
     */
    public HashSet<Alojamiento> buscarAlojamiento(double precioMax,HashSet <Alojamiento> alojamientosFiltrados) {
        HashSet<Alojamiento> alojamientoPrecioMax = new HashSet<>();
        Iterator<Alojamiento> it = alojamientosFiltrados.iterator();//recorre la coleccion de alojamientos con el destino elegido
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
     * @author Abril Galarraga
     * @author Mateo Cuevas
     */
    public HashSet<Alojamiento> buscarAlojamiento(int capacidadMin,HashSet <Alojamiento> alojamientosFiltrados) {
        HashSet<Alojamiento> alojamientosAptos = new HashSet<>();
        Iterator<Alojamiento> it = alojamientosFiltrados.iterator();//recorre la coleccion de alojamientos con el destino elegido
        while (it.hasNext()) {
            Alojamiento aux = it.next();
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



    //Reservas:

    /**
     * La siguiente función permite crear una reserva a partir de un Vuelo ya preseleccionado por el usuario, sabiendo la cantidad de pasajeros a reservar (mayores/menores).
     * El método retorna la reserva en caso de realizarse exitosamente, de lo contrario puede lanzar una excepcion.
     * @param vueloAReservar es el vuelo que se desea reservar
     * @param cantPasajerosMenores representa la cantidad (en numeros) de personas menores de edad que van a reservarse en el vuelo elegido
     * @param cantPasajerosMayores representa la cantidad (en numeros) de personas mayores de edad que van a reservarse en el vuelo elegido
     * @return la reserva creada dentro del método
     * @throws DisponibilidadAgotadaException si la cantidad de pasajeros totales a reservar supera la cantidad de lugares disponibles del vuelo seleccionado.
     * @author Abril Galarraga
     */
    public Reserva<Vuelo> reservar(Usuario usuario, Vuelo vueloAReservar, int cantPasajerosMenores, int cantPasajerosMayores) throws DisponibilidadAgotadaException {
        //Creo una reserva
        Reserva<Vuelo> reserva = null;
        Date diaActual = new Date(); //Fecha de realización de la reserva
        String fecha = String.valueOf(diaActual);

        if (vueloAReservar.verificarDisponibilidad()) {

            int totalPasajeros = cantPasajerosMayores + cantPasajerosMenores;
            if (vueloAReservar.getCantPasajeros() + totalPasajeros <= vueloAReservar.getCapacidadMax()) { //Verifica si el vuelo tiene la cantidad de lugares necesarios para poder establecer la reserva
                vueloAReservar.aumentarCantPasajeros(totalPasajeros); //Incrementa la cantidad de pasajeros del vuelo
                if (vueloAReservar.getCantPasajeros()== vueloAReservar.getCapacidadMax()){ //Si el vuelo se llenó, lo situamos como no disponible
                    vueloAReservar.modificarDisponibilidad();
                }
                 reserva = new Reserva<Vuelo>(vueloAReservar, vueloAReservar.getId(), fecha, vueloAReservar.getFechaDeLlegada(), vueloAReservar.getFechaDeSalida(), cantPasajerosMenores, cantPasajerosMayores);
                boolean agregadoAlUsuario = usuario.agregarReserva(reserva); //Se le agrega al usuario la reserva creada
            }
        } else{

            throw new DisponibilidadAgotadaException("No hay mas disponibilidad");
        }
        return reserva;
    }

    /**
     *La siguiente función permite crear una reserva a partir de un alojamiento ya preseleccionado por el usuario, sabiendo la cantidad de huespedes a reservar (mayores/menores).
     * El método retorna la reserva en caso de realizarse exitosamente, de lo contrario puede lanzar una excepcion.
     * @param alojamientoAReservar es el alojamiento que el cliente desea reservar
     * @param huespedesMenores es la cantidad de huespedes menores de edad que se cuentan dentro del alojamiento
     * @param huespedesMayores es la cantidad de huespedes mayores de edad que se cuentan dentro del alojamiento
     * @param fechaLLegada fecha de llegada de los clientes al alojamiento
     * @param fechaSalida fecha de salida de los clientes al alojamiento
     * @return la reserva creada
     * @throws DisponibilidadAgotadaException en caso de que el alojamiento a reservar no se encuentre disponible o no posee la capacidad necesaria para su reserva
     * @author Abril Galarraga
     */
    public Reserva<Alojamiento> reservar (Usuario usuario, Alojamiento alojamientoAReservar, int huespedesMenores, int huespedesMayores, String fechaLLegada, String fechaSalida) throws DisponibilidadAgotadaException{
        //Creo una reserva
        Reserva<Alojamiento> reserva = null;
        Date diaActual = new Date(); //Fecha de realización de la reserva
        String fecha = String.valueOf(diaActual);
        if (alojamientoAReservar.verificarDisponibilidad()) {

            int totalAHospedar = huespedesMayores + huespedesMenores;
            if (alojamientoAReservar.getCapacidad() >= totalAHospedar) { //Verifica si el alojamiento tiene la cantidad de lugares necesarios para poder establecer la reserva
                alojamientoAReservar.modificarDisponibilidad(); //Sitúa como no disponible el alojamiento a partir de ahpra
                reserva = new Reserva<>(alojamientoAReservar, alojamientoAReservar.getId(), fecha, fechaLLegada, fechaSalida, huespedesMenores, huespedesMayores);
                boolean agregadoAlUsuario = usuario.agregarReserva(reserva); //Se le agrega al usuario la reserva creada
            }
        } else{
            throw new DisponibilidadAgotadaException("No hay mas disponibilidad");
        }
        return reserva;
    }


    //Mostrar listas:
    /** La siguiente función imprime las características más importantes de cada país
     * @author Abril Galarraga
     * @return un String que contiene la información de todos los países
     */
    public StringBuilder mostrarListaPaises (){
        StringBuilder info = new StringBuilder();
        Iterator<Map.Entry<String, Pais>> it = paises.entrySet().iterator();
        while (it.hasNext()){
            Pais unPais = (Pais) it.next();
            info.append(unPais.toString()).append("\n");
        }
        return info;
    }


    /** La siguiente función imprime las características de cada alojamiento ofrecido por la empresa
     * @author Abril Galarraga
     * @return un String que contiene la información de todos los alojamientos que ofrece la empresa
     */
    public StringBuilder mostrarListaAlojamientos (){
        StringBuilder info = new StringBuilder();
        Iterator<Alojamiento> it = alojamientos.iterator();
        while (it.hasNext()){
            Alojamiento unAlojamiento = (Alojamiento) it.next();
            info.append(unAlojamiento.toString()).append("\n");
        }
        return info;
    }


}
