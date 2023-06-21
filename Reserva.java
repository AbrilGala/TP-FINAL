package Empresa;

import Empresa.Alojamiento.Alojamiento;
import Empresa.Vuelos.Vuelo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Reserva <T>{
    private T reserva;
    private int id;
    private String fechaDeRealizacion;
    private String fechaDeLlegada;
    private String fechaDeSalida;
    private int cantMenoresDeEdad;
    private int cantMayoresDeEdad;

    public Reserva(T reserva, int id, String fechaDeRealizacion, String fechaDeLlegada, String fechaDeSalida, int cantMenoresDeEdad, int cantMayoresDeEdad) {
        this.reserva = reserva;
        this.id = id;
        this.fechaDeRealizacion = fechaDeRealizacion;
        this.fechaDeLlegada = fechaDeLlegada;
        this.fechaDeSalida = fechaDeSalida;
        this.cantMenoresDeEdad = cantMenoresDeEdad;
        this.cantMayoresDeEdad = cantMayoresDeEdad;
    }

    public static ArrayList<Reserva> JSONtoReserva(JSONArray arrayReservas) throws Exception
    {
        ArrayList<Reserva> arrayReservasTotal = new ArrayList<>();
        for(int m = 0; m<arrayReservas.length(); m++)
        {
            JSONObject objectReservas = new JSONObject();
            objectReservas = arrayReservas.getJSONObject(m);
            String fechaDeRealizacion = objectReservas.getString("fecha de realizacion");
            String fechaDeSalida = objectReservas.getString("fecha de salida");
            String fechaDeLlegada = objectReservas.getString("fecha de llegada");
            int numeroDeReserva = objectReservas.getInt("numero de reserva");
            int cantMayoresDeEdad = objectReservas.getInt("mayores de edad");
            int cantMenoresDeEdad = objectReservas.getInt("menores de edad");
            JSONObject reserva = objectReservas.getJSONObject("reserva");
            if(reserva.has("aerolinea"))
            {
                Vuelo vuelo = Vuelo.JSONToVuelos(reserva);
                Reserva reserva1 = new Reserva<>(vuelo, numeroDeReserva, fechaDeRealizacion, fechaDeLlegada, fechaDeSalida, cantMenoresDeEdad, cantMayoresDeEdad);
                arrayReservasTotal.add(reserva1);
            }else
            {
                Alojamiento alojamiento=Alojamiento.JSONToAlojamiento(reserva);
                Reserva reserva1 = new Reserva<>(alojamiento, numeroDeReserva, fechaDeRealizacion, fechaDeLlegada, fechaDeSalida, cantMenoresDeEdad, cantMayoresDeEdad);
                arrayReservasTotal.add(reserva1);
            }
        }
        return arrayReservasTotal;
    }

    public T getReserva() {
        return reserva;
    }

    public int getId() {
        return id;
    }

    public String getFechaDeRealizacion() {
        return fechaDeRealizacion;
    }

    public String getFechaDeLlegada() {
        return fechaDeLlegada;
    }

    public String getFechaDeSalida() {
        return fechaDeSalida;
    }

    public int getCantMenoresDeEdad() {
        return cantMenoresDeEdad;
    }

    public int getCantMayoresDeEdad() {
        return cantMayoresDeEdad;
    }

    @Override
    public String toString() {
        String info = "";
        info += "Reserva: "+reserva+"\nNumero de reserva: "+id+"\nFecha de realizacion: "+fechaDeRealizacion+"\nFecha de salida: "+fechaDeSalida+"\nFecha de llegada: "+fechaDeLlegada+"\nMenores de edad: "+cantMenoresDeEdad+"\nMayores de edad: "+cantMayoresDeEdad+"\n";
        return info;
    }

    public JSONObject reservaToJSON() throws Exception
    {
        JSONObject jsonObject = new JSONObject();
        if(reserva instanceof Vuelo)
        {
            jsonObject.put("reserva", (((Vuelo) reserva).vueloToJSON()));
        }else
        {
            jsonObject.put("reserva",(((Alojamiento) reserva).alojamientoToJSON()));
        }
        jsonObject.put("numero de reserva", id);
        jsonObject.put("fecha de realizacion", fechaDeRealizacion);
        jsonObject.put("fecha de salida", fechaDeSalida);
        jsonObject.put("fecha de llegada", fechaDeLlegada);
        jsonObject.put("menores de edad", cantMenoresDeEdad);
        jsonObject.put("mayores de edad", cantMayoresDeEdad);
        System.out.println(jsonObject.toString());
        return jsonObject;
    }
}
