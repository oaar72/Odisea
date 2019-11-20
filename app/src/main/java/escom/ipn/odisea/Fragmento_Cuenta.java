package escom.ipn.odisea;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import Entidad.Persona;

import static android.content.Context.MODE_PRIVATE;

public class Fragmento_Cuenta extends Fragment implements View.OnClickListener
{
    Button btnCancelarCuenta;
    Button btnActualizar;

    TextView txtActualizaName;
    TextView txtActualizaPhone;
    TextView txtActualizaPaterno;

    public Fragmento_Cuenta()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_fragmento__cuenta, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        txtActualizaName    = getActivity().findViewById(R.id.txtActualizaName);
        txtActualizaPaterno = getActivity().findViewById(R.id.txtActualizaPaterno);
        txtActualizaPhone   = getActivity().findViewById(R.id.txtActualizaPhone);
        btnCancelarCuenta   = getActivity().findViewById(R.id.btnCancelarCuenta);
        btnActualizar       = getActivity().findViewById(R.id.btnActualizar);

        btnCancelarCuenta.setOnClickListener(this);
        btnActualizar.setOnClickListener(this);

        // Recupera Datos
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String mail     = pref.getString("mail", "");
        String nombre   = pref.getString("nombre", "");
        String paterno  = pref.getString("paterno", "");
        String phone    = pref.getString("phone", "");

        txtActualizaName.setText(nombre);
        txtActualizaPhone.setText(phone);
        txtActualizaPaterno.setText(paterno);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.btnActualizar:
                final SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);

                String v_usuario = pref.getString("mail", "");

                String namespace = getString(R.string.namespace);
                String url = getString(R.string.url);
                String soap_action = getString(R.string.soap_action);
                String method = "updatePersona";

                Argumento a_nombre = new Argumento("nombre", txtActualizaName.getText().toString());
                Argumento a_paterno = new Argumento("paterno", txtActualizaPaterno.getText().toString());
                Argumento a_numero = new Argumento("numero", txtActualizaPhone.getText().toString());
                Argumento a_usuario = new Argumento("codUser", v_usuario);

                UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

                String msgError = "";
                Context context = getActivity().getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence text;

                if (a_nombre.getValue().equals(""))
                {
                    msgError = "El nombre no puede ser vacio";
                }
                if (a_paterno.getValue().equals(""))
                {
                    msgError += "El primer apellido no puede ser vacio";
                }
                if (a_numero.getValue().equals(""))
                {
                    msgError += "El número no puede ser vacio";
                }

                if (msgError.equals(""))
                {
                    Persona p = new Persona();
                    p = service.updateUser(a_nombre, a_paterno, a_numero, a_usuario);

                    if (p.getMensaje().equals(" "))
                    {
                        text = "Datos actualizados";
                    }
                    else
                    {
                        text = p.getMensaje();
                    }
                }
                else
                {
                    text = msgError;
                }
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                break;
            case R.id.btnCancelarCuenta:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("¿Esta seguro de que desea eliminar su cuenta?");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        final SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);
                        String v_usuario    = pref.getString("mail", "");
                        String namespace    = getString(R.string.namespace);
                        String url          = getString(R.string.url);
                        String soap_action  = getString(R.string.soap_action);
                        String method       = "deletePersona";
                        Argumento a_usuario = new Argumento("codUser", v_usuario);
                        UtilsWCF service    = new UtilsWCF(namespace, url, soap_action + method, method);
                        service.deletePersona(a_usuario);
                        dialog.dismiss();
                        pref.edit().remove("Sesionado").apply();
                        pref.edit().remove("pass").apply();
                        pref.edit().remove("mail").apply();
                        pref.edit().remove("nombre").apply();
                        pref.edit().remove("paterno").apply();
                        pref.edit().remove("phone").apply();

                        Intent instancia = new Intent(getActivity(), MainActivity.class);
                        startActivity(instancia);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
    }
}
