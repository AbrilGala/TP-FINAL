import empresa.Reserva;
import org.json.JSONArray;
import persona.ControladoraUsuario;
import persona.JsonUtiles;
import persona.Usuario;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Usuario usuario = new Usuario("nahuel", "moron", 44957485, "mdp", "nahuelarielmoron1@gmail.com", "16/09/2003", "masculino", "Moronnahuu1", "nahuel1");
        Reserva<String> reserva = new Reserva<>("Vuelo", 1, "16/10/2023", "21/12/2023", "22/12/2023", 2, 2);
        Reserva<String> reserva1 = new Reserva<>("Hotel", 2, "5/1/2023", "10/5/2023", "12/5/2023", 0, 5);
        usuario.agregarReserva(reserva);
        usuario.agregarReserva(reserva1);
        usuario.eliminarReserva(reserva1);
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
                        String contrasena = scanner.nextLine();
                        Usuario usuario1=controladoraUsuario.iniciarSesion(mail, contrasena);
                        if(usuario1==null)
                        {
                            System.out.println("LogIn.Usuario incorrecto");
                        }else
                        {
                            System.out.println("Ingreso correctamente");
                            menu(usuario1);
                        }
                        break;
                    case 2:
                        boolean registro = false;
                        Usuario usuario1 = crearUsuario();
                        registro = controladoraUsuario.registrarse(usuario1);
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
            }while(salir==false);

        }catch(Exception e)
        {
            System.out.println(e);
        }
    }


    //funcion menu para la busqueda de vuelos y alojamientos
    private static void menu(Usuario usuario1)
    {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        do
        {
            EmpresaVuelo empresaVuelo=new EmpresaVuelo();
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
                    System.out.println("Ingrese la fecha de salida");
                    String fechaSalida=scanner.nextLine();

                    HashSet setVuelosDestino=empresaVuelo.buscarVuelo(paisOrigen,paisDestino);
                    System.out.println(setVuelosDestino.toString());
                    menu2(usuario1,empresaVuelo,setVuelosDestino);


                    break;
                case 2:

                    break;
                case 3:

                    break;
            }

        }while(salir==false);

    }
    //funcion para el filtrado de los vuelos
    private static void menu2(Usuario usuario,EmpresaVuelo empresaVuelo,HashSet setVuelosDestino)
    {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        do
        {
            System.out.println("Desea filtrar por:");
            System.out.println("OPCION 1: Por clase de vuelo");
            System.out.println("OPCION 2: Por rango de precio");
            System.out.println("OPCION 3: Por aerolinea");
            System.out.println("OPCION 4: No filtrar");
            int opcion = scanner.nextInt();
            switch (opcion)
            {

                case 1:
                    System.out.println("LAS CLASES DE LOS VUELOS SON:" +
                            "PRIMERA CLASE" +
                            "EJECUTIVA" +
                            "ECONOMICA");
                    String clase=scanner.nextLine();
                    HashSet vuelosFiltradosClase=empresaVuelo.buscarVuelo(clase,setVuelosDestino);
                    System.out.println(vuelosFiltradosClase.toString());
                    System.out.println("desea reservar alguno");
                    String rta=scanner.nextLine();
                    if(rta.equalsIgnoreCase("si"))
                    {
                        System.out.println("ingrese el id del vuelo que quiera reservar");
                        int id=scanner.nextInt();
                        Vuelo vueloAReservar= empresaVuelo.buscarVuelo(id,vuelosFiltradosClase);
                        System.out.println("ingrese las personas mayores de edad (18 años o mas)");
                        int mayoresEdad=scanner.nextInt();
                        System.out.println("ingrese las personas mayores de edad (17 años o menos)");
                        int menoresEdad=scanner.nextInt();
                        //Reserva reserva=empresaVuelo.reservarVuelo(Vuelo vueloAReservar);
                        //usuario.agregarReserva(reserva);
                    }
                    break;
                case 2:
                    System.out.println("INGRESE PRECIO MINIMO");
                    float precioMinimo=scanner.nextFloat();
                    System.out.println("INGRESE PRECIO MAXIMO");
                    float precioMaximo=scanner.nextFloat();
                    HashSet vuelosFiltradosPrecio=empresaVuelo.buscarVuelo(precioMinimo,precioMaximo,setVuelosDestino);
                    System.out.println(vuelosFiltradosPrecio.toString());
                    System.out.println("desea reservar alguno");
                    String rta1=scanner.nextLine();
                    if(rta1.equalsIgnoreCase("si"))
                    {
                        System.out.println("ingrese el id del vuelo que quiera reservar");
                        int id=scanner.nextInt();
                        Vuelo vueloAReservar= empresaVuelo.buscarVuelo(id,vuelosFiltradosPrecio);
                        System.out.println("ingrese las personas mayores de edad (18 años o mas)");
                        int mayoresEdad=scanner.nextInt();
                        System.out.println("ingrese las personas mayores de edad (17 años o menos)");
                        int menoresEdad=scanner.nextInt();
                        //Reserva reserva=empresaVuelo.reservarVuelo(Vuelo vueloAReservar);
                        //usuario.agregarReserva(reserva);
                    }

                    break;
                case 3:
                    //falta terminar
                    for (Aerolinea aux:Aerolinea.values()) {
                        System.out.println(aux);
                    }
                    System.out.println("INGRESE LA AEROLINEA");
                    String aerolinea=scanner.nextLine();
                    HashSet vuelosFiltradosAerolinea=empresaVuelo.buscarVuelo(precioMinimo,precioMaximo,setVuelosDestino);
                    System.out.println(vuelosFiltradosAerolinea.toString());
                    System.out.println("desea reservar alguno");
                    String rta2=scanner.nextLine();
                    if(rta2.equalsIgnoreCase("si"))
                    {
                        System.out.println("ingrese el id del vuelo que quiera reservar");
                        int id=scanner.nextInt();
                        Vuelo vueloAReservar= empresaVuelo.buscarVuelo(id,vuelosFiltradosAerolinea);
                        System.out.println("ingrese las personas mayores de edad (18 años o mas)");
                        int mayoresEdad=scanner.nextInt();
                        System.out.println("ingrese las personas mayores de edad (17 años o menos)");
                        int menoresEdad=scanner.nextInt();
                        //Reserva reserva=empresaVuelo.reservarVuelo(Vuelo vueloAReservar);
                        //usuario.agregarReserva(reserva);
                    }
                    break;
            }

        }while(salir==false);
    }
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
        System.out.println("Ingrese genero: ");
        String sexo = scanner.nextLine();
        System.out.println("Ingrese nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.println("Ingrese contrasena: ");
        String contrasena = scanner.nextLine();

        Usuario usuario = new Usuario(nombre, apellido, dni, direccion, mail, fechaNacimiento, sexo, nombreUsuario, contrasena);
        return usuario;
    }
}
