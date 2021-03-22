package com.example.testzebra

import android.os.Bundle
import android.os.Looper
import android.text.Spanned
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.zebra.sdk.comm.BluetoothConnection
import com.zebra.sdk.comm.Connection
import java.io.OutputStream


private const val TAG = "Zefra"

class MainActivity : AppCompatActivity() {
    private var outputStream: OutputStream? = null

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
            val mac = "CC:78:AB:A0:49:BA"
            Impresion.imprimir(mac, data)
        }

        findViewById<Button>(R.id.btnImprimirz900).setOnClickListener {
            val mac = "B0:91:22:7D:78:7E"
            Impresion.imprimir(mac, data)
        }

    }
}