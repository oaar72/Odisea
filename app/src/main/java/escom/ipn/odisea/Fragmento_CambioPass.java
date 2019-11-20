package escom.ipn.odisea;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Data.UtilsWCF;
import Entidad.Argumento;

import static android.content.Context.MODE_PRIVATE;

public class Fragmento_CambioPass extends Fragment implements View.OnClickListener
{
    public Fragmento_CambioPass()
    {
        // Required empty public constructor
    }

    Button btnActualizar;

    TextView txtOldPass;
    TextView txtNewPass;
    TextView txtConfirmPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_fragmento_cambio_pass, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        txtOldPass      = getActivity().findViewById(R.id.txtPassold);
        txtNewPass      = getActivity().findViewById(R.id.txtNuevaPass);
        txtConfirmPass  = getActivity().findViewById(R.id.txtConfirmaNuvaPass);
        btnActualizar   = getActivity().findViewById(R.id.btnActualizarPass);

        btnActualizar.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.btnActualizarPass:
                final SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);

                String v_usuario = pref.getString("mail", "");

                String namespace = getString(R.string.namespace);
                String url = getString(R.string.url);
                String soap_action = getString(R.string.soap_action);
                String method = "updatePass";

                Argumento a_usuario = new Argumento("codUser", v_usuario);
                Argumento a_pass = new Argumento("pass1", txtOldPass.getText().toString());
                Argumento a_pass_new = new Argumento("pass2", txtNewPass.getText().toString());

                UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

                String msgError = "";
                Context context = getActivity().getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence text;

                if (a_pass.getValue().equals(""))
                {
                    msgError = "Se debe de proporcionar la contraseña anterior";
                }
                if (a_pass_new.getValue().equals(""))
                {
                    msgError += "Se debe de proporcionar una contraseña";
                }
                if (!txtNewPass.getText().toString().equals(txtConfirmPass.getText().toString()))
                {
                    msgError += "Las contraseñas no coinciden";
                }

                if (msgError.equals(""))
                {
                    service.updatePass(a_usuario, a_pass, a_pass_new);
                    text = "Datos actualizados";
                }
                else
                {
                    text = msgError;
                }
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                pref.edit().remove("pass").apply();
                pref.edit().remove("Sesionado").apply();

                Intent instancia = new Intent(getActivity(), MainActivity.class);
                startActivity(instancia);
                break;
        }
    }
}
