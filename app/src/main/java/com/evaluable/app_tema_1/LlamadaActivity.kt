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

    /*
    gestionarPermisoLlamada, lo que hace principalmente es la verificacion de sdk ya que
    un sdk mayor a 22 tendra que pedir permiso y si se ejecuta en una inferior a 23 este no tendra
    que pedir permisos ninguno.
    Igualmente este llama a funcion realizar llamada depues de pasar por los 2 verificaciones
     */
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

    /*
        Este metodo pedirPermisoLlamada es lo que hemos comentado antes, en caso de que sea
        mayor a un sdk 22 este tendra que solicitar permisos para poder ejecutar el programa, en caso
        contrario, este no podrá hacer nada, si no que tendra que abrir de nuevo el programa para que pida el permiso
        o darle al boton de llamar para preguar si le permite el permiso
     */
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