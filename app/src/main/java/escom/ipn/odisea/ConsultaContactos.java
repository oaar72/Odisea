package escom.ipn.odisea;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import Data.UtilsWCF;
import Entidad.Argumento;

import static android.content.Context.MODE_PRIVATE;

public class ConsultaContactos extends Fragment implements ListView.OnClickListener{

    public ConsultaContactos()
    {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_consulta_contactos, container, false);

        ListView list;

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
        Button btnAgregarContacto = v.findViewById(R.id.btnAgregarContacto);

        btnAgregarContacto.setOnClickListener(this);

        ArrayList<String> contactos =new ArrayList<String>();

        for (String item:service.traerContactos(parametros))
        {
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

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnAgregarContacto:

                Fragmento_Contactos nextFrag= new Fragmento_Contactos();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Contenedor, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
