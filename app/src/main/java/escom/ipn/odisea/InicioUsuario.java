package escom.ipn.odisea;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class InicioUsuario extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Fragmento_Configuracion.OnFragmentInteractionListener,
        Fragmento_Contactos.OnFragmentInteractionListener,
        Fragmento_Lugares.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_usuario);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);
        Boolean sesion = pref.getBoolean("Sesionado", false);
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String mail = pref.getString("mail", "");
        String pass = pref.getString("pass", "");
        String nombre = pref.getString("nombre", "");
        String paterno = pref.getString("paterno", "");
        String phone = pref.getString("phone", "");

        editor.commit();

        TextView t_mail = findViewById(R.id.txtInicioMail);
        TextView t_name = findViewById(R.id.txtInicioName);

        String name = nombre + " " + paterno;

        t_mail.setText(mail);
        t_name.setText(name);

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment frag = null;

        boolean isSelectedFrag = false;

        if (id == R.id.itemInicio)
        {
            frag = new Fragmento_Inicio();
            isSelectedFrag = true;
        }
        else if (id == R.id.itemContactos)
        {
            frag = new Fragmento_Contactos();
            isSelectedFrag = true;
        }
        else if (id == R.id.itemLugares)
        {
            frag = new Fragmento_Lugares();
            isSelectedFrag = true;
        }
        else if (id == R.id.itemAccount)
        {
            frag = new Fragmento_Cuenta();
            isSelectedFrag = true;
        }
        else if (id == R.id.itemConfiguracion)
        {
            frag = new Fragmento_DatosMedicos();
            isSelectedFrag = true;
        }
        else if (id == R.id.itemLogOut)
        {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);
            pref.edit().remove("pass").apply();
            pref.edit().remove("Sesionado").apply();

            Intent instancia = new Intent(this, MainActivity.class);
            startActivity(instancia);
        }

        if (isSelectedFrag)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, frag).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
