package com.example.testzebra

import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.example.testzebra.database.ObservacioService
import com.example.testzebra.impresion.Impresion
import com.example.testzebra.models.local.Observacion

private const val TAG = "Zefra"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sourceDetail: String = "<div style='text-align:center'>" +
                "<h1>FACTURA #277</h1> <br/>" +
                "<h4>Catastro: C023D3401-S</h4>" +
                "</div>" +
                "<h5>Cliente: Saul Mamani</h4>" +
                "<p> <br>" +
                "Consumo: 23 cc <br>" +
                "Total: <strong> 45,20 BOB </strong> <br/>" +
                "Son: Cuarenta y cinco 20/100 Bolivianos</p>";

        val detalle: Spanned = HtmlCompat.fromHtml(sourceDetail, HtmlCompat.FROM_HTML_MODE_LEGACY);
        findViewById<TextView>(R.id.txtMessage).text = detalle;

        val data = HashMap<String, String>()
        data["@cuenta"] = "4041387"
        data["@catastro"] = "D-031-C-45-610"
        data["@medidor"] = "A10L018299"
        data["@categoria"] = "EDIFICIOS"
        data["@nombre"] = "SAUL MAMANI MAMANI"
        data["@direccion"] = "PROLONGACION CORNETA MAMANI N0. 123 TERESA DE JORN"


        findViewById<Button>(R.id.btnImprimirz902).setOnClickListener {
            val consumo = findViewById<EditText>(R.id.txtConsumo)
            data["@consumo_actual"] = consumo.text.toString() + " (m3)"

            val mac = "CC:78:AB:A0:49:BA"
            Impresion.imprimir(mac, data)
        }

        findViewById<Button>(R.id.btnImprimirz900).setOnClickListener {
            val consumo = findViewById<EditText>(R.id.txtConsumo)
            data["@consumo_actual"] = consumo.text.toString() + " (m3)"

            val mac = "B0:91:22:7D:78:7E"
            Impresion.imprimir(mac, data)
        }
    }

    fun store(v: View) {
        val conexion = ObservacioService(applicationContext)
        var codigo = (1..1000).random();
        val reg = Observacion(
            codigo,
            "Prueba $codigo"
        )

        println(reg);
        conexion.store(reg)
        conexion.close()

        val observaciones = conexion.observaciones
        println("****DATOS**** (${observaciones.count()})")
        observaciones.forEach {
            println(it)
        }

        Toast.makeText(this, "Total registros: ${observaciones.count()}", Toast.LENGTH_LONG).show()
    }

    fun delete(v: View) {
        val conexion = ObservacioService(this)
        println(conexion.show(770))

        Toast.makeText(this, "Todos los datos eliminados!", Toast.LENGTH_LONG).show()
    }
}