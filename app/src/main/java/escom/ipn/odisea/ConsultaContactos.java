package escom.ipn.odisea;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import Data.UtilsWCF;
import Entidad.Argumento;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsultaContactos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ConsultaContactos extends Fragment {

    //private OnFragmentInteractionListener mListener;

    public ConsultaContactos() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_consulta_contactos, container, false);

         final ListView list;
         String[] sistemas = {"Ubuntu", "Android", "iOS", "Windows", "Mac OSX",
                "Google Chrome OS", "Debian", "Mandriva", "Solaris", "Unix"};

        // Inflate the layout for this fragment
        String namespace    = getString(R.string.namespace);
        String url          = getString(R.string.url);
        String soap_action  = getString(R.string.soap_action);
        String method       = "getContactos";

        UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

        SharedPreferences pref = this.getActivity().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String mail = pref.getString("mail", "");
        Argumento parametros = new Argumento("codUser",mail);

        //service.getContact(parametros);

        list = (ListView) v.findViewById(R.id.listContactos);

        ArrayList<String> contactos =new ArrayList<String>();

        for (String item:service.traerContactos(parametros)
             ) {
            contactos.add(item.toString());
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,contactos);
        list.setAdapter(adaptador);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //Intent myIntent = new Intent(getActivity(), getActivity().getClass()); //NextActivity.class);
                //getActivity().startActivity(myIntent);
              //String contacto = list.getSelectedItem().toString();

                actualizarContactos nextFrag= new actualizarContactos();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Contenedor, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();



            }
        });



        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
     /*   if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
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
}
