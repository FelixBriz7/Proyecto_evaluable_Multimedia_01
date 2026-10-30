package com.evaluable.app_tema_1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.evaluable.app_tema_1.databinding.ActivityConfigBinding
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil

class Config_Activity : AppCompatActivity() {

    //Componentes con ViewBinding
    private lateinit var  configBinding: ActivityConfigBinding;

    //Variable para guardar todos los datos de la app
    private lateinit var sharedFich: SharedPreferences;

    /*
        Usamos variables con etiquetas para guardar y poder recuperar los datos
        que se guardan mediante el usuario lo valla poniendo
     */

    private val nameSharedFich = "shared_fich";
    private val nameSharedPhone = "numero_de_telefono";
    private val nameSharedURL = "web_url";


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //Funcion con la que vamos a inflar las vistas
        configBinding = ActivityConfigBinding.inflate(layoutInflater);
        setContentView(configBinding.root);

        /*
        Esta linea le dice andorid que genere o busque el archivo donde se guardan
        los datos de la aplicación,es decir como una mini memoria.
        Es como un especie de cuaderno donde va apuntar las cosas
        que el usuario guarde
         */

        sharedFich = getSharedPreferences(nameSharedFich, Context.MODE_PRIVATE);


        /*
        En caso de que hubiera datos guardados este lo mostrará en pantalla

        Es decir si existe algun dato guardado lo devuelve, para no tener que poner los mimos datos
        siempre, pero en caso de que yo existan o haya datos, este lo dejará en vacio ("")
         */

        configBinding.editTextPhoneConfig.setText(sharedFich.getString(nameSharedPhone, ""));
        configBinding.editTextTextURLConfig.setText(sharedFich.getString(nameSharedURL, ""));

        //Configuración del boton

        configBinding.buttonGuardarConfig.setOnClickListener {

            //Estas variables es donde guardan los datos escritos por el usuario y quitanfo los espacios
            // del principio y final para para evitrar el minimo de errores

            val telefono = configBinding.editTextPhoneConfig.text.toString().trim();
            val url = configBinding.editTextTextURLConfig.text.toString().trim();

            when {
                telefono.isEmpty() -> {
                    Toast.makeText(this, "Debes de introducir un numero de telefono", Toast.LENGTH_SHORT).show();
                }
                //Distinto de true
                !esTelefonoEspanol(telefono) -> {
                    Toast.makeText(this, "Telefono no válido, debe ser un numero Español", Toast.LENGTH_SHORT).show();
                }

                url.isEmpty() -> {
                    Toast.makeText(this, "Debes de introducir una url", Toast.LENGTH_SHORT).show();
                }

                !esURLValida(url) -> {
                    Toast.makeText(this, "La URL no es valida. Ejemplo: https://www.google.com", Toast.LENGTH_LONG).show()
                }


                else -> {
                    // Guardamos el teléfono en formato internacional Español
                    val  telefonoFinal = formatearTelefono(telefono);

                    // Guardamos en SharedPreferences
                    val editor = sharedFich.edit();
                    editor.putString(nameSharedPhone, telefonoFinal);
                    editor.putString(nameSharedURL,url);
                    editor.apply();

                    //Abrir Main_Activity con Intent y pasar datos con putExtra

                    val intent = Intent(this, MainActivity::class.java);
                    intent.putExtra("extra_telefono", telefonoFinal);
                    intent.putExtra("extra_url", url);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);


                }



            }
        }


    }

    /*
    Metodos Cruds con lo que verificamos si la url, o el
    telefono movil son correstas, para evitar el minimo de los fallos
     */

    //Validacion de telefono


    private fun esTelefonoEspanol(telefono: String): Boolean {
        val phoneUtil = PhoneNumberUtil.getInstance()
        return try {
            val numero = phoneUtil.parse(telefono, "ES");
            phoneUtil.isValidNumberForRegion(numero, "ES");
        } catch (e: NumberParseException) {
            false
        }
    }

    private fun formatearTelefono(telefono: String): String {
        val telefonoMovil = PhoneNumberUtil.getInstance()
        val numero = telefonoMovil.parse(telefono, "ES");
        return telefonoMovil.format(numero, PhoneNumberUtil.PhoneNumberFormat.E164);
    }


    /*
    Android trae ya hecha una expresión regular
    que describe cómo es una dirección web válida
     */

    private fun esURLValida(url: String): Boolean {
        val empiezaHTTPS = url.startsWith("https://");
        return empiezaHTTPS && Patterns.WEB_URL.matcher(url).matches();
    }







}