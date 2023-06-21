package Apis.Excepciones;

public class PaisInexistenteException extends Exception{
    private String info;

    public PaisInexistenteException (){
        info = "El pais ingresado no existe";
    }

    @Override
    public String getMessage() {
        return info;
    }
}
