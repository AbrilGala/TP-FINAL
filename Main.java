import EmpresaVuelo.EmpresaVuelo;
import EmpresaVuelo.Excepciones.DisponibilidadAgotadaException;
import EmpresaVuelo.Reservas.Reserva;
import EmpresaVuelo.Vuelos.Aerolineas.Aerolinea;
import EmpresaVuelo.Vuelos.Vuelo;
import Persona.Usuario.ControladoraUsuario;
import Persona.Usuario.JsonUtiles;
import Persona.Usuario.Usuario;
import org.json.JSONArray;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        /*
        Usuario usuario = new Usuario("nahuel", "moron", 44957485, "mdp", "nahuelarielmoron1@gmail.com", "16/09/2003", "masculino", "Moronnahuu1", "nahuel1");
        Reserva<String> reserva = new Reserva<>("Vuelo", 1, "16/10/2023", "21/12/2023", "22/12/2023", 2, 2);
        Reserva<String> reserva1 = new Reserva<>("Hotel", 2, "5/1/2023", "10/5/2023", "12/5/2023", 0, 5);
        usuario.agregarReserva(reserva);
        usuario.agregarReserva(reserva1);
        usuario.eliminarReserva(reserva1);
         */
        EmpresaVuelo empresaVuelo = new EmpresaVuelo();

        try {
            empresaVuelo.JSONtoVuelo();
            // empresaVuelo.JSONtoAlojamiento();
        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.println(e.getMessage());
        }

        System.out.println("\t Bienvenido/s!!\n");
        try
        {
            boolean salir = false;
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
                        scanner.nextLine();
                        String contrasena = scanner.nextLine();
                        Usuario usuario1=controladoraUsuario.iniciarSesion(mail, contrasena);
                        if(usuario1==null)
                        {
                            System.out.println("LogIn.Usuario incorrecto");
                        }else
                        {
                            System.out.println("Ingreso correctamente");
                            menu(usuario1, empresaVuelo);
                        }
                        break;
                    case 2:
                        boolean registro = false;
                        Usuario usuario2 = crearUsuario();
                        registro = controladoraUsuario.registrarse(usuario2);
                        System.out.println(registro);
                        if(registro)
                        {
                        }
                        break;
                    case 3:
                        salir=true;
                        break;
                }
                JSONArray jsonArray = controladoraUsuario.mapaToJSON();
                JsonUtiles.grabar(jsonArray, "usuarios");
                ///System.out.println(usuario.usuarioToJSON().toString());
            }while(!salir);

        }catch(Exception e)
        {
            System.out.println(e);
        }
    }


    //funcion menu para la busqueda de vuelos y alojamientos
    private static void menu(Usuario usuario, EmpresaVuelo empresaVuelo)
    {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        do
        {
            System.out.println("Ingrese la opcion que desea realizar: ");
            System.out.println("OPCION 1: Buscar vuelos");
            System.out.println("OPCION 2: Buscar alojamiento");
            System.out.println("OPCION 3: Salir");
            int opcion = scanner.nextInt();
            switch (opcion)
            {
                case 1:
                    System.out.println("Ingrese el origen");
                    String paisOrigen=scanner.nextLine();
                    System.out.println("Ingrese el destino");
                    String paisDestino=scanner.nextLine();

                    HashSet<Vuelo> setVuelosDestino = empresaVuelo.buscarVuelo(paisOrigen,paisDestino);
                    System.out.println("Lista de vuelos: "+setVuelosDestino.toString()); //Poner la función de imprimir lista Vuelos
                    //Opcion de Filtrar vuelos:
                    System.out.println("¿Desea filtrar su búsqueda? (si/no): ");
                    String filtrado = scanner.nextLine();
                    while (filtrado.equalsIgnoreCase("si")){
                        setVuelosDestino = filtrarVuelos(empresaVuelo,setVuelosDestino); //Obtengo una lista nueva con los vuelos ya filtrados
                        System.out.println("¿Desea seguir filtrando su busqueda? (Si/No): ");
                        filtrado = scanner.nextLine();
                    }
                    //Opcion de Reservar Vuelos:
                    System.out.println("¿Desea reservar algún vuelo?");
                    String respuesta =scanner.nextLine();
                    if(respuesta.equalsIgnoreCase("si")){
                        if (reservarVuelo(setVuelosDestino,empresaVuelo,usuario)){
                            System.out.println("La reserva se ha realizado con exito: ");
                        } else {
                            System.out.println("La reserva no se ha podido realizar");
                        }
                    }
                    break;
                case 2:
                    System.out.println("Ingrese el pais destino");
                    String paisDestino1=scanner.nextLine();

                    try {
                        HashSet<Alojamiento> setAlojamientosDestino = empresaVuelo.buscarAlojamiento(paisDestino1);
                        System.out.println("Lista de vuelos: "+setAlojamientosDestino.toString()); //Poner la función de imprimir lista Vuelos
                        //Opcion de Filtrar vuelos:
                        System.out.println("¿Desea filtrar su búsqueda? (si/no): ");
                        String filtrado1 = scanner.nextLine();
                        while (filtrado1.equalsIgnoreCase("si")){
                            setAlojamientosDestino = filtrarAlojamiento(empresaVuelo,setAlojamientosDestino); //Obtengo una lista nueva con los vuelos ya filtrados
                            System.out.println("¿Desea seguir filtrando su busqueda? (Si/No): ");
                            filtrado1 = scanner.nextLine();
                        }
                        //Opcion de Reservar Vuelos:
                        System.out.println("¿Desea reservar algún alojamiento?");
                        String respuesta1 =scanner.nextLine();
                        /*if(respuesta1.equalsIgnoreCase("si")){
                            if (reservarVuelo(setAlojamientosDestino,empresaVuelo,usuario)){
                                System.out.println("La reserva se ha realizado con exito: ");
                            } else {
                                System.out.println("La reserva no se ha podido realizar");
                            }
                        }*/
                    } catch (PaisInexistenteException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    break;
            }

        }while(!salir); //Mientras el usuario no desee salir

    }


    /**
     * El siguiente método permite filtrar la búsqueda de vuelos, obteniendo una lista más específica.
     * @param empresaVuelo es la empresa que contiene todos los vuelos a filtrar
     * @param setVuelosDestino es la lista de vuelos completa que luego será filtrada
     * @return la lista de vuelos filtrados por el usuario
     * @author Abril Galarraga
     * @author Mateo Cuevas
     */
    private static HashSet<Vuelo> filtrarVuelos(EmpresaVuelo empresaVuelo, HashSet<Vuelo> setVuelosDestino)
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
                        "PRIMERA CLASE" +
                        "EJECUTIVA" +
                        "ECONOMICA");
                String clase =scanner.nextLine();
                setVuelosFiltrados = empresaVuelo.buscarVuelo(clase,setVuelosDestino);
                break;
            case 2:
                System.out.println("INGRESE PRECIO MINIMO");
                double precioMinimo=scanner.nextDouble();
                System.out.println("INGRESE PRECIO MAXIMO");
                double precioMaximo = scanner.nextDouble();
                setVuelosFiltrados = empresaVuelo.buscarVuelo(precioMinimo,precioMaximo,setVuelosDestino);
                break;
            case 3:
                System.out.println("Aerolineas disponibles: ");
                for (Aerolinea aux: Aerolinea.values()) {
                    System.out.println(aux);
                }
                System.out.println("SELECCIONE UNA AEROLINEA");
                Aerolinea aerolinea= Aerolinea.valueOf(scanner.nextLine());
                setVuelosFiltrados = empresaVuelo.buscarVuelo(aerolinea, setVuelosDestino);
                break;
            case 4:
                setVuelosFiltrados=setVuelosDestino;
        }
        return setVuelosFiltrados;
    }
    /**
     * El siguiente método permite filtrar la búsqueda de alojamientos, obteniendo una lista más específica.
     * @param empresaVuelo es la empresa que contiene todos los alojamientos a filtrar
     * @param setAlojamientosDestino es la lista de alojamientos completa que luego será filtrada
     * @return la lista de alojamientos filtrados por el usuario
     * @author Abril Galarraga
     * @author Mateo Cuevas
     */
    private static HashSet<Alojamiento> filtrarAlojamiento(EmpresaVuelo empresaVuelo, HashSet<Alojamiento> setAlojamientosDestino)
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
                setAlojamientosFiltrados = empresaVuelo.buscarAlojamiento(precioMaximo,setAlojamientosFiltrados);
                break;
            case 2:
                System.out.println("INGRESE LA CAPACIDAD MINIMA");
                int capacidadMin=scanner.nextInt();
                setAlojamientosFiltrados = empresaVuelo.buscarAlojamiento(capacidadMin,setAlojamientosFiltrados);
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
     * @param empresaVuelo es la empresa del programa
     * @param usuario es el usuario actual, el cual va a realizar la reserva
     * @return true si la reserva del vuelo se realizó con exito, de lo contrario retorna false.
     * @author Abril Galarraga
     */
    public static boolean reservarVuelo (HashSet<Vuelo> listaVuelos, EmpresaVuelo empresaVuelo, Usuario usuario){
        boolean reservado = false;
        System.out.println("ingrese el id del vuelo que quiera reservar");
        int id=scanner.nextInt();
        Vuelo vueloAReservar = empresaVuelo.buscarVuelo(id,listaVuelos);
        System.out.println("Ingrese la cantidad de personas mayores de edad (18 años o mas)");
        int mayoresEdad =scanner.nextInt();
        System.out.println("Ingrese la cantidad de personas menores de edad (17 años o menos)");
        int menoresEdad =scanner.nextInt();
        try{
            Reserva nuevaReserva =empresaVuelo.reservar(vueloAReservar,menoresEdad, mayoresEdad);
            if (usuario.agregarReserva(nuevaReserva)){
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
     * @param empresaVuelo es la empresa del programa
     * @param usuario es el usuario actual, el cual va a realizar la reserva
     * @return true si la reserva del alojamiento se realizó con exito, de lo contrario retorna false.
     * @author Mateo Cuevas
     */
    public static boolean reservarAlojamiento (HashSet<Alojamiento> setAlojamientosDestino, EmpresaVuelo empresaVuelo, Usuario usuario){
        boolean reservado = false;
        System.out.println("ingrese el nombre del alojamiento que quiera reservar");
        String nombre=scanner.nextLine();
        Alojamiento alojamientoAReservar = empresaVuelo.buscarAlojamiento(nombre,setAlojamientosDestino);
        System.out.println("Ingrese la cantidad de personas mayores de edad (18 años o mas)");
        int mayoresEdad =scanner.nextInt();
        System.out.println("Ingrese la cantidad de personas menores de edad (17 años o menos)");
        int menoresEdad =scanner.nextInt();
        /*try{
           Reserva nuevaReserva =empresaVuelo.reservar(alojamientoAReservar,menoresEdad, mayoresEdad);
            if (usuario.agregarReserva(nuevaReserva)){
                reservado = true;
            }
        } catch (DisponibilidadAgotadaException e) {
            System.out.println(e.getMessage());
        }*/
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
