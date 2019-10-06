package escom.ipn.odisea;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Data.UtilsWCF;
import Entidad.Argumento;
import Entidad.Persona;

public class RegistroUsuarioDatos extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario_datos);

        Button btnAceptar   = findViewById(R.id.btnConfirmaDatos);

        final TextView phone    = findViewById(R.id.txtPhone);
        final TextView mail     = findViewById(R.id.txtMailRegistro);
        final TextView pass     = findViewById(R.id.txtPassRegistro);
        final TextView nombre   = findViewById(R.id.txtNombreRegistro);
        final TextView paterno  = findViewById(R.id.txtPaternoRegistro);
        final TextView msg      = findViewById(R.id.txtErrorRegistro);

        btnAceptar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int cveUsuario = 0;

                String v_mail    = mail.getText().toString();
                String v_pass    = pass.getText().toString();
                String v_name    = nombre.getText().toString();
                String v_paterno = paterno.getText().toString();
                String v_phone   = phone.getText().toString();

                String msgError = "";

                Persona user;

                if (v_mail.equals(""))
                {
                    msgError += getString(R.string.msgCorreoNoValido) + ". ";
                }

                if (v_pass.equals(""))
                {
                    msgError += getString(R.string.msgPassVacio) + ". ";
                }

                if (v_name.equals(""))
                {
                    msgError += getString(R.string.msgNombreVacio) + ". ";
                }

                if (v_paterno.equals(""))
                {
                    msgError += getString(R.string.msgApellidoVacio) + ". ";
                }

                if (v_phone.equals(""))
                {
                    msgError += getString(R.string.msgTelefonoVacio) + ". ";
                }

                if (msgError.equals(""))
                {
                    String namespace    = getString(R.string.namespace);
                    String url          = getString(R.string.url);
                    String soap_action  = getString(R.string.soap_action);
                    String method       = "addUser";

                    Argumento a_mail    = new Argumento("mail", v_mail);
                    Argumento a_pass    = new Argumento("pass", v_pass);
                    Argumento a_nombre  = new Argumento("nombre", v_name);
                    Argumento a_paterno = new Argumento("paterno", v_paterno);
                    Argumento a_phone   = new Argumento("phone", v_phone);

                    UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

                    user  = service.addUser(a_mail, a_pass, a_nombre, a_paterno, a_phone);

                    msgError = user.getMensaje();

                    if (msgError.equals(" "))
                    {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putString("mail", v_mail);
                        editor.putString("pass", v_pass);
                        editor.putString("nombre", v_name);
                        editor.putString("paterno", v_paterno);
                        editor.putString("phone", v_phone);
                        editor.apply();

                        String mailAsunto = getString(R.string.mailRegistro);
                        String mailMensaje = "Estimado " + v_name + "<br/><br/>" +
                                getString(R.string.mailBienvenida) + "<br/><br/>" +
                                getString(R.string.mailDatos) + "<br/>" +
                                getString(R.string.mailDatoUsuario) + " " + v_mail + "<br/>"+
                                getString(R.string.mailDatoPass) + " " + v_pass + "<br/><br/>" +
                                getString(R.string.mailFinMensaje);

                        a_mail.setKey("destinatario");
                        Argumento a_asunto = new Argumento("asunto", mailAsunto);
                        Argumento a_mensaje = new Argumento("mensaje", mailMensaje);


                        url          = getString(R.string.mail_url);
                        soap_action  = getString(R.string.mail_soap_action);
                        method       = "sendMail";

                        service = new UtilsWCF(namespace, url, soap_action + method, method);

                        service.sendMail(a_mail, a_asunto, a_mensaje);

                        Intent instancia = new Intent(v.getContext(), InicioUsuario.class);
                        startActivity(instancia);
                    }
                    else
                    {
                        msg.setVisibility(View.VISIBLE);
                        msg.setText(msgError);
                    }
                }
                if (!msgError.equals(""))
                {
                    msg.setVisibility(View.VISIBLE);
                    msg.setText(msgError);
                }
            }
        });
    }
}
