package Entidad;

public class Dato {

    private String tipo;
    private String descripcion;
    private String valor;
    private String mensaje;

    public Dato()
    {
        this.tipo     = "";
        this.descripcion   = "";
        this.valor       = "";
        this.mensaje     = "";

    }

    public Dato(String tipo, String descripcion, String valor)
    {
        this.tipo     = tipo;
        this.descripcion   = descripcion;
        this.valor       = valor;
    }

    public String gettipo()
    {
        return this.tipo;
    }
    public void settipo(String tipo)
    {
        this.tipo = tipo;
    }

    public String getdescripcion()
    {
        return this.descripcion;
    }
    public void setdescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public String getMensaje()
    {
        return this.mensaje;
    }
    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
    }

    public String getvalor()
    {
        return this.valor;
    }
    public void setvalor(String valor)
    {
        this.valor = valor;
    }
}
