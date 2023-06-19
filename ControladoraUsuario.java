package persona;

import empresa.Reserva;
import org.json.JSONArray;
import org.json.JSONObject;
import persona.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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

    public String leerArchivoUsuarios() throws Exception {
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
        return mapaUsuarios.toString();
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
        public boolean iniciarSesion(String mail, String contrasena) throws Exception
        {
            boolean inicioCorrecto = false;
            Usuario usuarioEncontrado = null;
            usuarioEncontrado = verificarUsuario(mail);
            if(usuarioEncontrado==null)
            {
            }else
            {
                boolean contrasenaCorrecta = verificarContrasena(usuarioEncontrado, contrasena);
                if(!contrasenaCorrecta)
                {
                    throw new EContrasenaIncorrecta("contrasena incorrecta");
                }else
                {
                    inicioCorrecto = true;
                }
            }
            return inicioCorrecto;
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
            while(it.hasNext())
            {
                Usuario aux = (Usuario) it.next();
                JSONObject jsonObject = aux.usuarioToJSON();
                jsonArray.put(jsonObject);
            }
            return jsonArray;
        }
}

