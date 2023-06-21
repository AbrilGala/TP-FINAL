package EmpresaVuelo.Vuelos;

import APIS.Paises.Pais;
import EmpresaVuelo.EmpresaVuelo;
import EmpresaVuelo.Interfaces.ICalcularPrecio;
import EmpresaVuelo.Interfaces.IVerificarDisponibilidad;
import EmpresaVuelo.Vuelos.Aerolineas.Aerolinea;
import netscape.javascript.JSObject;
import org.json.JSONException;
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
        cantidadHoras = 0;
        distanciaKm = 0;
        aerolinea = Aerolinea.AEROLINEAS_ARGENTINAS;
        id = 0;
        origen = null;
        destino = null;
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

    public Pais getOrigen() {
        return origen;
    }

    public Pais getDestino() {
        return destino;
    }

    public double getCantidadHoras() {
        return cantidadHoras;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }


    public int getId() {
        return id;
    }

    public double getPrecio() {
        return precio;
    }

    public String getFechaDeLlegada() {
        return fechaDeLlegada;
    }

    public String getFechaDeSalida() {
        return fechaDeSalida;
    }

    //Setter
    private void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    private void setCantPasajeros(int cantPasajeros) {
        this.cantPasajeros = cantPasajeros;
    }

    //Imprime la información de un vuelo:
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
        vuelo.put("pais origen", origen.paisToJSON());
        vuelo.put("pais destino", destino.paisToJSON());
        vuelo.put("cantidadHoras", cantidadHoras);
        vuelo.put("distanciaKm", distanciaKm);
        vuelo.put("aerolinea", aerolinea.getNombre());
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
    public static Vuelo verificarVuelo(String tipoVuelo, int cantPasajeros, int capacidadMax, boolean disponibilidad, Pais paisOrigen, Pais paisDestino, int cantidadHoras, double distanciaKM,Aerolinea aerolinea, int id, double precio, String fechaLlegada, String fechaSalida)
    {
        Vuelo vuelo;
        if(tipoVuelo.equals("Economico"))
        {
            vuelo = new VueloEconomico(cantPasajeros, capacidadMax, disponibilidad, paisOrigen, paisDestino, cantidadHoras, distanciaKM, aerolinea , id, precio, fechaLlegada, fechaSalida);
        }else
        {
            if(tipoVuelo.equals("Ejecutivo"))
            {
                vuelo = new VueloEjecutivo(cantPasajeros, capacidadMax, disponibilidad, paisOrigen, paisDestino, cantidadHoras, distanciaKM, aerolinea, id, precio, fechaLlegada, fechaSalida);
            }else
            {
                vuelo = new VueloPrimeraClase(cantPasajeros, capacidadMax, disponibilidad, paisOrigen, paisDestino, cantidadHoras, distanciaKM, aerolinea, id, precio, fechaLlegada, fechaSalida);
            }
        }
        return vuelo;
    }
    public static Vuelo JSONAvuelos(JSONObject jsonObject) throws JSONException
    {
        EmpresaVuelo aux = new EmpresaVuelo();
        int cantPasajeros = jsonObject.getInt("cantPasajeros");
        int capacidadMax = jsonObject.getInt("capacidadMax");
        boolean disponibilidad = jsonObject.getBoolean("disponibilidad");
        JSONObject origen = jsonObject.getJSONObject("pais origen");
        Pais paisOrigen = Pais.JSONToPais(origen);
        JSONObject destino = jsonObject.getJSONObject("pais destino");
        Pais paisDestino = Pais.JSONToPais(destino);
        int cantidadHoras = jsonObject.getInt("cantidadHoras");
        double distanciaKM = jsonObject.getDouble("distanciaKm");
        String aerolinea = jsonObject.getString("aerolinea");
        Aerolinea aerolinea1 = Aerolinea.verificarAerolinea(aerolinea);
        int id = jsonObject.getInt("id");
        double precio = jsonObject.getDouble("precio");
        String tipoVuelo = jsonObject.getString("tipoVuelo");
        String fechaLlegada = jsonObject.getString("fechaDeLlegada");
        String fechaSalida = jsonObject.getString("fechaDeSalida");
        Vuelo vuelo = verificarVuelo(tipoVuelo, cantPasajeros, capacidadMax, disponibilidad, paisOrigen, paisDestino, cantidadHoras, distanciaKM, aerolinea1, id, precio, fechaLlegada, fechaSalida);
        return vuelo;
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
