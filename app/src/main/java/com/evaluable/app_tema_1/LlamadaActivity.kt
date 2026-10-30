package com.evaluable.app_tema_1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.evaluable.app_tema_1.databinding.ActivityLlamadaBinding
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class LlamadaActivity : AppCompatActivity() {

    private lateinit var llamadaBinding: ActivityLlamadaBinding;

    private var telefono: String? = null;

    private  val codigoPermisoLlamada = 100;

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
            gestionarPermisoLlamada();
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

    private fun gestionarPermisoLlamada() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            if (permiso == PackageManager.PERMISSION_GRANTED) {
                realizarLlamada()
            } else {
                pedirPermisoLlamada.launch(Manifest.permission.CALL_PHONE)
            }
        } else {
            realizarLlamada()
        }
    }

    private val pedirPermisoLlamada = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            realizarLlamada()
        } else {
            Toast.makeText(this, "Sin permiso no se puede realizar la llamada", Toast.LENGTH_LONG).show()
        }
    }


}