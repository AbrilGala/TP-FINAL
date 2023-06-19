package EmpresaVuelo.Alojamiento;

import EmpresaVuelo.Interfaces.ICalcularPrecio;
import EmpresaVuelo.Interfaces.IVerificarDisponibilidad;

public class Alojamiento implements ICalcularPrecio, IVerificarDisponibilidad {
    //Atributos
    private int capacidad; //Habitaciones
    private boolean disponibilidad;
    private String ciudad;
    private String pais;
    private float direccion;
    private String descripcionServicios;
    private float precio;
    private int cantidadServicios;

    //Constructor
    public Alojamiento(int capacidad, boolean disponibilidad, String ciudad, String pais, float direccion, String descripcionServicios, float precio, int cantidadServicios) {
        this.capacidad = capacidad;
        this.disponibilidad = disponibilidad;
        this.ciudad = ciudad;
        this.pais = pais;
        this.direccion = direccion;
        this.descripcionServicios = descripcionServicios;
        this.precio = precio;
        this.cantidadServicios = cantidadServicios;
    }

    public Alojamiento (){
        capacidad = 0;
        disponibilidad = true;
        ciudad ="";
        pais = "";
        direccion = 0;
        descripcionServicios = "";
        precio = 0;
        cantidadServicios = 0;
    }

    //Getters

    public int getCapacidad() {
        return capacidad;
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

    public float getDireccion() {
        return direccion;
    }

    public String getDescripcionServicios() {
        return descripcionServicios;
    }

    public float getPrecio() {
        return precio;
    }

    public int getCantidadServicios() {
        return cantidadServicios;
    }

    //Setter
    private void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    //Metodos

    //Funcion que calcula el precio de un alojamiento segun la capacidad de hospedaje y los servicios que ofrece
    @Override
    public float calcularPrecio() {
        float precioTotal = 30000; //Por defecto el hospedaje sale 30000 (capacidad max 2 personas)
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
}
