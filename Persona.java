package persona;

import org.json.JSONObject;

public class Persona {
    private String nombre;
    private String apellido;
    private int dni;
    private String direccion;
    private String mail;
    private String fechaDeNacimiento;
    private String sexo;

    public Persona(String nombre, String apellido, int dni, String direccion, String mail, String fechaDeNacimiento, String sexo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.direccion = direccion;
        this.mail = mail;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.sexo = sexo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getDni() {
        return dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getMail() {
        return mail;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public JSONObject personaToJSON() throws Exception
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombre", nombre);
        jsonObject.put("apellido", apellido);
        jsonObject.put("dni", dni);
        jsonObject.put("direccion", direccion);
        jsonObject.put("mail", mail);
        jsonObject.put("fecha de nacimiento", fechaDeNacimiento);
        jsonObject.put("sexo", sexo);
        return jsonObject;
    }

    public static Persona JSONtoPersona(JSONObject persona) throws Exception
    {
        String nombre = persona.getString("nombre");
        String apellido = persona.getString("apellido");
        String mail = persona.getString("mail");
        String fechaDeNacimiento = persona.getString("fecha de nacimiento");
        String direccion = persona.getString("direccion");
        String sexo = persona.getString("sexo");
        int dni = persona.getInt("dni");
        Persona persona1 = new Persona(nombre, apellido, dni, direccion, mail, fechaDeNacimiento, sexo);
        return persona1;
    }

    @Override
    public String toString() {
        String info = "";
        info += "Nombre: "+nombre+"\nApellido: "+apellido+"\nDNI: "+dni+"\nMail: "+mail+"\nDireccion: "+direccion+"\nFecha de nacimiento: "+fechaDeNacimiento+"\nSexo: "+sexo+"\n";
        return info;
    }
}
