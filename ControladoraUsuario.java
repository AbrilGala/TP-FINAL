package Persona.Usuario;

import EmpresaVuelo.Reservas.Reserva;
import Persona.Persona;
import Persona.Usuario.Excepciones.ContraseniaIncorrectaException;
import Persona.Usuario.Excepciones.UsuarioNoEncontradoException;
import Persona.Usuario.Excepciones.UsuarioRepetidoException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ControladoraUsuario {
    private HashMap<String, Usuario> mapaUsuarios;

    public ControladoraUsuario() {

        mapaUsuarios = new HashMap<>();
    }

    public ControladoraUsuario(HashMap mapaUsuarios) {
        this.mapaUsuarios = mapaUsuarios;
    }

    public HashMap<String, Usuario> getMapaUsuarios() {
        return mapaUsuarios;
    }

    public void leerArchivoUsuarios() throws Exception {
        JSONArray jsonArray = new JSONArray(JsonUtiles.leer("usuarios"));
        for(int i = 0; i<jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String nombreUsuario = jsonObject.getString("nombre de usuario");
            String contrasena = jsonObject.getString("contrasena");
            JSONObject persona = jsonObject.getJSONObject("persona");
            Persona persona1 = Persona.JSONtoPersona(persona);

            JSONArray arrayReservas = jsonObject.getJSONArray("reservas");
            ArrayList<Reserva> arrayReservasTotal = Reserva.JSONtoReserva(arrayReservas);

            JSONArray jsonReservasCanceladas = jsonObject.getJSONArray("reservas canceladas");
            ArrayList<Reserva> arrayReservasCanceladas = Reserva.JSONtoReserva(jsonReservasCanceladas);

            Usuario usuario = new Usuario(persona1, nombreUsuario, contrasena, arrayReservasTotal, arrayReservasCanceladas);
            mapaUsuarios.put(persona1.getMail(), usuario);
        }
    }
    public Usuario verificarUsuario(String mail)
    {
        Usuario usuarioEncontrado = null;
        Iterator<Usuario> it = mapaUsuarios.values().iterator();
        while(it.hasNext() && usuarioEncontrado==null)
        {
            Usuario aux = it.next();
            if(aux.getMail().equals(mail))
            {
                usuarioEncontrado = aux;
            }
        }
        return usuarioEncontrado;
    }
    public boolean verificarContrasena(Usuario usuario, String contrasena)
    {
        boolean contrasenaCorrecta = false;
        if(usuario.getContrasena().equals(contrasena))
        {
            contrasenaCorrecta = true;
        }
        return contrasenaCorrecta;
    }
    public Usuario iniciarSesion(String mail, String contrasena) throws Exception
    {
        Usuario usuarioABuscar = null;
        Usuario usuarioEncontrado = null;
        if(verificarUsuario(mail)!=null)
        {
            usuarioABuscar=verificarUsuario(mail);
            if(!verificarContrasena(usuarioABuscar,contrasena))
            {
                throw new ContraseniaIncorrectaException("contrasena incorrecta");
            }
            else {usuarioEncontrado=usuarioABuscar;}
        }else
        {
            throw new UsuarioNoEncontradoException("usuario incorrecto");
        }
        return usuarioEncontrado;
    }
    public boolean registrarse(Usuario usuario) throws Exception
    {
        Usuario aux = null;
        boolean usuarioCreado = false;
        aux = verificarUsuario(usuario.getMail());
        if(aux==null)
        {
            mapaUsuarios.put(usuario.getMail(),usuario);
            usuarioCreado = true;
        }else
        {
            throw new UsuarioRepetidoException("usuario repetido");
        }
        return usuarioCreado;
    }

    public JSONArray mapaToJSON() throws Exception
    {
        Iterator it = mapaUsuarios.entrySet().iterator();
        JSONArray jsonArray = new JSONArray();
        for(Map.Entry<String, Usuario> mapa : mapaUsuarios.entrySet())
        {
            JSONObject jsonObject = mapa.getValue().usuarioToJSON();
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
}
