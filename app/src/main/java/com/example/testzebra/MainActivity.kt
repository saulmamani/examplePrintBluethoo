package com.example.testzebra

import android.bluetooth.BluetoothAdapter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.*


private const val TAG = "Zefra"

class MainActivity : AppCompatActivity() {
    private var outputStream: OutputStream? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sourceDetail: String = "<div style='text-align:center'>" +
                "<h1>FACTURA #146</h1> <br/>" +
                "<h4>Catastro: C023D3401-S</h4>" +
                "</div>" +
                "<h5>Cliente: Saul Mamani</h4>" +
                "<p> <br>" +
                "Consumo: 23 cc <br>" +
                "Total: <strong> 45,20 BOB </strong> <br/>" +
                "Son: Cuarenta y cinco 20/100 Bolivianos</p>";

        val detalle: Spanned = HtmlCompat.fromHtml(sourceDetail, HtmlCompat.FROM_HTML_MODE_LEGACY);
        findViewById<TextView>(R.id.txtMessage).text = detalle;

        //imprimiendo imagen
        findViewById<Button>(R.id.btnImprimirImagen).setOnClickListener{
            val sourceLogo = findViewById<ImageView>(R.id.imgLogo);
            val logo = this.imageToBitmap(sourceLogo);
            outputStream?.run {
                write(logo)
            }
        }
    }

    public fun conectar(v: View) {
        GlobalScope.launch(Dispatchers.Main) {
            if (outputStream == null) {
                outputStream = connect()?.also {
                    Toast.makeText(this@MainActivity, "Printer Connected", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    public fun limpiar(v: View) {
        outputStream?.run {
            write("\n".toByteArray())
        }
    }

    fun imprimir(v: View) {
        outputStream?.run {
            write("\n\t\t\t\t\t\t\t\t\t\t\t\t*----**SeLa 2021**----*\r\n|".toByteArray())
            write("\t\t\t\t\t\t\t\t\t\t\t\t**<<<<<<<<<Oruro\r\n|".toByteArray())
            write("\t\t\t\t\t\t\t\t\t\t\t\t**<<<<<<<<<Bolivia\r\n|".toByteArray())
            write("\t\t\t\t\t\t\t\t\t\t\t\t**<<<<<<<<<2021\r\n|".toByteArray())
            write(byteArrayOf(20))
        }

        outputStream?.flush()
        outputStream?.write("Ya pues!\n".toByteArray())
    }

    private fun imageToBitmap(image: ImageView): ByteArray {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
    }

    private suspend fun connect(): OutputStream? {
        return withContext(Dispatchers.IO) {
            var outputStream: OutputStream? = null
            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            if (bluetoothAdapter != null && bluetoothAdapter.isEnabled) {
                try {
                    val bluetoothAddress = "CC:78:AB:A0:49:BA" // replace with your device's address
                    val bluetoothDevice = bluetoothAdapter.getRemoteDevice(bluetoothAddress)
                    val bluetoothSocket = bluetoothDevice?.createRfcommSocketToServiceRecord(
                        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
                    )

                    bluetoothAdapter.cancelDiscovery()
                    bluetoothSocket?.connect()
                    if (bluetoothSocket!!.isConnected) {
                        outputStream = bluetoothSocket!!.outputStream
                        outputStream.write("\n".toByteArray())
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "connect: ${e.message}")
                }
            }
            outputStream
        }
    }
}