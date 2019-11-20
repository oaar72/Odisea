package escom.ipn.odisea;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import Data.UtilsWCF;
import Entidad.Argumento;
import Entidad.Contacto;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragmento_Contactos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragmento_Contactos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragmento_Contactos extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public Button btnRegistroContactos;
    public TextView txtNombre;
    public TextView txtTelefono;
    public TextView txtCorreoElectronico;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Fragmento_Contactos() {
        // Required empty public constructor
    }
    Button btnAceptar;
    TextView nombre;
    TextView telefono;
    TextView mail;
    TextView msg ;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragmento_Contactos.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragmento_Contactos newInstance(String param1, String param2)
    {
        Fragmento_Contactos fragment = new Fragmento_Contactos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragmento__contactos, container, false);

        // Inflate the layout for this fragment
        String namespace    = getString(R.string.namespace);
        String url          = getString(R.string.url);
        String soap_action  = getString(R.string.soap_action);
        String method       = "traerGrupos";

        UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

        ArrayList<String> grupos =new ArrayList<String>();
        //grupos = service.traerGrupos();
        for (String item:service.traerGrupos()
        ) {
            grupos.add(item);
        }
        Spinner spinner = (Spinner) v.findViewById(R.id.sp_clasificacion);
        //ArrayAdapter<String> comboAdapter;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, grupos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        final SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);

        //Button btnAceptar     = getActivity().findViewById(R.id.btnRegistrarContacto);
        TextView nombre       = getActivity().findViewById(R.id.txtContactoNombre);
        TextView telefono     = getActivity().findViewById(R.id.txtContactoNumero);
        TextView mail         = getActivity().findViewById(R.id.txtContactoMail);
        TextView msg          = getActivity().findViewById(R.id.txtErrorRegistro);
        Spinner clasifi       = getActivity().findViewById(R.id.sp_clasificacion);

        String v_usuario    = pref.getString("mail", "");
        String v_nombre     = nombre.getText().toString();
        String v_telefono   = telefono.getText().toString();
        String v_mail       = mail.getText().toString();
        String v_clasifi    = clasifi.getSelectedItem().toString();

        String msgError = "";

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

       /* if (!v_mail.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
        {
            msgError = getString(R.string.msgCorreoNoValido);
        }*/

        if (msgError.equals(""))
        {
            Argumento a_usuario     = new Argumento("usuario", v_usuario);
            Argumento a_nombre      = new Argumento("nombre",v_nombre);
            Argumento a_telefono    = new Argumento("telefono", v_telefono);
            Argumento a_mail        = new Argumento("mail", v_mail);
            Argumento clasificacion = new Argumento("descripcion", v_clasifi);

            String namespace    = getString(R.string.namespace);
            String url          = getString(R.string.url);
            String soap_action  = getString(R.string.soap_action);
            String method       = "addContact";

            Contacto con = new Contacto();

            UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

            con = service.addContact(a_usuario, a_nombre, a_telefono, a_mail,clasificacion);

            ConsultaContactos nextFrag= new ConsultaContactos();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.Contenedor, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();

        }
        else
        {
            msg.setVisibility(View.VISIBLE);
            msg.setText(msgError);
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        btnAceptar = getActivity().findViewById(R.id.btnRegistrarContacto);
        txtNombre            = getActivity().findViewById(R.id.txtContactoNombre);
        txtTelefono          = getActivity().findViewById(R.id.txtContactoNumero);
        txtCorreoElectronico = getActivity().findViewById(R.id.txtContactoMail);

        btnAceptar.setOnClickListener (this);

    }

}
