package Data;

import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.List;

import Entidad.Argumento;
import Entidad.Contacto;

public class EnvioDato
{
    private String usuario;
    private String longitud;
    private String latitud;
    private int bateria;
    private String senial;

    private boolean envioMsm;
    private boolean envioMail;
    private boolean envioFb;
    private boolean envioWts;

    public EnvioDato(int bateria, String senial, boolean envioMsm, boolean envioMail, boolean envioFb, boolean envioWts) {
        this.longitud = longitud;
        this.latitud = latitud;
        this.bateria = bateria;
        this.senial = senial;
        this.envioMsm = envioMsm;
        this.envioMail = envioMail;
        this.envioFb = envioFb;
        this.envioWts = envioWts;
    }

    public void setUsuario(String usuario){ this.usuario = usuario; }

    public String getLongitud()
    {
        return this.latitud;
    }

    public void setLongitud(String longitud)
    {
        this.longitud = longitud;
    }

    public String getLatitud()
    {
        return this.latitud;
    }

    public void setLatitud(String latitud)
    {
        this.latitud = latitud;
    }

    public List<Contacto> getContactos()
    {
        List<Contacto> contactos = new ArrayList<Contacto>();

        //contactos.add(new Contacto("Oscar", "5545461176", "oalvarezvb4b@gmail.com"));
        //contactos.add(new Contacto("Ricardo", "5515275624", "rperezvb4b@gmail.com"));
        //contactos.add(new Contacto("Janis", "5583355593", "acoronavb4b@gmail.com"));

        String namespace    = "http://tempuri.org/";
        String url          = "https://wsodisea.azurewebsites.net/WSOdisea.svc?wsdl";
        String soap_action  = "http://tempuri.org/IWSOdisea/";
        String method       = "getContacts";

        Argumento a_mail = new Argumento("codUser", this.usuario );

        UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

        contactos = service.getContactos(a_mail);

        return contactos;
    }

    public void enviar()
    {
        List<Contacto> contactos = new ArrayList<Contacto>();
        contactos = getContactos();

        if (envioMsm)
        {
            for (Contacto c : contactos)
            {
                sendmsn(c.gettelefono(), constuirMensaje(2));
            }
        }

        if (envioMail)
        {
            String namespace = "http://tempuri.org/";
            String url = "https://wsmail.azurewebsites.net/ServicioMail.svc?wsdl";
            String soap_action = "http://tempuri.org/IServicioMail/";
            String method = "sendMail";

            UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);
            service = new UtilsWCF(namespace, url, soap_action + method, method);

            Argumento a_asunto = new Argumento("asunto", "Odisea | Ultima ubicación");
            Argumento a_mensaje = new Argumento("mensaje", constuirMensaje(1));

            for (Contacto c : contactos) {
                Argumento a_mail = new Argumento("destinatario", c.getmail());
                service.sendMail(a_mail, a_asunto, a_mensaje);
            }
        }
    }

    public String constuirMensaje(int tipo)
    {
        String msg = "";

        switch (tipo)
        {
            case 1:
                msg += "La ultima ubicación del usuario conocida del usaurio es: " + this.latitud + "," + this.longitud + "<br/>";
                msg += "Porcentaje de batería: " + this.bateria + "%<br />";
                msg += getInfo();
                break;
            case 2:
                msg += "Ubicación conocida: " + this.latitud + " " + this.longitud;
                msg += "\nBatería: " + this.bateria + "%\n";
                msg += getInfo();
                break;
        }
        return msg;
    }

    public void sendmsn(String phoneNo, String sms)
    {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, sms, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getInfo()
    {
        String info;

        String namespace    = "http://tempuri.org/";
        String url          = "https://wsodisea.azurewebsites.net/WSOdisea.svc?wsdl";
        String soap_action  = "http://tempuri.org/IWSOdisea/";
        String method       = "getInfo";

        Argumento a_mail = new Argumento("codUser", this.usuario );

        UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

        info = service.getInfo(a_mail);

        return info;
    }
}
