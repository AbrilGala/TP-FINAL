import Apis.ConsumoAPI;
import Apis.ControladoraPaises;
import Apis.Excepciones.PaisInexistenteException;
import Apis.JsonUtiles;
import Apis.Pais;
import Empresa.Alojamiento.Alojamiento;
import Empresa.EmpresaGestora;
import Empresa.Excepciones.DisponibilidadAgotadaException;
import Empresa.Reserva;
import Empresa.Vuelos.Aerolinea;
import Empresa.Vuelos.Vuelo;
import Empresa.Vuelos.VueloNoEncontradoException;
import LogIn.ControladoraUsuario;
import LogIn.Excepciones.ContraseniaIncorrectaException;
import LogIn.Excepciones.UsuarioNoEncontradoException;
import LogIn.Excepciones.UsuarioRepetidoException;
import LogIn.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("\t Bienvenido/s!!\n");
        try
        {
            EmpresaGestora empresaGestora = new EmpresaGestora();
            //empresaGestora.cargaDeVuelos();
            boolean salir = false;
            Usuario usuario1 = null;
            do
            {
                ControladoraUsuario controladoraUsuario = new ControladoraUsuario();
                controladoraUsuario.leerArchivoUsuarios();
                System.out.println("Ingrese la opcion que desea realizar: ");
                System.out.println("OPCION 1: Iniciar sesion");
                System.out.println("OPCION 2: Registrarse");
                System.out.println("OPCION 3: Salir");
                int opcion = scanner.nextInt();
                switch (opcion)
                {
                    case 1:
                        System.out.println("Ingrese su mail:");
                        scanner.nextLine();
                        String mail = scanner.nextLine();
                        System.out.println("ingrese contrasena: ");
                        String contrasena = scanner.nextLine();
                        usuario1=controladoraUsuario.iniciarSesion(mail, contrasena);
                        if(usuario1==null)
                        {
                            System.out.println("LogIn.Usuario incorrecto");
                        }else
                        {
                            System.out.println("Ingreso correctamente");
                            busqueda(usuario1, empresaGestora);
                        }
                        break;
                    case 2:
                        try {
                            Usuario usuario2 = crearUsuario();
                            controladoraUsuario.registrarse(usuario2);
                            System.out.println("se registro correctamente");
                        }catch (UsuarioRepetidoException e)
                        {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3: //Persistencia del archivo de usuarios con sus reservas
                        HashMap<String, Usuario> mapaUsuario = controladoraUsuario.getMapaUsuarios();
                        mapaUsuario.remove(usuario1.getNombreUsuario());
                        mapaUsuario.put(usuario1.getNombreUsuario(),usuario1);
                        ControladoraUsuario nuevaControladora = new ControladoraUsuario(mapaUsuario);
                        JSONArray jsonArray = nuevaControladora.mapaToJSON();
                        JsonUtiles.grabar(jsonArray, "usuarios");
                        salir=true;
                        break;
                }

                JSONArray jsonArray = controladoraUsuario.mapaToJSON();
                JsonUtiles.grabar(jsonArray, "usuarios");
                ///System.out.println(usuario.usuarioToJSON().toString());
            }while(!salir);



        }catch(UsuarioNoEncontradoException e)
        {
            System.out.println(e.getMessage());
        }
        catch(ContraseniaIncorrectaException e)
        {
            System.out.println(e.getMessage());
        }
        catch(UsuarioRepetidoException e)
        {
            System.out.println(e.getMessage());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }


    }


    //funcion menu para la busqueda de vuelos y alojamientos
    private static void busqueda(Usuario usuario, EmpresaGestora empresaGestora)
    {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        do
        {
            System.out.println("Ingrese la opcion que desea realizar: ");
            System.out.println("OPCION 1: Buscar vuelos");
            System.out.println("OPCION 2: Buscar alojamiento");
            System.out.println("OPCION 3: Ver reservas");
            System.out.println("OPCION 4: Salir");
            int opcion = scanner.nextInt();
            switch (opcion)
            {
                case 1: //Buscar vuelos:
                    System.out.println("Ingrese el origen");
                    scanner.nextLine();
                    String origen=scanner.nextLine();
                    Pais paisOrigen=empresaGestora.buscarPaisPorNombre(origen);
                    System.out.println("Ingrese el destino");
                    String destino=scanner.nextLine();
                    Pais paisDestino=empresaGestora.buscarPaisPorNombre(destino);
                    HashSet<Vuelo> setVuelosDestino = empresaGestora.buscarVuelo(paisOrigen,paisDestino);
                    System.out.println("Lista de vuelos: \n"+setVuelosDestino.toString()); //Poner la función de imprimir lista Vuelos
                    //Opcion de Filtrar vuelos:
                    System.out.println("¿Desea filtrar su búsqueda? (si/no): ");
                    String filtrado = scanner.nextLine();
                    if (filtrado.equalsIgnoreCase("si")){
                        setVuelosDestino = filtrarVuelos(empresaGestora,setVuelosDestino); //Obtengo una lista nueva con los vuelos ya filtrados
                        System.out.println("Lista de vuelos: "+setVuelosDestino.toString());
                    }
                    //Opcion de Reservar Vuelos:
                    System.out.println("¿Desea reservar algún vuelo?");
                    String respuesta =scanner.nextLine();
                    if(respuesta.equalsIgnoreCase("si")){
                        try {
                            if (reservarVuelo(usuario,setVuelosDestino,empresaGestora)){
                                System.out.println("\nLa reserva se ha realizado con exito: ");
                            } else {
                                System.out.println("\nLa reserva no se ha podido realizar");
                            }
                        } catch (VueloNoEncontradoException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;

                case 2: //Buscar Alojamiento
                    System.out.println("Ingrese el pais destino");
                    scanner.nextLine();
                    String destino1=scanner.nextLine();
                        Pais paisDestino1=empresaGestora.buscarPaisPorNombre(destino1);
                        HashSet<Alojamiento> setAlojamientosDestino = empresaGestora.buscarAlojamiento(paisDestino1);
                        System.out.println("Lista de Alojamientos: "+empresaGestora.mostrarListaAlojamientos(setAlojamientosDestino)); //Poner la función de imprimir lista Vuelos
                        //Opcion de Filtrar vuelos:
                    System.out.println("¿Desea filtrar su búsqueda? (si/no): ");
                        String filtrado1 = scanner.nextLine();
                        if(filtrado1.equalsIgnoreCase("si")){
                            setAlojamientosDestino = filtrarAlojamiento(empresaGestora,setAlojamientosDestino); //Obtengo una lista nueva con los vuelos ya filtrados
                            System.out.println("Lista de Alojamientos: "+empresaGestora.mostrarListaAlojamientos(setAlojamientosDestino));
                        }
                        //Opcion de Reservar Vuelos:
                        System.out.println("¿Desea reservar algún alojamiento?");
                        String respuesta1 =scanner.nextLine();
                        if(respuesta1.equalsIgnoreCase("si")){
                            if (reservarAlojamiento(setAlojamientosDestino,empresaGestora,usuario)){
                                System.out.println("La reserva se ha realizado con exito: ");
                            } else {
                                System.out.println("La reserva no se ha podido realizar");
                            }
                        }
                    break;

                case 3: //Ver reservas - Eliminar:
                    if(usuario.getReservas().isEmpty()) //Si no tiene reservas:
                    {
                        System.out.println("usted no tiene reservas hechas");
                    }
                    else { //Si posee reservas:
                        System.out.println(usuario.mostrarListaReservas());
                        System.out.println("desea cancelar alguna? (si/no)");
                        scanner.nextLine();
                        String respuesta2=scanner.nextLine();
                        //Si el usuario desea eliminar una reserva:
                        if(respuesta2.equalsIgnoreCase("si"))
                        {
                            System.out.println("ingrese el id de la reserva que desea cancelar");
                            int id= scanner.nextInt();
                            Reserva reservaACancelar = usuario.buscarReservasPorId(id);
                            if(empresaGestora.cancelarReserva(id,usuario)) { //Elimina la reserva
                                System.out.println("\nLa reserva ha sido eliminada con exito");
                            } else{
                                System.out.println("\n La reserva no ha podido ser eliminada");
                            }
                        }
                        //Si el usuario posee reservas canceladas:
                        if (!usuario.getReservasCanceladas().isEmpty()){
                            System.out.println("desea ver las reservas canceladas? (si/no");scanner.nextLine();
                            respuesta2=scanner.nextLine();
                            //Si el usuario desea ver las reservas canceladas
                            if(respuesta2.equalsIgnoreCase("si"))
                            {
                                if(usuario.getReservasCanceladas().isEmpty()){
                                    System.out.println("usted no tiene reservas canceladas");}
                                else {
                                    System.out.println("Reservas canceladas: \n"+usuario.mostrarListaReservasCanceladas());
                                }
                            }
                        }

                    }
                    break;
                case 4:
                    salir=true;
                    break;
            }
        }while(!salir); //Mientras el usuario no desee salir
    }


    /**
     * El siguiente método permite filtrar la búsqueda de vuelos, obteniendo una lista más específica.
     * @param empresaGestora es la empresa que contiene todos los vuelos a filtrar
     * @param setVuelosDestino es la lista de vuelos completa que luego será filtrada
     * @return la lista de vuelos filtrados por el usuario
     * @author Abril Galarraga
     * @author Mateo Cuevas
     */
    private static HashSet<Vuelo> filtrarVuelos(EmpresaGestora empresaGestora, HashSet<Vuelo> setVuelosDestino)
    {
        Scanner scanner = new Scanner(System.in);
        HashSet<Vuelo> setVuelosFiltrados = new HashSet<>();
        System.out.println("Desea filtrar por:");
        System.out.println("OPCION 1: Por la clase del vuelo");
        System.out.println("OPCION 2: Por rango de precio");
        System.out.println("OPCION 3: Por aerolinea");
        System.out.println("OPCION 4: No filtrar");
        int opcion = scanner.nextInt();
        switch (opcion)
        {
            case 1: //A probar:
                System.out.println("LAS CLASES DE LOS VUELOS SON:" +
                        "\nPRIMERA CLASE" +
                        "\nEJECUTIVO" +
                        "\nECONOMICO");
                scanner.nextLine();
                String clase =scanner.nextLine();
                setVuelosFiltrados = empresaGestora.buscarVuelo(clase,setVuelosDestino);
                break;
            case 2:
                System.out.println("INGRESE PRECIO MINIMO");
                float precioMinimo=scanner.nextFloat();
                System.out.println("INGRESE PRECIO MAXIMO");
                float precioMaximo = scanner.nextFloat();
                setVuelosFiltrados = empresaGestora.buscarVuelo(precioMinimo,precioMaximo,setVuelosDestino);
                break;
            case 3:
                System.out.println("Aerolineas disponibles: ");
                for (Aerolinea aux: Aerolinea.values()) {
                    System.out.println(aux);
                }
                System.out.println("Escriba la aerolinea elegida aquí:");
                scanner.nextLine();
                Aerolinea aerolinea = Aerolinea.valueOf(scanner.nextLine());
                setVuelosFiltrados = empresaGestora.buscarVuelo(aerolinea, setVuelosDestino);
                break;
            case 4:
                setVuelosFiltrados=setVuelosDestino;
        }
        return setVuelosFiltrados;
    }



    /**
     * El siguiente método permite filtrar la búsqueda de alojamientos, obteniendo una lista más específica.
     * @param empresaGestora es la empresa que contiene todos los alojamientos a filtrar
     * @param setAlojamientosDestino es la lista de alojamientos completa que luego será filtrada
     * @return la lista de alojamientos filtrados por el usuario
     * @author Abril Galarraga
     * @author Mateo Cuevas
     */
    private static HashSet<Alojamiento> filtrarAlojamiento(EmpresaGestora empresaGestora, HashSet<Alojamiento> setAlojamientosDestino)
    {
        Scanner scanner = new Scanner(System.in);
        HashSet<Alojamiento> setAlojamientosFiltrados = new HashSet<>();
        System.out.println("Desea filtrar por:");
        System.out.println("OPCION 1: Por precio maximo");
        System.out.println("OPCION 2: Por capacidad minima de personas");
        System.out.println("OPCION 3: No filtrar");
        int opcion = scanner.nextInt();
        switch (opcion)
        {
            case 1: //A probar:
                System.out.println("INGRESE PRECIO MAXIMO");
                double precioMaximo = scanner.nextDouble();
                setAlojamientosFiltrados = empresaGestora.buscarAlojamiento(precioMaximo,setAlojamientosDestino);
                break;
            case 2:
                System.out.println("INGRESE LA CAPACIDAD MINIMA");
                int capacidadMin=scanner.nextInt();
                setAlojamientosFiltrados = empresaGestora.buscarAlojamiento(capacidadMin,setAlojamientosDestino);
                break;
            case 3:
                setAlojamientosFiltrados=setAlojamientosDestino;

        }
        return setAlojamientosFiltrados;
    }



    /**
     * El método reservarVuelo le permite al usuario reservar un vuelo determinado a través de un id en base a una lista de vuelos disponibles y preseleccionada por el usuario.
     * Para concretar la reserva deberá ingresar la cantidad de pasajeros (mayores y/o menores de edad) que van a ingresar al vuelo.
     * @param listaVuelos es la lista de vuelos que le va a permitir al usuario seleccionar uno para reservalo
     * @param empresaGestora es la empresa del programa
     * @param usuario es el usuario actual, el cual va a realizar la reserva
     * @return true si la reserva del vuelo se realizó con exito, de lo contrario retorna false.
     * @author Abril Galarraga
     */
    public static boolean reservarVuelo (Usuario usuario, HashSet<Vuelo> listaVuelos, EmpresaGestora empresaGestora) throws VueloNoEncontradoException{
        boolean reservado = false;
        System.out.println("ingrese el id del vuelo que quiera reservar");
        int id=scanner.nextInt();
        Vuelo vueloAReservar = empresaGestora.buscarVuelo(id,listaVuelos);
        if (vueloAReservar == null){
            throw new VueloNoEncontradoException("El vuelo solicitado no ha sido encontrado");
        }
        System.out.println("Ingrese la cantidad de personas mayores de edad (18 años o mas)");
        int mayoresEdad =scanner.nextInt();
        System.out.println("Ingrese la cantidad de personas menores de edad (17 años o menos)");
        int menoresEdad =scanner.nextInt();
        try{
            Reserva nuevaReserva =empresaGestora.reservar(usuario,vueloAReservar,menoresEdad, mayoresEdad);
            if (nuevaReserva != null){
                reservado = true;
            }
        } catch (DisponibilidadAgotadaException e) {
            System.out.println(e.getMessage());
        }
        return reservado;
    }



    /**
     * El método reservarAlojamiento le permite al usuario reservar un alojamiento determinado a través de su nombre unico en base a una lista de alojamiento disponibles y preseleccionada por el usuario.
     * Para concretar la reserva deberá ingresar la cantidad de perosnas (mayores y/o menores de edad).
     * @param setAlojamientosDestino es la lista de alojamientos que le va a permitir al usuario seleccionar uno para reservalo
     * @param empresaGestora es la empresa del programa
     * @param usuario es el usuario actual, el cual va a realizar la reserva
     * @return true si la reserva del alojamiento se realizó con exito, de lo contrario retorna false.
     * @author Mateo Cuevas
     */
    public static boolean reservarAlojamiento (HashSet<Alojamiento> setAlojamientosDestino, EmpresaGestora empresaGestora, Usuario usuario){
        boolean reservado = false;
        System.out.println("ingrese el id del alojamiento que quiera reservar");
       int id=scanner.nextInt();
        Alojamiento alojamientoAReservar = empresaGestora.buscarAlojamientoId(id,setAlojamientosDestino);
        System.out.println("Ingrese la cantidad de personas mayores de edad (18 años o mas)");
        int mayoresEdad =scanner.nextInt();
        System.out.println("Ingrese la cantidad de personas menores de edad (17 años o menos)");
        int menoresEdad =scanner.nextInt();
        System.out.println("Ingrese la fecha de llegada");
        scanner.nextLine();
        String fechaLLegada=scanner.nextLine();
        System.out.println("Ingrese la fecha de salida");
        String fechaSalida=scanner.nextLine();
        try{
           Reserva nuevaReserva =empresaGestora.reservar(usuario,alojamientoAReservar,menoresEdad,mayoresEdad,fechaLLegada,fechaSalida);
           if (nuevaReserva != null) {
               reservado = true;
           }
        } catch (DisponibilidadAgotadaException e) {
            System.out.println(e.getMessage());
        }
        return reservado;
    }



    /**
     * Función que permite cargar los datos de un nuevo usuario.
     * Se utiliza en el caso de que el usuario sea nuevo dentro del sistema y se requiera registrarse
     * @return el nuevo usuario creado con todos sus datos
     * @author Nahuel Moron
     */
    public static Usuario crearUsuario()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese apellido: ");
        String apellido = scanner.nextLine();
        System.out.println("Ingrese DNI: ");
        int dni = scanner.nextInt();
        System.out.println("Ingrese direccion: ");
        scanner.nextLine();
        String direccion = scanner.nextLine();
        System.out.println("Ingrese mail: ");
        String mail = scanner.nextLine();
        System.out.println("Ingrese fecha de nacimiento: ");
        String fechaNacimiento = scanner.nextLine();
        System.out.println("Ingrese su sexo: ");
        String sexo = scanner.nextLine();
        System.out.println("Ingrese nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.println("Ingrese contrasena: ");
        String contrasena = scanner.nextLine();

        Usuario usuario = new Usuario(nombre, apellido, dni, direccion, mail, fechaNacimiento, sexo, nombreUsuario, contrasena);
        return usuario;
    }

}