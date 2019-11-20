package escom.ipn.odisea;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import Data.UtilsWCF;


/**
 * A simple {@link Fragment} subclass.
 */
public class actualizarContactos extends Fragment {

 String contacto;

    public actualizarContactos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_actualizar_contactos, container, false);

        // Inflate the layout for this fragment
        String namespace    = getString(R.string.namespace);
        String url          = getString(R.string.url);
        String soap_action  = getString(R.string.soap_action);
        String method       = "traerGrupos";

        UtilsWCF service = new UtilsWCF(namespace, url, soap_action + method, method);

        ArrayList<String> grupos =new ArrayList<String>();
        for (String item:service.traerGrupos()
        ) {
            grupos.add(item);
        }
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        //ArrayAdapter<String> comboAdapter;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, grupos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
      //  Button btnBorrar = (Button) getActivity().findViewById(R.id.btnBorrarContacto);

        //btnBorrar.setOnClickListener ((View.OnClickListener) getActivity());

    }

}
