package escom.ipn.odisea;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import Data.UtilsWCF;
import Entidad.Argumento;
import Entidad.Contacto;

public class RegistroContactos extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_contactos);

        final Button btnAceptar     = findViewById(R.id.btnRegistrarContacto);
        final TextView nombre       = findViewById(R.id.txtContactoNombre);
        final TextView telefono     = findViewById(R.id.txtContactoNumero);
        final TextView mail         = findViewById(R.id.txtContactoMail);
        final TextView msg          = findViewById(R.id.txtErrorRegistro);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);

        Spinner spinner = (Spinner) findViewById(R.id.sp_clasificacion);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnAceptar.setOnClickListener(new View.OnClickListener()
        {
            String v_usuario    = pref.getString("mail", "");
            String v_nombre     = nombre.getText().toString();
            String v_telefono   = telefono.getText().toString();
            String v_mail       = mail.getText().toString();

            String msgError = "";

            @Override
            public void onClick(View v)
            {
                if (v_nombre.equals(""))
                {
                    msgError = getString(R.string.msgNombreVacio);
                }

                if (v_telefono.equals(""))
                {
                    msgError = getString(R.string.msgTelefonoVacio);
                }

                if (v_telefono.length() < 10)
                {
                    msgError = getString(R.string.msgTelefonoInvalido);
                }

                if (v_mail.equals(""))
                {
                    msgError = getString(R.string.msgCorreoNoValido);
                }

                if (!v_mail.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
                {
                    msgError = getString(R.string.msgCorreoNoValido);
                }

                if (msgError.equals(""))
                {
                    Argumento a_usuario     = new Argumento("usuario", v_usuario);
                    Argumento a_nombre      = new Argumento("nombre",v_nombre);
                    Argumento a_telefono    = new Argumento("telefono", v_telefono);
                    Argumento a_mail        = new Argumento("mail", v_mail);

                    String namespace    = getString(R.string.namespace);
                    String url          = getString(R.string.url);
                    String soap_action  = getString(R.string.soap_action);
                    String method       = "addContact";

                    Contacto con = new Contacto();

                    UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

                    con = service.addContact(a_usuario, a_nombre, a_telefono, a_mail);

                }
                else
                {
                    msg.setVisibility(View.VISIBLE);
                    msg.setText(msgError);
                }
            }
        });

    }
}
