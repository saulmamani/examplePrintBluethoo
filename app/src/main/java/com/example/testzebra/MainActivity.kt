package com.example.testzebra

import android.bluetooth.BluetoothAdapter
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Looper
import android.text.Spanned
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.zebra.sdk.comm.BluetoothConnection
import com.zebra.sdk.comm.Connection
import com.zebra.sdk.comm.ConnectionException
import com.zebra.sdk.comm.TcpConnection
import com.zebra.sdk.graphics.ZebraImageFactory
import com.zebra.sdk.graphics.ZebraImageI
import com.zebra.sdk.printer.ZebraPrinter
import com.zebra.sdk.printer.ZebraPrinterFactory
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


private const val TAG = "Zefra"

class MainActivity : AppCompatActivity() {
    private var outputStream: OutputStream? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sourceDetail: String = "<div style='text-align:center'>" +
                "<h1>FACTURA #195</h1> <br/>" +
                "<h4>Catastro: C023D3401-S</h4>" +
                "</div>" +
                "<h5>Cliente: Saul Mamani</h4>" +
                "<p> <br>" +
                "Consumo: 23 cc <br>" +
                "Total: <strong> 45,20 BOB </strong> <br/>" +
                "Son: Cuarenta y cinco 20/100 Bolivianos</p>";

        val detalle: Spanned = HtmlCompat.fromHtml(sourceDetail, HtmlCompat.FROM_HTML_MODE_LEGACY);
        findViewById<TextView>(R.id.txtMessage).text = detalle;

        findViewById<Button>(R.id.btnImprimirEjemplo).setOnClickListener{
            //imprimiendo imagen
            val example = MainActivity()

            val theBtMacAddress = "CC:78:AB:A0:49:BA"
            example.sendCpclOverBluetooth(theBtMacAddress)
        }

        findViewById<Button>(R.id.btnImprimirImagen).setOnClickListener{
            //imprimiendo imagen
            val example = MainActivity()

            val theBtMacAddress = "CC:78:AB:A0:49:BA"
            example.printImage(theBtMacAddress)
        }

    }

    private fun printImage(mac: String){
        val connection: Connection = BluetoothConnection(mac)
        try {
            connection.open()
            val printer: ZebraPrinter = ZebraPrinterFactory.getInstance(connection)
            val x = 0
            val y = 0
//            val imagen = applicationContext.resources.getResourceName(R.drawable.logo_200w)
//            val bitmap: Bitmap = (imagen.drawable as BitmapDrawable).bitmap
            printer.printImage("/home/saul/Escritorio/uno.jpg", x, y)
        } catch (e: ConnectionException) {
            e.printStackTrace()
        } catch (e: ZebraPrinterLanguageUnknownException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            connection.close()
        }
    }

    private fun sendCpclOverBluetooth(mac: String) {
        Thread(Runnable {
            try {
                // Instantiate connection for given Bluetooth&reg; MAC Address.
                val thePrinterConn: Connection =
                    BluetoothConnection(mac)

                // Initialize
                Looper.prepare()
                // Open the connection - physical connection is established here.
                thePrinterConn.open()

                // This example prints "This is a CPCL test." near the top of the label.
                //                PCX 0 1 !<B.PCX
//                T 5 0 125 179 I love saltenias
//                T 5 0 125 217 I love tucumanas

                //TEXT 4 0 30 40 MOLA MOLA....
                //! 0 200 200 210 1
                val cpclData = """
                    ! 0 200 200 600 1
                    T 4 0 125 160        Empresa: SeLA Oruro
                    T 5 0 125 179        Time: 12:12
                    T 5 0 125 217        User: @cliente
                    T 5 0 125 236        Details
                    T 5 0 125 255        Card Price: 2213
                    T 5 0 125 274        Balance: 33
                    T 5 0 25 293             Gracias
                    T 5 0 40 312                   www.aa.aa.aa
                    FORM
                    PRINT
                    
                    """.trimIndent().replace("@cliente", "Zoyla del Rincon")

                // Send the data to printer as a byte array.
                thePrinterConn.write(cpclData.toByteArray())

                // Make sure the data got to the printer before closing the connection
                Thread.sleep(500)

                // Close the connection to release resources.
                thePrinterConn.close()
                Looper.myLooper()!!.quit()
            } catch (e: java.lang.Exception) {

                // Handle communications error here.
                e.printStackTrace()
            }
        }).start()
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

    private fun imageToBitmap(image: ImageView): ByteArray? {
        Toast.makeText(this@MainActivity, "Imprimiendo...", Toast.LENGTH_SHORT)
            .show()
        // val bitmap:Bitmap = image.drawable.toBitmap()

//        val bitmap = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.RGB_565)
//Uri.parse("drawable/andro.png")

        val bitmap = (image as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
        //  return bmpToByteArray(bitmap, false)
    }

    fun bmpToByteArray(bmp: Bitmap, needRecycle: Boolean): ByteArray? {
        val output = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, output)
        if (needRecycle) {
            bmp.recycle()
        }
        val result = output.toByteArray()
        try {
            output.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return result
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