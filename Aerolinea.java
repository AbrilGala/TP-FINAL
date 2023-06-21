package Empresa.Vuelos;

public enum Aerolinea {
    AEROLINEAS_ARGENTINAS("AEROLINEAS_ARGENTINAS"), AIR_NEW_ZEALAND("AIR_NEW_ZEALAND"), KOREAN_AIR ("KOREAN_AIR"), SINGAPORE_AIRLINES("SINGAPORE_AIRLINES"), AEROMEXICO("AEROMEXICO"), AIR_EUROPA("AIR_EUROPA"), JET_SMART("JET_SMART");

    private String nombre;
    private Aerolinea (String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public static Aerolinea verificarAerolinea(String aerolinea)
    {
        if(aerolinea.equals(AEROLINEAS_ARGENTINAS.nombre))
        {
            return AEROLINEAS_ARGENTINAS;
        }else
        {
            if(aerolinea.equals(AEROMEXICO.nombre))
            {
                return AEROMEXICO;
            }else
            {
                if(aerolinea.equals(AIR_EUROPA.nombre))
                {
                    return AIR_EUROPA;
                }else
                {
                    if(aerolinea.equals(AIR_NEW_ZEALAND.nombre))
                    {
                        return AIR_NEW_ZEALAND;
                    }else
                    {
                        if(aerolinea.equals(KOREAN_AIR.nombre))
                        {
                            return KOREAN_AIR;
                        }else
                        {
                            if(aerolinea.equals(SINGAPORE_AIRLINES.nombre))
                            {
                                return SINGAPORE_AIRLINES;
                            }else
                            {
                                return JET_SMART;
                            }
                        }
                    }
                }
            }
        }
    }
}