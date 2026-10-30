package com.evaluable.app_tema_1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.evaluable.app_tema_1.databinding.ActivityLlamadaBinding

class LlamadaActivity : AppCompatActivity() {

    private lateinit var llamadaBinding: ActivityLlamadaBinding;

    private var telefono: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        llamadaBinding = ActivityLlamadaBinding.inflate(layoutInflater);
        setContentView(llamadaBinding.root);

        /*
        Extraemos la información de extra_telefono para aplicarselo al boton de llamada
        mas tarde y tambien lo utilizaremos para que lo muestre en pantalla, es decir donde esta el TextView
        aparecera el numero de telefono para que el usuario vea el numero que va a llamar
         */

        telefono = intent.getStringExtra("extra_telefono");
        llamadaBinding.textViewNumeroTelefonoLlamada.text = telefono;

        llamadaBinding.imageButtonLlamarLlamada.setOnClickListener {
            realizarLlamada();
        }

        //Boton para regresar a MainActivity

        llamadaBinding.imageButtonVueltaAtrasLlamada.setOnClickListener {
            finish();
        }


    }

    //Metodo externo que realiza la acción de llamada

    private fun realizarLlamada() {
        val intentLlamar = Intent(Intent.ACTION_CALL, Uri.parse("tel:$telefono"));
        startActivity(intentLlamar);
    }


}