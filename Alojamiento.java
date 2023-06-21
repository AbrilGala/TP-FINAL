package Empresa.Alojamiento;

import Empresa.Interfaces.ICalcularPrecio;
import Empresa.Interfaces.IVerificarDisponibilidad;
import org.json.JSONException;
import org.json.JSONObject;

public class Alojamiento implements ICalcularPrecio, IVerificarDisponibilidad {
    //Atributos
    private int capacidad; //Habitaciones
    private String nombre;
    private boolean disponibilidad; //Si esta ocupado o no
    private String ciudad;
    private String pais;
    private double direccion;
    private String descripcionServicios;
    private double precio;
    private int cantidadServicios;
    private int cantidadEstrellas;
    private int id;

    //Constructor
    public Alojamiento(int capacidad, String nombre, boolean disponibilidad, String ciudad, String pais, double direccion, String descripcionServicios, double precio, int cantidadServicios, int cantidadEstrellas,int id) {
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
        this.id=id;
    }

    public Alojamiento (){
        capacidad = 0;
        nombre = "";
        disponibilidad = true;
        ciudad ="";
        pais = "";
        direccion = 0;
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

    public double getDireccion() {
        return direccion;
    }

    public String getDescripcionServicios() {
        return descripcionServicios;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidadServicios() {
        return cantidadServicios;
    }

    public int getCantidadEstrellas() {
        return cantidadEstrellas;
    }

    public int getId() {
        return id;
    }

    //Setter
    private void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }


    //Metodos

    //Funcion que calcula el precio de un alojamiento segun la capacidad de hospedaje y los servicios que ofrece
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

    //Funcion que verifica la disponibilidad de un vuelo (si no se lleno su capacidad maxima)
    @Override
    public boolean verificarDisponibilidad() {
        return disponibilidad;
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
    public static Alojamiento JSONToAlojamiento(JSONObject jsonObject) throws JSONException
    {
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
        int id = jsonObject.getInt("id");
        Alojamiento aux = new Alojamiento(capacidad, nombre, disponibilidad, ciudad, pais, direccion, servicios, precio, cantidadServicios, cantidadEstrellas,id);
        return aux;
    }
    public JSONObject alojamientoToJSON() throws JSONException
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
        jsonObject.put("id", id);
        return jsonObject;
    }

    @Override
    public String toString() {
        String info = "";
        info += "Nombre: "+nombre+"\nCapacidad: "+capacidad+"\nDisponibilidad: "+disponibilidad+"\nCiudad: "+ciudad+"\nPais: "+pais+"\nDireccion: "+direccion+"\nDescripcion de servicios: "+descripcionServicios+"\nPrecio: "+precio+"\nCantidad de servicios: "+cantidadServicios+"\nCantidad de estrellas: "+cantidadEstrellas+"\n";
        return info;
    }
}

