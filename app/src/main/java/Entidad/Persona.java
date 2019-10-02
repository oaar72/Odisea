package Entidad;

public class Persona
{
    private String nombre;
    private String paterno;
    private String materno;
    private String codUser;
    private String pasword;
    private String telefono;
    private String mensaje;

    public Persona()
    {
        this.nombre   = "";
        this.paterno  = "";
        this.materno  = "";
        this.codUser  = "";
        this.codUser  = "";
        this.pasword  = "";
        this.telefono = "";
        this.mensaje  = "";
    }

    public Persona(String nombre, String paterno, String materno, String codUser, String password, String telefono)
    {
        this.nombre  = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.codUser = codUser;
        this.codUser = codUser;
        this.pasword = password;
        this.telefono = telefono;
    }

    public String getNombre()
    {
        return this.nombre;
    }
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getPaterno()
    {
        return this.paterno;
    }
    public void setPaterno(String paterno)
    {
        this.paterno = paterno;
    }

    public String getMaterno()
    {
        return this.materno;
    }
    public void setMaterno(String materno)
    {
        this.materno = materno;
    }

    public String getCodUser()
    {
        return this.codUser;
    }
    public void setCodUser(String codUser)
    {
        this.codUser = codUser;
    }

    public String getTelefono()
    {
        return this.telefono;
    }
    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }

    public String getPasword()
    {
        return this.pasword;
    }
    public void setPasword(String password)
    {
        this.pasword = password;
    }

    public String getMensaje()
    {
        return this.mensaje;
    }
    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
    }
}