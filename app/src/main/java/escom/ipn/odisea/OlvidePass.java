package escom.ipn.odisea;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Data.UtilsWCF;
import Entidad.Argumento;

public class OlvidePass extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide_pass);

        final TextView mail = findViewById(R.id.txtMailPass);
        Button btnRecover = findViewById(R.id.btnEnviar);

        btnRecover.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String msgError =  "";
                String v_mail = mail.getText().toString();

                if (!v_mail.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
                {
                    msgError = getString(R.string.msgCorreoNoValido) + ". ";
                }
                if (msgError.equals(""))
                {
                    String namespace    = getString(R.string.namespace);
                    String url          = getString(R.string.url);
                    String soap_action  = getString(R.string.soap_action);
                    String method       = "recoverPassword";

                    Argumento a_mail = new Argumento("mail", v_mail);

                    String con = "";

                    UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

                    con = service.recoverPassword(a_mail);

                    String[] datos = {"","",""};

                    if (con.contains(","))
                    {
                        datos = con.split(",");
                    }

                    String mailAsunto = "Odisea | Recuperación de contraseña";
                    String mailMensaje = "Se ha solicitado reestablecer la contraseña para el usuario "+ v_mail + "<br/>"
                            + "Contraseña temporal: " + datos[1] + "<br/>"
                            + "La contraseña temporal solo es vigente por 24 horas";

                    a_mail.setKey("destinatario");

                    Argumento a_asunto = new Argumento("asunto", mailAsunto);
                    Argumento a_mensaje = new Argumento("mensaje", mailMensaje);

                    url          = getString(R.string.mail_url);
                    soap_action  = getString(R.string.mail_soap_action);
                    method       = "sendMail";

                    service = new UtilsWCF(namespace, url, soap_action + method, method);

                    service.sendMail(a_mail, a_asunto, a_mensaje);

                    Context context = getApplicationContext();
                    CharSequence text = datos[0].toString();
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = msgError;
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

    }
}
