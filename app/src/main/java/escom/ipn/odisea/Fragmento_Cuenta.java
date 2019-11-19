package escom.ipn.odisea;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        switch (v.getId())
        {
            case R.id.btnActualizar:

                Context context = getActivity().getApplicationContext();
                CharSequence text = "Datos actualizados";
                int duration = Toast.LENGTH_SHORT;

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
                        dialog.dismiss();
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
