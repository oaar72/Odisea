package Entidad;

public class Contacto
{
    private String nombre;
    private String telefono;
    private String mail;
    private String mensaje;

    public Contacto()
    {
        this.nombre     = "";
        this.telefono   = "";
        this.mail       = "";
        this.mensaje    = "";
    }

    public Contacto(String nombre, String telefono, String mail)
    {
        this.nombre     = nombre;
        this.telefono   = telefono;
        this.mail       = mail;
    }

    public String getnombre()
    {
        return this.nombre;
    }
    public void setnombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String gettelefono()
    {
        return this.telefono;
    }
    public void settelefono(String telefono)
    {
        this.telefono = telefono;
    }

    public String getmail()
    {
        return this.mail;
    }
    public void setmail(String mail)
    {
        this.mail = mail;
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
