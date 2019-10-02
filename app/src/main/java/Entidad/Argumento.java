package Entidad;

public class Argumento
{
    private String key;
    private String value;

    public Argumento(String key, String valor)
    {
        this.key = key;
        this.value = valor;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}