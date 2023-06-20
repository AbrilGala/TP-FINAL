package EmpresaVuelo.Vuelos;

import APIS.Paises.Pais;
import EmpresaVuelo.Interfaces.ICalcularPrecio;
import EmpresaVuelo.Interfaces.IVerificarDisponibilidad;
import EmpresaVuelo.Vuelos.Aerolineas.Aerolinea;
import netscape.javascript.JSObject;
import org.json.JSONObject;

public abstract class Vuelo implements ICalcularPrecio, IVerificarDisponibilidad{
    //Atributos
    private int cantPasajeros;
    private int capacidadMax; //Cantidad maxima de pasajeros que puede tener un vuelo
    private boolean disponibilidad; //Si se encuentra disponible para volar
    private Pais origen;
    private Pais destino;
    private double cantidadHoras;
    private double distanciaKm;
    private Aerolinea aerolinea;
    private int id;
    private double precio;
    private String fechaDeLlegada;
    private String fechaDeSalida;

    //Constructores:

    //Constructor completo
    public Vuelo(int cantPasajeros, int capacidadMax, boolean disponibilidad, Pais origen, Pais destino, double cantidadHoras, double distanciaKm, Aerolinea aerolinea, int id, double precio, String fechaDeLlegada, String fechaDeSalida) {
        this.cantPasajeros = cantPasajeros;
        this.capacidadMax = capacidadMax;
        this.disponibilidad = disponibilidad;
        this.origen = origen;
        this.destino = destino;
        this.cantidadHoras = cantidadHoras;
        this.distanciaKm = distanciaKm;
        this.aerolinea = aerolinea;
        this.id = id;
        this.precio = precio;
        this.fechaDeLlegada = fechaDeLlegada;
        this.fechaDeSalida =fechaDeSalida;
    }

    //Constructor completo menos el precio (se genera automaticamente)
    public Vuelo(int cantPasajeros, int capacidadMax, boolean disponibilidad, Pais origen, Pais destino, double cantidadHoras, double distanciaKm, Aerolinea aerolinea, int id, String fechaDeLlegada, String fechaDeSalida) {
        this.cantPasajeros = cantPasajeros;
        this.capacidadMax = capacidadMax;
        this.disponibilidad = disponibilidad;
        this.origen = origen;
        this.destino = destino;
        this.cantidadHoras = cantidadHoras;
        this.distanciaKm = distanciaKm;
        this.aerolinea = aerolinea;
        this.id = id;
        this.fechaDeLlegada = fechaDeLlegada;
        this.fechaDeSalida =fechaDeSalida;
        establecerPrecio();
    }

    //Constructor vacío
    public Vuelo (){
        cantPasajeros = 0;
        capacidadMax = 0;
        disponibilidad = true;
        origen = new Pais();
        destino = new Pais();
        cantidadHoras = 0;
        distanciaKm = 0;
        aerolinea = Aerolinea.AEROLINEAS_ARGENTINAS;
        id = 0;
        establecerPrecio();
        fechaDeLlegada = "";
        fechaDeSalida = "";
    }

    //Getters
    public int getCantPasajeros() {
        return cantPasajeros;
    }

    public int getCapacidadMax() {
        return capacidadMax;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public Pais getOrigen (){
        return origen;
    }

    public Pais getDestino (){
        return destino;
    }
    public double getCantidadHoras() {
        return cantidadHoras;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public double getPrecio() {
        return precio;
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public int getId() {
        return id;
    }

    public String getFechaDeLlegada() {
        return fechaDeLlegada;
    }

    public String getFechaDeSalida() {
        return fechaDeSalida;
    }


    //Setters
    private void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    private void setCantPasajeros(int cantPasajeros) {
        this.cantPasajeros = cantPasajeros;
    }

    //Métodos


    /**Imprime la información de un vuelo:
     * @author Abril Galarraga
     * @return un String con las características de un vuelo
     */
    @Override
    public String toString() {
        return "Vuelo n°" +id+" :"+
                "\nfecha de salida: "+fechaDeSalida+
                "\ncantidad de pasajeros reservados: " + cantPasajeros +
                "\ncapacidad maxima de pasajeros: " + capacidadMax +
                "\ndisponibilidad del vuelo: " + disponibilidad +
                "\norigen: '" + origen + '\'' +
                "\ndestino: '" + destino + '\'' +
                "\ncantidad de horas (duracion): " + cantidadHoras +
                "\ndistancia en Km:" + distanciaKm +
                "\nnombre de la aerolinea: '" + aerolinea + '\'' +
                "\nfecha de llegada: "+fechaDeLlegada+
                "\nprecio: $" + precio;
    }


    //Funcion que modifica al atributo precio segun el tipo de vuelo y la distancia en km
    private void establecerPrecio (){
        precio = calcularPrecio();
    }

    /** Funcion que realiza un objeto JSON de un vuelo
     * @author Abril Galarraga
     * @return un objeto JSON con los datos de un vuelo
     * @throws Exception
     */
    public JSONObject vueloToJson () throws Exception{
        JSONObject vuelo = new JSONObject();
        vuelo.put("cantPasajeros", cantPasajeros);
        vuelo.put("capacidadMax", capacidadMax);
        vuelo.put("disponibilidad",disponibilidad);
        vuelo.put("origen", origen);
        vuelo.put("destino", destino);
        vuelo.put("cantidadHoras", cantidadHoras);
        vuelo.put("distanciaKm", distanciaKm);
        vuelo.put("aerolinea", aerolinea);
        vuelo.put("id", id);
        vuelo.put("precio", precio);
        vuelo.put("fechaDeLlegada", fechaDeLlegada);
        vuelo.put("fechaDeSalida", fechaDeSalida);
        return vuelo;
    }


    /**Funcion que verifica la disponibilidad de un vuelo (si no se lleno su capacidad maxima)
     * @author Abril Galarraga
     * @return true si el vuelo se encuentra disponible, de lo contrario retorna false
     */
    @Override
    public boolean verificarDisponibilidad() {
        boolean disponibile = false;
        if (cantPasajeros < capacidadMax){
                disponibile = true;
        }
        return disponibile;
    }


    //Funcion que modifica el atributo de disponibilidad por su valor contrario
    @Override
    public void modificarDisponibilidad() {
        if (isDisponibilidad()){
            setDisponibilidad(false);
        } else {
            setDisponibilidad(true);
        }
    }
    public void aumentarCantPasajeros(int nuevosPasajeros)
    {
        setCantPasajeros(cantPasajeros+nuevosPasajeros);
    }
    public void disminuirCantPasajeros(int nuevosPasajeros)
    {
        setCantPasajeros(cantPasajeros-nuevosPasajeros);
    }

}
