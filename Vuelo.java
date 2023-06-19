package EmpresaVuelo.Vuelos;

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
    private String origen;
    private String destino;
    private float cantidadHoras;
    private float distanciaKm;
    private Aerolinea aerolinea;
    private int id;
    private float precio;

    //Constructores:


    public Vuelo(int cantPasajeros, int capacidadMax, boolean disponibilidad, String origen, String destino, float cantidadHoras, float distanciaKm, Aerolinea aerolinea, int id, float precio) {
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
    }
    public void aumentarCantPasajeros(int cantPasajeros)
    {
        this.cantPasajeros = this.cantPasajeros + cantPasajeros;
    }
    public void disminuirCantPasajeros(int cantPasajeros)
    {
        this.cantPasajeros = this.cantPasajeros - cantPasajeros;
    }
    public Vuelo (){
        cantPasajeros = 0;
        capacidadMax = 0;
        disponibilidad = true;
        origen = "";
        destino = "";
        cantidadHoras = 0;
        distanciaKm = 0;
        aerolinea = Aerolinea.AEROLINEAS_ARGENTINAS;
        id = 0;
        precio = 0;
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

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public float getCantidadHoras() {
        return cantidadHoras;
    }

    public float getDistanciaKm() {
        return distanciaKm;
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public int getId() {
        return id;
    }

    public float getPrecio() {
        return precio;
    }

    //Setter
    private void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    //Imprime la información de un vuelo:
    @Override
    public String toString() {
        return "Vuelo n°" +id+" :"+
                "\ncantidad de pasajeros reservados: " + cantPasajeros +
                "\ncapacidad maxima de pasajeros: " + capacidadMax +
                "\ndisponibilidad del vuelo: " + disponibilidad +
                "\norigen: '" + origen + '\'' +
                "\ndestino: '" + destino + '\'' +
                "\ncantidad de horas (duracion): " + cantidadHoras +
                "\ndistancia en Km:" + distanciaKm +
                "\nnombre de la aerolinea: '" + aerolinea + '\'' +
                "\nprecio: $" + precio;
    }

    //Funcion que retorna la informacion de un vuelo
    public String verVuelo (){
        return  toString();
    }

    //Funcion que modifica al atributo precio segun el tipo de vuelo y la distancia en km
    private void establecerPrecio (){
        precio = calcularPrecio();
    }

    //Funcion que realiza un JSON de un vuelo
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
        return vuelo;
    }

    //Funcion que verifica la disponibilidad de un vuelo (si no se lleno su capacidad maxima)
    @Override
    public boolean verificarDisponibilidad(int cantPasajerosMax) {
        boolean disponibile = false;
        if (cantPasajeros < capacidadMax){
            if(cantPasajeros+cantPasajerosMax < capacidadMax)
            {
                disponibile = true;
            }
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

}
