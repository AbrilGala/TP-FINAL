package Empresa;

import Apis.ControladoraPaises;
import Apis.Excepciones.ClavePaisInexistenteException;
import Apis.Excepciones.PaisInexistenteException;
import Apis.JsonUtiles;
import Apis.Pais;
import Empresa.Excepciones.DisponibilidadAgotadaException;
import Empresa.Vuelos.*;
import Empresa.Alojamiento.Alojamiento;
import LogIn.Usuario;
import com.sun.source.tree.Tree;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.util.*;


public class EmpresaGestora {


    //Atributos
    private HashSet<Vuelo> vuelos;
    private HashSet<Alojamiento> alojamientos;
    private TreeMap<String, Pais> paises; //Cargado desde un JSON

    //Constructor
    public EmpresaGestora() {
        vuelos = new HashSet<>();
        JSONtoVuelo();
        alojamientos = new HashSet<>();
        JSONtoAlojamiento();
        paises=cargarPaises();
    }

    public TreeMap<String, Pais>cargarPaises()
    {
        ControladoraPaises con=new ControladoraPaises();
        try {
            return con.JSONtoJava();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void cargaDeVuelos (){
        Pais pais1 = buscarPaisPorNombre("argentina");
        Pais pais = buscarPaisPorNombre("Croatia");
        Pais pais2 = buscarPaisPorNombre("colombia");
        Pais pais3 = buscarPaisPorNombre("congo");
        Pais pais4 = buscarPaisPorNombre("greece");
        Vuelo vuelo1 = new VueloEjecutivo(20, 30, true, pais, pais1, 4, 5000, Aerolinea.JET_SMART, 1, 32000, "31/10/2023", "30/10/2023");
        Vuelo vuelo5 = new VueloPrimeraClase(40, 50, true, pais1, pais2, 10, 4500, Aerolinea.AEROLINEAS_ARGENTINAS, 2, 30000, "19/5/2023", "17/5/2023");
        Vuelo vuelo2 = new VueloEconomico(80, 100, true, pais3, pais4, 3, 2800, Aerolinea.AEROMEXICO, 3, 18000, "12/3/2023", "12/3/2023");
        Vuelo vuelo3 = new VueloEjecutivo(10, 30, true, pais4, pais1, 2, 1700, Aerolinea.AIR_EUROPA, 4, 9000, "28/5/2023", "28/5/2023");
        Vuelo vuelo4 = new VueloPrimeraClase(48, 50, true, pais4, pais, 4, 3400, Aerolinea.KOREAN_AIR, 5, 19000, "21/2/2023","22/2/2023");

        try {
            JSONObject p1 = vuelo1.vueloToJSON();
            JSONObject p2 = vuelo2.vueloToJSON();
            JSONObject p3 = vuelo3.vueloToJSON();
            JSONObject p4 = vuelo4.vueloToJSON();
            JSONObject p5 = vuelo5.vueloToJSON();
            JSONArray vuelos = new JSONArray();
            vuelos.put(p1);
            vuelos.put(p2);
            vuelos.put(p3);
            vuelos.put(p4);
            vuelos.put(p5);

            JsonUtiles.grabar(vuelos, "vuelos");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public HashSet<Vuelo> getVuelos() {
        return vuelos;
    }

    public HashSet<Alojamiento> getAlojamientos() {
        return alojamientos;
    }

    public TreeMap<String, Pais> getPaises() {
        return paises;
    }
    //toString


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
    public StringBuilder mostrarListaAlojamientos (HashSet<Alojamiento> setAlojamientos){
        StringBuilder info = new StringBuilder();
        Iterator<Alojamiento> it = setAlojamientos.iterator();
        while (it.hasNext()){
            Alojamiento unAlojamiento = (Alojamiento) it.next();
            info.append(unAlojamiento.toString()).append("\n");
        }
        return info;
    }


    //toJSON

    /**
     * La siguiente función permite pasar de una coleccion de vuelos en Java a un arreglo JSON
     * @return retorna un JSONArray con
     * @author MateoCuevas & NahuelMoron
     */
    public JSONArray vuelosToJSON() throws JSONException
    {
        Iterator it = vuelos.iterator();
        JSONArray jsonArray = new JSONArray();
        while(it.hasNext())
        {
            Vuelo aux = (Vuelo) it.next();
            JSONObject jsonObject = aux.vueloToJSON();
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
    /**
     * La siguiente función permite pasar de una coleccion de alojamientos en Java a un arreglo JSON
     * @author MateoCuevas & NahuelMoron
     */
    public JSONArray alojamientoToJSON() throws JSONException
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


    //JSON to Java

    /**
     * La siguiente función permite pasar de un JSON a Vuelo en Java
     * @author MateoCuevas & NahuelMoron
     */
    public void JSONtoVuelo()
    {
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONArray(JsonUtiles.leer("vuelos"));
            for(int i = 0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Vuelo aux = Vuelo.JSONToVuelos(jsonObject);
                vuelos.add(aux);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * La siguiente función permite pasar de un JSON a alojamiento en Java
     * @author MateoCuevas & NahuelMoron
     */
    public void JSONtoAlojamiento()
    {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(JsonUtiles.leer("alojamientos"));
            for(int i = 0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Alojamiento aux=Alojamiento.JSONToAlojamiento(jsonObject);
                alojamientos.add(aux);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    //Metodos:


    //Verificaciones Paises


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
    public Pais buscarPaisPorNombre(String nombrePaisBuscado){
        Pais buscado =null;
        Iterator it = paises.entrySet().iterator();
        for (Map.Entry<String, Pais> entry : paises.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(nombrePaisBuscado)) {
                buscado=entry.getValue();
            }
        }
        return buscado;
    }

    //Busqueda Vuelos


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
            if (aux.getPrecio() <= precioMax) {//Si el alojamiento actual no supera el precio maximo, lo agrega al set
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
    public HashSet<Alojamiento> buscarAlojamiento(Pais pais)  {
        HashSet<Alojamiento> alojamientosDisponibles = new HashSet<>();
            Iterator<Alojamiento> it = alojamientos.iterator();
            while (it.hasNext()) {
                Alojamiento aux = (Alojamiento) it.next();
                if (aux.getPais().equals(pais)) { //Si el alojamiento actual se encuentra en el pais solicitado, lo agrega al set
                    alojamientosDisponibles.add(aux);
                }
            }
        return alojamientosDisponibles;
    }
    /**
     * La función buscarAlojamientoId permite al usuario filtrar su búsqueda de alojamientos estableciendo una condicion específica
     *
     * @param id es el id del alojamiento buscado
     * @return un alojamiento determinado ya que el id es unico de cada alojamiento
     * @author MateoCuevas
     */
    public Alojamiento buscarAlojamientoId(int id, HashSet<Alojamiento> setAlojamientos) {
        Alojamiento alojamientoAReservar = null;
        Iterator it = setAlojamientos.iterator();
        while (it.hasNext()) {
            Alojamiento alojamiento = (Alojamiento) it.next();
            if (alojamiento.getId() == id) {
                alojamientoAReservar = alojamiento;
            }
        }
        return alojamientoAReservar;
    }

    //Reservas


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
                reserva = new Reserva<Vuelo>(vueloAReservar, usuario.getCantidadReservas(), fecha, vueloAReservar.getFechaDeLlegada(), vueloAReservar.getFechaDeSalida(), cantPasajerosMenores, cantPasajerosMayores);
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
                reserva = new Reserva<>(alojamientoAReservar, usuario.getCantidadReservas(), fecha, fechaLLegada, fechaSalida, huespedesMenores, huespedesMayores);
                boolean agregadoAlUsuario = usuario.agregarReserva(reserva); //Se le agrega al usuario la reserva creada
            }
        } else{
            throw new DisponibilidadAgotadaException("No hay mas disponibilidad");
        }
        return reserva;
    }
    /**
     * El método cancelarReserva sirve para cancelar una determinada reserva que tenga el usuario
     * @param id es el id de la reserva que quiere cancelar
     * @param usuario es el usuario actual, el cual va a realizar la dada de baja
     * @author Mateo Cuevas
     * @author Abril Galarraga
     */

    public boolean cancelarReserva(int id,Usuario usuario) {
        boolean eliminado = false;
        Reserva reservaACancelar = usuario.buscarReservasPorId(id);
        if (reservaACancelar.getReserva() instanceof Vuelo) {
            Vuelo vueloCancelado = buscarVuelo(((Vuelo) reservaACancelar.getReserva()).getId(), vuelos);
            vueloCancelado.disminuirCantPasajeros(reservaACancelar.getCantMayoresDeEdad() + reservaACancelar.getCantMenoresDeEdad());
            if (vueloCancelado.verificarDisponibilidad() && !vueloCancelado.isDisponibilidad()) { //Si el vuelo no se encontraba disponible pero ahora posee lugares disponibles para su reserva:
                vueloCancelado.modificarDisponibilidad(); //Pone al vuelo como disponible
            }
            eliminado = usuario.eliminarReserva(reservaACancelar);
        }else if(reservaACancelar.getReserva() instanceof Alojamiento){
            Alojamiento alojamientoACancelar = buscarAlojamientoId(reservaACancelar.getId(),alojamientos);
            alojamientoACancelar.modificarDisponibilidad();
            eliminado = usuario.eliminarReserva(reservaACancelar);
        }
        return eliminado;
    }



    public void cargaArchivoAlojamientos()throws JSONException{

        Alojamiento a1=new Alojamiento(4,"Cabaña Los Troncos",true,"Buenos Aires",buscarPaisPorNombre("argentina"),"Alem 2000","calefaccion,tele,pitela,gym",4,4,1);
        Alojamiento a2=new Alojamiento(8,"Hotel Barcelona",true,"Madrid",buscarPaisPorNombre("spain"),"Rodrigues 3500","playstation,tele,pitela,gym,cancha de tenis",5,3,2);
        Alojamiento a3=new Alojamiento(10,"Hotel Manchester",true,"Hong Kong",buscarPaisPorNombre("china"),"Bibe 7000","desayuno,tv,gym,cancha de golf",4,5,3);
        Alojamiento a4=new Alojamiento(3,"Hostel Caracas",false,"lima",buscarPaisPorNombre("peru"),"Guille 200","tv,gym",2,2,4);
        try {
            JSONObject jsonObject1=a1.alojamientoToJSON();
            JSONObject jsonObject2=a2.alojamientoToJSON();
            JSONObject jsonObject3=a3.alojamientoToJSON();
            JSONObject jsonObject4=a4.alojamientoToJSON();
            JSONArray jsonArray=new JSONArray();
            jsonArray.put(jsonObject1);
            jsonArray.put(jsonObject2);
            jsonArray.put(jsonObject3);
           jsonArray.put(jsonObject4);
            JsonUtiles.grabar(jsonArray,"alojamientos");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}

