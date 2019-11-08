package Data;

import android.os.Build;
import android.os.StrictMode;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import Entidad.Argumento;
import Entidad.Contacto;
import Entidad.Persona;

public class UtilsWCF
{
    private String NAMESPACE;
    private String URL;
    private String SOAP_ACTION;
    private String METHOD_NAME;

    public UtilsWCF(String namespace, String url, String soap_action, String method_name)
    {
        this.NAMESPACE   = namespace;
        this.URL         = url;
        this.SOAP_ACTION = soap_action;
        this.METHOD_NAME = method_name;
    }

    public Persona login(Argumento user, Argumento pass)
    {
        Persona persona = new Persona();

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty(user.getKey(), user.getValue());
        request.addProperty(pass.getKey(), pass.getValue());
        
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try
        {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();

            if (response.getProperty("mensaje") != null)
            {
                persona.setMensaje(response.getPropertyAsString("mensaje"));
            }

            if (persona.getMensaje().equals(" "))
            {
                persona.setCodUser(response.getPropertyAsString("codUser"));
                persona.setNombre(response.getPropertyAsString("nombre"));
                persona.setPaterno(response.getPropertyAsString("paterno"));
                persona.setTelefono(response.getPropertyAsString("telefono"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            persona.setMensaje("Ocurrio un Error.");
        }
        return persona;
    }

    public Persona addUser(Argumento mail, Argumento pass, Argumento nombre, Argumento paterno, Argumento phone, Argumento token)
    {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty(mail.getKey(), mail.getValue());
        request.addProperty(pass.getKey(), pass.getValue());
        request.addProperty(nombre.getKey(), nombre.getValue());
        request.addProperty(paterno.getKey(), paterno.getValue());
        request.addProperty(phone.getKey(), phone.getValue());
        request.addProperty(token.getKey(), token.getValue());

        Persona user = new Persona();

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try
        {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();

            if (response.getProperty("mensaje") != null)
            {
                user.setMensaje(response.getPropertyAsString("mensaje"));
            }

            if (user.getMensaje().equals(" "))
            {
                user.setCodUser(response.getPropertyAsString("codUser"));
                user.setNombre(response.getPropertyAsString("nombre"));
                user.setPaterno(response.getPropertyAsString("paterno"));
                user.setTelefono(response.getPropertyAsString("telefono"));
                user.settoken(response.getPropertyAsString("token"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return user;
    }

    public String sendMail(Argumento destinatario, Argumento asunto, Argumento mensaje)
    {
        String msgError = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty(destinatario.getKey(), destinatario.getValue());
        request.addProperty(asunto.getKey(), asunto.getValue());
        request.addProperty(mensaje.getKey(), mensaje.getValue());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        if (Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try
        {
            androidHttpTransport.call(SOAP_ACTION, envelope);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return msgError;
    }

    public Contacto addContact(Argumento usuario, Argumento nombre, Argumento phone, Argumento mail)
    {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty(usuario.getKey(), usuario.getValue());
        request.addProperty(nombre.getKey(), nombre.getValue());
        request.addProperty(mail.getKey(), mail.getValue());
        request.addProperty(phone.getKey(), phone.getValue());

        Contacto contact = new Contacto();

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try
        {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();

            if (response.getProperty("mensaje") != null)
            {
                contact.setMensaje(response.getPropertyAsString("mensaje"));
            }

            if (contact.getMensaje().equals(" "))
            {
                contact.setnombre(response.getPropertyAsString("nombre"));
                contact.settelefono(response.getPropertyAsString("telefono"));
                contact.setmail(response.getPropertyAsString("mail"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return contact;
    }
}