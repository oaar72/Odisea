package escom.ipn.odisea;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import Data.UtilsWCF;
import Entidad.Argumento;
import Entidad.Dato;

import static android.content.Context.MODE_PRIVATE;

public class Fragmento_DatosMedicos extends Fragment implements View.OnClickListener
{
    private OnFragmentInteractionListener mListener;

    public Fragmento_DatosMedicos() {
        // Required empty public constructor
    }
    public Button btnGuardarDatosM;
    public TextView txtECD;
    public TextView txtAlergias;
    public TextView txtTipoSangre;
    public TextView msg;
    public RadioButton rbSi;
    public RadioButton rbNo;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_fragmento__datos_medicos, null);
        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        btnGuardarDatosM = getActivity().findViewById(R.id.btnGuardarDatosMedicos);
        txtECD           = getActivity().findViewById(R.id.txtECD);
        txtAlergias      = getActivity().findViewById(R.id.txtAlergias);
        txtTipoSangre    = getActivity().findViewById(R.id.txtTipoSangre);
        msg              = getActivity().findViewById(R.id.txtErrorRegistro);
        rbSi             = getActivity().findViewById(R.id.rbSiDona);
        rbNo             = getActivity().findViewById(R.id.rbNoDona);
        btnGuardarDatosM.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        final SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);

        String v_usuario        = pref.getString("mail", "");

        String msgError = "";

        String namespace    = getString(R.string.namespace);
        String url          = getString(R.string.url);
        String soap_action  = getString(R.string.soap_action);
        String method       = "addDatoMedico";

        String v_TipoSangre     = "";
        String v_ECD            = "";
        String v_Alergias       = "";


        if (txtTipoSangre.getText() == null)
        {
            v_TipoSangre = "";
        }
        else
        {
            v_TipoSangre = txtTipoSangre.getText().toString();
        }

        if (txtECD.getText() == null)
        {
            v_ECD = "";
        }
        else
        {
            v_ECD = txtECD.getText().toString();
        }

        if (txtAlergias.getText() == null)
        {
            v_Alergias = "";
        }
        else
        {
            v_Alergias = txtAlergias.getText().toString();
        }

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
            Intent intent = new Intent (v.getContext(), InicioUsuario.class);
            startActivityForResult(intent, 0);
        }

        if (!msgError.equals(""))
        {
            msg.setVisibility(View.VISIBLE);
            msg.setText(msgError);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
