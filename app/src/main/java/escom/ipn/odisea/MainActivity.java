package escom.ipn.odisea;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import Data.UtilsWCF;
import Entidad.Argumento;
import Entidad.Persona;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRegister        = findViewById(R.id.btnRegistro);
        Button btnLogin           = findViewById(R.id.btnLogin);
        Button btnPass            = findViewById(R.id.btnOlvidePass);

        final TextView usuario    = findViewById(R.id.txtEmail);
        final TextView pass       = findViewById(R.id.txtPass);
        final TextView msg        = findViewById(R.id.txtError);

        btnLogin.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int cveUsuario   = 0;

                Persona persona = new Persona();

                String v_usuario = usuario.getText().toString();
                String v_pass    = pass.getText().toString();

                Argumento user = new Argumento("username", v_usuario);
                Argumento pass = new Argumento("pass", v_pass);

                Button btnLogin = findViewById(R.id.btnLogin);

                String msgError  = "";

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btnLogin.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                if (v_usuario.equals(""))
                {
                    msgError += getString(R.string.msgCorreoNoValido) + ". ";
                }

                if (v_pass.equals(""))
                {
                    msgError += getString(R.string.msgPassVacio) + ". ";
                }

                if (msgError.equals(""))
                {
                    String namespace    = getString(R.string.namespace);
                    String url          = getString(R.string.url);
                    String soap_action  = getString(R.string.soap_action);
                    String method       = "getUser";

                    UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

                    persona = service.login(user, pass);

                    if (persona.getMensaje().equals(" "))
                    {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putString("mail", persona.getCodUser());
                        editor.putString("nombre", persona.getNombre());
                        editor.putString("paterno", persona.getPaterno());
                        editor.putString("phone", persona.getTelefono());
                        editor.putBoolean("Sesionado", true);
                        editor.commit();

                        Intent instancia = new Intent(v.getContext(), InicioUsuario.class);
                        startActivity(instancia);
                    }
                    else
                    {
                        msgError += getString(R.string.msgFailLogin);
                    }
                }

                if (!msgError.equals(""))
                {
                    msg.setVisibility(View.VISIBLE);
                    msg.setText(msgError);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent (v.getContext(), RegistroUsuario.class);
                startActivityForResult(intent, 0);
            }
        });

        btnPass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent (v.getContext(), OlvidePass.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}