package escom.ipn.odisea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RegistroUsuario extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
    }
    protected void onStart()
    {
        super.onStart();

        Button btnCancelar = findViewById(R.id.btn_cancelar);
        Button btnPermitir = findViewById(R.id.btn_Permitir);

        btnCancelar.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

            }
        });

        btnPermitir.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent (v.getContext(), RegistroUsuarioDatos.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}