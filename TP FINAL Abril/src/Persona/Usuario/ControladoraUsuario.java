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

    public ControladoraUsuario(HashMap<String, Usuario> mapaUsuarios) {
        this.mapaUsuarios = mapaUsuarios;
    }

    public HashMap<String, Usuario> getMapaUsuarios() {
        return mapaUsuarios;
    }

    /**
     * El método leerArchivoUsuarios permite poder leer el archivo de usuarios perteneciente al sistema y cargarlo dentro de la coleccion de usuarios, es decir, para su manejo
     * @throws Exception lanzado por el JSONObject
     * @author Nahuel Moron
     */
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

    public Usuario verificarUsuario(String mail) {
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


    /**
     * El método permite verificar si la contraseña que el usuario envía coinicide con la asignada al usuario
     * @param usuario es el usuario que vamos a utilizar para realizar la confirmación
     * @param contrasena es la contraseña a verificar
     * @return true si la contraseña es correcta, de lo contrario retorna false.
     * @author Nahuel Moron
     */
    public boolean verificarContrasena(Usuario usuario, String contrasena) {
        boolean contrasenaCorrecta = false;
        if(usuario.getContrasena().equals(contrasena))
        {
            contrasenaCorrecta = true;
        }
        return contrasenaCorrecta;
    }


    /** El método iniciarSesion le permite al cliente ingresar su email y contrania para poder ingresar al sistema. Para ello deberá haberse registrado previamente
     * @param mail del usuario que desea iniciar sesion
     * @param contrasenia ingresada por el usuario
     * @return el usuario en cuestion
     * @throws UsuarioNoEncontradoException en caso de que el usuario no haya sido encotrado dentro del programa
     * @throws ContraseniaIncorrectaException en caso de que la contrasenia ingresada sea incorrecta
     * @author Nahuel Moron
     */
    public Usuario iniciarSesion(String mail, String contrasenia) throws UsuarioNoEncontradoException, ContraseniaIncorrectaException {
        Usuario usuarioABuscar = null;
        Usuario usuarioEncontrado = null;
        if(verificarUsuario(mail)!=null)
        {
            usuarioABuscar=verificarUsuario(mail);
            if(!verificarContrasena(usuarioABuscar,contrasenia))
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

    /**
     * El método registrarse le permite a un nuevo usuario crearse una cuenta dentro del sistema, para ello se deberán tomar sus datos previamente.
     * @param usuario es el usuario que desea registrarse en el sistema
     * @return true si el nuevo usuario se pudo registrar correctamente, de lo contrario retorna false.
     * @throws UsuarioRepetidoException en caso de que el usuario ingrese un user repetido, es decir ya existente dentro del sistema.
     * @author Nahuel Moron
     */
    public boolean registrarse(Usuario usuario) throws UsuarioRepetidoException {
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

    public JSONArray mapaToJSON() throws Exception {
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
