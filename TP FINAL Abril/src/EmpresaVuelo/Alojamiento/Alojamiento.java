package EmpresaVuelo.Alojamiento;

import EmpresaVuelo.Interfaces.ICalcularPrecio;
import EmpresaVuelo.Interfaces.IVerificarDisponibilidad;
import org.json.JSONObject;

public class Alojamiento implements ICalcularPrecio, IVerificarDisponibilidad {
    //Atributos
    private int capacidad; //Habitaciones
    private String nombre;
    private boolean disponibilidad; //Si esta ocupado o no
    private String ciudad;
    private String pais;
    private String direccion;
    private String descripcionServicios;
    private double precio;
    private int cantidadServicios;
    private int cantidadEstrellas;

    //Constructores

    //Constructor Completo
    public Alojamiento(int capacidad, String nombre, boolean disponibilidad, String ciudad, String pais, String direccion, String descripcionServicios, double precio, int cantidadServicios, int cantidadEstrellas) {
        this.capacidad = capacidad;
        this.nombre = nombre;
        this.disponibilidad = disponibilidad;
        this.ciudad = ciudad;
        this.pais = pais;
        this.direccion = direccion;
        this.descripcionServicios = descripcionServicios;
        this.precio = precio;
        this.cantidadServicios = cantidadServicios;
        this.cantidadEstrellas = cantidadEstrellas;
    }

    //Constructor vacío
    public Alojamiento (){
        capacidad = 0;
        nombre = "";
        disponibilidad = true;
        ciudad ="";
        pais = "";
        direccion = "";
        descripcionServicios = "";
        precio = 0;
        cantidadServicios = 0;
        cantidadEstrellas = 0;
    }

    //Getters

    public int getCapacidad() {
        return capacidad;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }

    public String getDescripcionServicios() {
        return descripcionServicios;
    }

    public int getCantidadServicios() {
        return cantidadServicios;
    }

    public int getCantidadEstrellas() {
        return cantidadEstrellas;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getPrecio() {
        return precio;
    }

    //Setter:
    private void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    //Metodos:

    /**Funcion que calcula el precio de un alojamiento segun la capacidad de hospedaje y los servicios que ofrece
     * La función es implementada de la interfaz ICalcularPrecio
     * @author Abril Galarraga
     * @return el precio total de un alojamiento
     */
    @Override
    public double calcularPrecio() {
        double precioTotal = 30000; //Por defecto el hospedaje sale 30000 (capacidad max 2 personas)
        //Segun la capacidad del hospedaje:
       if (getCapacidad() > 2 && getCapacidad() <=4) {  //Si la capacidad maxima es de 4 personas
            precioTotal += 10000;
        } else { //Si la capacidad maxima supera los 4 huespedes
           precioTotal += 15000;
       }
       //Segun la cantidad de servicios:
        if (getCantidadServicios() >=3){
            precioTotal += 15000;
        } else if (getCantidadServicios() >=10){
            precioTotal += 22000;
        } else {
            precioTotal += 28000;
        }
        return precioTotal;
    }


    /** Funcion que verifica la disponibilidad de un vuelo (si no se lleno su capacidad maxima) y puede ser reservado
     * @author Abril Galarraga
     * @return true si el alojamiento se encuentra disponible para su reserva, de lo contrario retorna false
     */
    @Override
    public boolean verificarDisponibilidad() {
        return disponibilidad;
    }


    /**Funcion que modifica el atributo de disponibilidad por su valor contrario
     * Es implementada de la interfaz IVerificarDisponibilidad
     * @author Abril Galarraga
     */
    @Override
    public void modificarDisponibilidad() {
        setDisponibilidad(!isDisponibilidad());
    }


    //JSON:
    /**
     * El método alojamientoToJSON permite almacenar las características de un alojamiento a un objeto JSON:
     * @return un objeto JSON
     * @throws Exception lanzado por el JSONObject
     * @author Nahuel Moron
     */
    public JSONObject alojamientoToJSON() throws Exception
    {
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("capacidad", capacidad);
        jsonObject.put("nombre", nombre);
        jsonObject.put("disponibilidad", disponibilidad);
        jsonObject.put("ciudad", ciudad);
        jsonObject.put("pais", pais);
        jsonObject.put("direccion", direccion);
        jsonObject.put("descripcionServicios", descripcionServicios);
        jsonObject.put("precio", precio);
        jsonObject.put("cantidadServicios", cantidadServicios);
        jsonObject.put("cantidadEstrellas", cantidadEstrellas);
        return jsonObject;
    }
}
