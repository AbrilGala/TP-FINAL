package Persona.Usuario;

import EmpresaVuelo.Reservas.Reserva;
import Persona.Persona;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Usuario extends Persona {
    private ArrayList<Reserva> reservasCanceladas;
    private ArrayList<Reserva> reservas;
    private String nombreUsuario;
    private String contrasena;

    public Usuario(String nombre, String apellido, int dni, String direccion, String mail, String fechaDeNacimiento, String sexo, String nombreUsuario, String contrasena) {
        super(nombre, apellido, dni, direccion, mail, fechaDeNacimiento, sexo);
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        reservas = new ArrayList<>();
        reservasCanceladas = new ArrayList<>();
    }

    public Usuario(Persona persona, String nombreUsuario, String contrasena)
    {
        super(persona.getNombre(), persona.getApellido(), persona.getDni(), persona.getDireccion(), persona.getMail(), persona.getFechaDeNacimiento(), persona.getSexo());
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        reservas = new ArrayList<>();
        reservasCanceladas = new ArrayList<>();
    }


    public Usuario(Persona persona, String nombreUsuario, String contrasena, ArrayList<Reserva> reservas, ArrayList<Reserva> reservasCanceladas) {
        super(persona.getNombre(), persona.getApellido(), persona.getDni(), persona.getDireccion(), persona.getMail(), persona.getFechaDeNacimiento(), persona.getSexo());
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.reservas = reservas;
        this.reservasCanceladas = reservasCanceladas;
    }

    public ArrayList<Reserva> getReservasCanceladas() {
        return reservasCanceladas;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }
    public JSONObject usuarioToJSON() throws Exception
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("persona", personaToJSON());
        jsonObject.put("nombre de usuario", nombreUsuario);
        jsonObject.put("contrasena", contrasena);
        JSONArray arrayReservas = new JSONArray();
        JSONArray arrayReservasCanceladas = new JSONArray();
        for(int i = 0; i<reservas.size(); i++)
        {
            arrayReservas.put(reservas.get(i).reservaToJSON());
        }
        jsonObject.put("reservas",arrayReservas);
        for(int j = 0; j<reservasCanceladas.size(); j++)
        {
            arrayReservasCanceladas.put(reservasCanceladas.get(j).reservaToJSON());
        }
        jsonObject.put("reservas canceladas", arrayReservasCanceladas);
        return jsonObject;
    }
    public boolean agregarReserva(Reserva reserva)
    {
        reservas.add(reserva);
        return true;
    }
    public boolean eliminarReserva(Reserva reserva)
    {
        reservas.remove(reserva);
        reservasCanceladas.add(reserva);
        return true;
    }
    @Override
    public String toString() {
        String info = "";
        info += "Nombre de usuario: "+nombreUsuario+"\nReserva: "+reservas.toString()+"\nReservas canceladas: "+reservasCanceladas.toString();
        return super.toString() + info;
    }
}
