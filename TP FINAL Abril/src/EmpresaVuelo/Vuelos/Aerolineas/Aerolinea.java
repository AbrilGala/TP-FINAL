package EmpresaVuelo.Vuelos.Aerolineas;

public enum Aerolinea {
    AEROLINEAS_ARGENTINAS("Baja", "Nacionales e Internacionales"), AIR_NEW_ZEALAND("Alta", "Internacionales"), KOREAN_AIR ("Alta", "Internacionales"), SINGAPORE_AIRLINES("Alta", "Internacionales"), AEROMEXICO("Media", "Nacionales e Internacionales"), AIR_EUROPA("Alta", "Internacionales"), JET_SMART("Alta", "Internacionales"), LADE("Alta", "Internacionales");

    private String capacidad; //Baja, Media, Alta
    private String tipoVuelo; //Internacional o nacional

    //Constructor
    private Aerolinea ( String capacidad, String tipoVuelo){
        this.capacidad = capacidad;
        this.tipoVuelo = tipoVuelo;
    }

    //Getters
    public String getCapacidad() {
        return capacidad;
    }

    public String getTipoVuelo() {
        return tipoVuelo;
    }

}
