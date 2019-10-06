package escom.ipn.odisea;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("OdiseaPreferences", MODE_PRIVATE);
                boolean sesion = pref.getBoolean("Sesionado", false);

                if (sesion)
                {
                    Intent instancia = new Intent(SplashScreen.this, InicioUsuario.class);
                    startActivity(instancia);
                }
                else
                {
                    Intent instancia = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(instancia);
                }
            }
        }, 1000);
    }
}
