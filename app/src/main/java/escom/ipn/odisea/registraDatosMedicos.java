package escom.ipn.odisea;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import Data.UtilsWCF;
import Entidad.Argumento;
import Entidad.Contacto;
import Entidad.Dato;

public class registraDatosMedicos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_datos_medicos);

        final Button btnGuardarDatosM     = findViewById(R.id.btnGuardarDatosMedicos);
        final TextView txtECD             = findViewById(R.id.txtECD);
        final TextView txtAlergias        = findViewById(R.id.txtAlergias);
        final TextView txtTipoSangre      = findViewById(R.id.txtTipoSangre);
        final TextView msg                = findViewById(R.id.txtErrorRegistro);
        final RadioButton rbSi            = findViewById(R.id.rbSiDona);
        final RadioButton rbNo            = findViewById(R.id.rbNoDona);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);

        btnGuardarDatosM.setOnClickListener(new View.OnClickListener()
        {
            String v_usuario        = pref.getString("mail", "");
            String v_TipoSangre     = txtTipoSangre.getText().toString();
            String v_ECD            = txtECD.getText().toString();
            String v_Alergias       = txtAlergias.getText().toString();

            String msgError = "";

            @Override
            public void onClick(View v)
            {
                String namespace    = getString(R.string.namespace);
                String url          = getString(R.string.url);
                String soap_action  = getString(R.string.soap_action);
                String method       = "addDatoMedico";

                if (v_TipoSangre.equals("") && v_ECD.equals("")&& v_Alergias.equals(""))
                {
                    msgError = getString(R.string.msgNoSeHaregistradoNingunDato);
                }
                else
                {
                    if(!v_TipoSangre.equals(""))
                    {
                        Argumento a_valor = new Argumento("valor",v_TipoSangre);
                        Argumento a_descripcion      = new Argumento("descripcion","Tipo Sangre");
                        Argumento a_usuario  = new Argumento("usuario",v_usuario);
                        Dato con = new Dato();
                        UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

                        con = service.addDato(a_valor, a_descripcion,a_usuario);
                    }
                    if(!v_ECD.equals(""))
                    {
                        Argumento a_valor            = new Argumento("valor",v_ECD);
                        Argumento a_descripcion      = new Argumento("descripcion","ECD");
                        Argumento a_usuario  = new Argumento("usuario",v_usuario);
                        Dato con = new Dato();
                        UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

                        con = service.addDato(a_valor, a_descripcion,a_usuario);
                    }
                    if(!v_Alergias.equals(""))
                    {
                        Argumento a_valor            = new Argumento("valor",v_Alergias);
                        Argumento a_descripcion      = new Argumento("descripcion","Alergias");
                        Argumento a_usuario  = new Argumento("usuario",v_usuario);
                        Dato con = new Dato();
                        UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

                        con = service.addDato(a_valor, a_descripcion,a_usuario);
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
