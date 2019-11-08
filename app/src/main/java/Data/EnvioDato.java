package Data;

import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.List;

import Entidad.Argumento;

public class EnvioDato
{
    private String longitud;
    private String latitud;
    private int bateria;
    private String senial;

    private boolean envioMsm;
    private boolean envioMail;
    private boolean envioFb;
    private boolean envioWts;

    public EnvioDato(String longitud, String latitud, int bateria, String senial, boolean envioMsm, boolean envioMail, boolean envioFb, boolean envioWts)
    {
        this.longitud   = longitud;
        this.latitud    = latitud;
        this.bateria    = bateria;
        this.senial     = senial;
        this.envioMsm   = envioMsm;
        this.envioMail  = envioMail;
        this.envioFb    = envioFb;
        this.envioWts   = envioWts;
    }

    public void enviar()
    {
        if (envioMsm)
        {
            sendmsn("5588140368", constuirMensaje());
        }

        if (envioMail)
        {
            String namespace    = "http://tempuri.org/";
            String url          = "https://wsmail.azurewebsites.net/ServicioMail.svc?wsdl";
            String soap_action  = "http://tempuri.org/IServicioMail/";
            String method       = "sendMail";

            UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);
            service = new UtilsWCF(namespace, url, soap_action + method, method);

            Argumento a_asunto = new Argumento("asunto","Odisea | Ultima ubicación");
            Argumento a_mensaje = new Argumento("mensaje",constuirMensaje());

            List<String> contactos = new ArrayList<String>();

            contactos.add("oalvarezvb4b@gmail.com");
            contactos.add("rperezvb4b@gmail.com");
            contactos.add("acoronavb4b@gmail.com");

            for (String item: contactos)
            {
                Argumento a_mail = new Argumento("destinatario", item);
                service.sendMail(a_mail, a_asunto, a_mensaje);
            }
        }

        if (envioFb)
        {

        }

        if (envioWts)
        {

        }
    }

    public String constuirMensaje()
    {
        String msg = "";

        msg += "La ultima ubicación del usuario conocida del usaurio es: "+ this.latitud + "," + this.longitud + "<br/>";
        msg += "Porcentaje de batería: " + this.bateria + "%";

        return msg;
    }

    public void sendmsn(String phoneNo, String sms)
    {
        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, sms, null, null);
            //Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            //Toast.makeText(getApplicationContext(), "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
