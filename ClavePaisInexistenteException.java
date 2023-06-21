package Apis.Excepciones;

public class ClavePaisInexistenteException extends Exception{
    private String info;

    public ClavePaisInexistenteException (){
        info = "El pais ingresado es inexistente";
    }

    @Override
    public String getMessage() {
        return info;
    }
}
