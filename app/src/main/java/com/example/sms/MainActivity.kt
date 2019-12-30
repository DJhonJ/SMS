package com.example.sms

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var numero: String
    lateinit var mensaje: String
    val smsManager: SmsManager by lazy {
        SmsManager.getDefault()
    }

    companion object {
        private const val SMS_PERMISSION_REQUEST_CODE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPermisos()

        buttonEnviar.setOnClickListener {
            numero = this.txtNumero.text.toString()
            mensaje = this.txtMensaje.text.toString()

            if(numero.isEmpty()) {
                Toast.makeText(this, "Ingresar un número de celular", Toast.LENGTH_SHORT).show()
            }

            else if(mensaje.isNullOrBlank()) {
                Toast.makeText(this, "Ingresar el mensaje a enviar", Toast.LENGTH_SHORT).show()
            }

            else {

                smsManager.sendMultipartTextMessage(numero, null, smsManager.divideMessage(mensaje),
                                         null, null)

                Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show()

                this.txtNumero.text.clear()
                this.txtMensaje.text.clear()
            }
        }
    }

    private fun getPermisos() : Boolean {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS), SMS_PERMISSION_REQUEST_CODE)

            Toast.makeText(this, "Sele van a otorgar", Toast.LENGTH_SHORT).show()

        }else {
            Toast.makeText(this, "Si los tiene", Toast.LENGTH_SHORT).show()
        }

        return false
    }
}
