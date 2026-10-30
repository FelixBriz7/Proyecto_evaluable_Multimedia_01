package com.evaluable.app_tema_1

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import androidx.annotation.RequiresApi
import com.evaluable.app_tema_1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding;

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(mainBinding.root);


        //Guardamos la información que proviene de Config_Activity


        val telefono = intent.getStringExtra("extra_telefono");
        val url = intent.getStringExtra("extra_url");


        //Boton de regreso hacia los ajustes
        /*
        Para especificar un poco mas lo que hacemos con este metodo es que evitamos la
        duplicidad de pantallas o de "Activitys", haciendo que las interfaces gráficas se muevan o roten
        dependiendo de donde esté situado el usuario
         */

        mainBinding.imageButtonMainActivityBackConfig.setOnClickListener {

            val intentVolver = Intent(this, Config_Activity::class.java);
            intentVolver.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intentVolver);

        }

        //Boton que redirige la url hacia el navegador

        mainBinding.imageButtonMainActivityUrl.setOnClickListener {
            val intentUrl = Intent(Intent.ACTION_VIEW, Uri.parse(url!!));
            startActivity(intentUrl);
        }

        /*
        Aqui le decimos al programa que habra dentro de nuestra propia aplicación
        el correo o el porgrama predefinido de correo que tenga puesto es usuario, normalmente
        Gmail, pero para que abra otra aplicación añadimos la siguiente linea
        "intentCorreo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)"
         */

        mainBinding.imageButtonMainActivityCorreo.setOnClickListener {
            val intentCorreo =
                Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL);
            intentCorreo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentCorreo);
        }

        //Boton que programará 2 minutos de alarma

        mainBinding.imageButtonMainActivityAlarma.setOnClickListener {

            val calendario = Calendar.getInstance();
            calendario.add(Calendar.MINUTE, 2);

            val hora = calendario.get(Calendar.HOUR_OF_DAY);
            val minutos = calendario.get(Calendar.MINUTE);

            val intentAlarma = Intent(AlarmClock.ACTION_SET_ALARM);
            intentAlarma.putExtra(AlarmClock.EXTRA_HOUR, hora);
            intentAlarma.putExtra(AlarmClock.EXTRA_MINUTES, minutos);
            intentAlarma.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
            startActivity(intentAlarma);

        }

        mainBinding.imageButtonMainActivityLlamar.setOnClickListener {

            val intentLlamar = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse(telefono!!);

            }


        }

    }
}