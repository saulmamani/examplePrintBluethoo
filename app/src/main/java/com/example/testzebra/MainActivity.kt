package com.example.testzebra

import android.bluetooth.BluetoothAdapter
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStream
import java.util.*


private const val TAG = "Zefra"

class MainActivity : AppCompatActivity() {
    private var outputStream: OutputStream? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val sourceDetail: String = "<div style='text-align:center'>" +
                "<h1>FACTURA #261</h1> <br/>" +
                "<h4>Catastro: C023D3401-S</h4>" +
                "</div>" +
                "<h5>Cliente: Saul Mamani</h4>" +
                "<p> <br>" +
                "Consumo: 23 cc <br>" +
                "Total: <strong> 45,20 BOB </strong> <br/>" +
                "Son: Cuarenta y cinco 20/100 Bolivianos</p>";

        val detalle: Spanned = HtmlCompat.fromHtml(sourceDetail, HtmlCompat.FROM_HTML_MODE_LEGACY);
        findViewById<TextView>(R.id.txtMessage).text = detalle;

        findViewById<Button>(R.id.btnImprimirEjemplo).setOnClickListener {
            //imprimiendo imagen
            val example = MainActivity()

            val theBtMacAddress = "CC:78:AB:A0:49:BA"
            example.sendCpclOverBluetooth(theBtMacAddress)
        }

        findViewById<Button>(R.id.btnImprimirImagen).setOnClickListener {
            //imprimiendo imagen
            val example = MainActivity()

            val theBtMacAddress = "CC:78:AB:A0:49:BA"
            example.printImage(theBtMacAddress)
        }

    }

    private fun printImage(mac: String) {
        Thread(Runnable {
            try {
                // Instantiate connection for given Bluetooth&reg; MAC Address.
                val thePrinterConn: Connection =
                    BluetoothConnection(mac)

                // Initialize
                Looper.prepare()

                // Open the connection - physical connection is established here.
                thePrinterConn.open()

                // This example prints "This is a ZPL test." near the top of the label.
                val zplData = """
              ﻿CT~~CD,~CC^~CT~
              ^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR5,5~SD10^JUS^LRN^CI0^XZ
              ^XA
              ^MMT
              ^PW799
              ^LL0951
              ^LS0
              ^FO32,0^GFA,02560,02560,00016,:Z64:
              eJztlbFqG0EQhmd3D24RRncpQhoFHU4TXITUIVhnkgewIX1eQWWKgBalOaRCr3BJdYE8QEgT1anyCCpFKuFKGNuT3ZOsm3/9CPaA4D79dzP/7O3sET3GAwgb8SDi04g/R3zrkBnRMOoq0jNeAqcRZ7wCfsJj4O+8BT7ja+AvfAvlU9R1hnoyZDBoRwwN5J6lwZoZDJ553kB5lgaVQb2XoZ4EFgZDeWkwlJcGi4DCYBnx14hVy4cGzLzlQwN+dVga1BlyWB3J/dZO18DRpOVDA/0WuwaeRzyKeIiLG/Q/NXJJOXDlZu4nucrtmRTe77Smwl8WdzrmD6WrcGl39ffW1Z2+3em62fmbbILkHSQtZ7wmauY11Uctpz69GqeH/OaqIHOZOfq2T/vX/zaq8zjzhde01sKTz1wmgg0d13JGZX+hK3XlnFwjGvoEAq0f4JXgfJBfdLql/u/tPyf09EbuXzK/pjBgT8tnUp+vZzCAH+ij3N/2zQjnIcV50XNktZzAFjBuhDqlEy6FPl1weAedrqLnC5hfE96x4D71Flx1nEQHlCVn5f1h7w2Ffjqd0ULoqiyjF/SSVq8JYpNLsrR9C7IaA2v9KTqBB9EJXf1Azs+RX6ww/7sl8Cvpn+6d7wafxu58+M3XSC79CsjQxW70uhuA/AJXlfxD0XuJPeNOGg2PUCIabBpnq1qKkT86wfL3Goi/f4+xj/9hBxZb:A175
              ^FT265,78^A0N,45,45^FH\^FDSeLA-ORURO^FS
              ^FT300,110^A0N,28,28^FH\^FDAVISO DE COBRO^FS
              ^FT265,141^A0N,25,24^FH\^FDPERIODO^FS
              ^FT421,137^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@periodo^FS^CI0
              ^FT56,194^A0N,25,24^FH\^FDCUENTA^FS
              ^FT230,194^A0N,25,24^FH\^FDCATASTRO^FS
              ^FT429,194^A0N,25,24^FH\^FDMEDIDOR^FS
              ^FT622,194^A0N,25,24^FH\^FDFECHA^FS
              ^FT56,232^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@cuenta^FS^CI0
              ^FT230,232^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@catastro^FS^CI0
              ^FT429,232^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@medidor^FS^CI0
              ^FT622,232^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@medidor^FS^CI0
              ^FT56,283^A0N,25,24^FH\^FDCATEGORIA^FS
              ^FT56,350^A0N,25,24^FH\^FDNOMBRE^FS
              ^FT56,417^A0N,25,24^FH\^FDDIRECCI\E3N^FS
              ^FT188,481^A0N,25,24^FH\^FDFECHA^FS
              ^FT339,481^A0N,25,24^FH\^FDLECTURA^FS
              ^FT522,481^A0N,25,24^FH\^FDHISTORIAL^FS
              ^FT56,520^A0N,25,24^FH\^FDANTERIOR^FS
              ^FT56,547^A0N,25,24^FH\^FDACTUAL^FS
              ^FT56,577^A0N,25,24^FH\^FDCONSUMO ACTUAL^FS
              ^FT56,310^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@categoria^FS^CI0
              ^FT56,378^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@nombre^FS^CI0
              ^FT56,445^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@direccion^FS^CI0
              ^FO501,456^GB0,208,4^FS
              ^FT622,141^A0N,25,24^FH\^FDNro.^FS
              ^FT683,135^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@nro^FS^CI0
              ^FT188,512^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@fecha_anterior^FS^CI0
              ^FT188,539^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@fecha_actual^FS^CI0
              ^FT339,512^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@lectura_anterior^FS^CI0
              ^FT339,539^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@lectura_actual^FS^CI0
              ^FT339,569^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@consumo_actual^FS^CI0
              ^FT57,765^A@N,17,18,TT0003M_^FH\^CI17^F8^FDSI SU DEUDA ES MAYOR A 2 MESES SE COBRARÁ LA MULTA POR MORA EN CAJA^FS^CI0
              ^FT56,796^A0N,20,19^FH\^FDSi Ud. cancel\A2 en caja meses pendientes, no tome en cuenta el valor de la factura.^FS
              ^FT56,820^A0N,20,19^FH\^FDEstimado cliente, en caso de encontrar diferencias en la lectura actual y la lectura de su ^FS
              ^FT56,844^A0N,20,19^FH\^FDmedidor, favor presentar el reclamo de ODECO para la correci\A2n del mes. \ADGracias!^FS
              ^FT522,512^A0N,25,24^FH\^FDPERIODO^FS
              ^FT522,538^A@N,17,18,TT0003M_^FH\^CI17^F8^FD1^FS^CI0
              ^FT522,559^A@N,17,18,TT0003M_^FH\^CI17^F8^FD2^FS^CI0
              ^FT522,581^A@N,17,18,TT0003M_^FH\^CI17^F8^FD3^FS^CI0
              ^FT522,602^A@N,17,18,TT0003M_^FH\^CI17^F8^FD4^FS^CI0
              ^FT522,622^A@N,17,18,TT0003M_^FH\^CI17^F8^FD5^FS^CI0
              ^FT522,644^A@N,17,18,TT0003M_^FH\^CI17^F8^FD6^FS^CI0
              ^FT538,538^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@periodo1^FS^CI0
              ^FT658,512^A0N,25,24^FH\^FDCONSUMO^FS
              ^FT658,538^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@consumo1^FS^CI0
              ^FT538,559^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@periodo2^FS^CI0
              ^FT538,581^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@periodo3^FS^CI0
              ^FT538,602^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@periodo4^FS^CI0
              ^FT538,622^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@periodo5^FS^CI0
              ^FT538,643^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@periodo6^FS^CI0
              ^FT658,559^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@consumo2^FS^CI0
              ^FT658,581^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@consumo3^FS^CI0
              ^FT658,602^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@consumo4^FS^CI0
              ^FT658,622^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@consumo5^FS^CI0
              ^FT658,643^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@consumo6^FS^CI0
              ^FT56,607^A@N,17,18,TT0003M_^FH\^CI17^F8^FDFACTURA MES @MAR. Bs.^FS^CI0
              ^FT62,631^A@N,17,18,TT0003M_^FH\^CI17^F8^FDFACTURA PENDIENTE Bs.^FS^CI0
              ^FT206,655^A0N,17,16^FH\^FDTOTAL Bs.^FS
              ^FT339,605^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@factura_mes_bs^FS^CI0
              ^FT339,629^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@pendiente_bs^FS^CI0
              ^FT339,653^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@total_bs^FS^CI0
              ^FT56,712^A0N,25,24^FH\^FDOBSERVACI\E3N: ^FS
              ^FT334,708^A@N,17,18,TT0003M_^FH\^CI17^F8^FDDEUDA ANTERIOR^FS^CI0
              ^FT307,708^A@N,17,18,TT0003M_^FH\^CI17^F8^FD(1)^FS^CI0
              ^FT638,58^A0N,17,16^FH\^FDOP:^FS
              ^FT638,83^A0N,17,16^FH\^FDUS:^FS
              ^FT667,55^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@operador^FS^CI0
              ^FT667,79^A@N,17,18,TT0003M_^FH\^CI17^F8^FD@usuario^FS^CI0
              ^PQ1,0,1,Y^XZ
                """.trimIndent()

                // Send the data to printer as a byte array.
                thePrinterConn.write(zplData.toByteArray())

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



//        val connection: Connection = BluetoothConnection(mac)
//        try {
//
//            // Initialize
//            //   Looper.prepare()
//            // Open the connection - physical connection is established here.
//            connection.open()
//
//            val printer =
//                ZebraPrinterFactory.getInstance(connection)
//            val x = 0
//            val y = 0
//            //val dir =
//              //  Environment.getExternalStorageState();// Environment.DIRECTORY_DOWNLOADS + "/yakusela.jpg";
//            //var dir = context?.getExternalFilesDir(null).toString() + "/sela.jpg";
//
//            //val dir = Environment.getExter(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + "yakusela.jpg"
//            //printer.printImage(dir, x, y)
//            val path = getExternalFilesDir(null).toString() + "/sela.jpg"
//            val path2 = filesDir;
//
////            printer.printImage("/storage/emulated/0/Download/sela.jpg", x, y);
//            printer.printImage(path, x, y);
//
////            ZebraPrinterFactory.getInstance(connection)
//            // printer.printImage()
//
//
//            //  Looper.myLooper()!!.quit()
//
//        } catch (e: ConnectionException) {
//            e.printStackTrace()
//        } catch (e: ZebraPrinterLanguageUnknownException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } finally {
//            connection.close()
//        }
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

//                val file = "XYZ"
                val dir = Environment.DIRECTORY_DOWNLOADS + "/yakusela.jpg";


                val cpclData = """
                    ! 0 200 200 600 1
                    T 5 0 125 179        Time: 12:12
                    T 4 0 125 217        User: @cliente
                    T 5 0 125 236        Details
                    T 5 0 125 255        Card Price: 2213
                    T 5 0 125 274        Balance: 33
                    T 5 0 25 293             Gracias
                    T 5 0 40 312                   www.aa.aa.aa
                    FORM
                    PRINT
                    
                    """.trimIndent().replace("@cliente", dir)

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
//        outputStream?.run {
//            write("\n".toByteArray())
//        }

        // val ima = this.getExternalFilesDir(null)
        //val dir = getExternalFilesDir(null).toString()
        val path = applicationContext.getExternalFilesDir(null).toString()
        val path2 = applicationContext.filesDir.path

        val f = File(path)
        //obtiene ombres de archivos dentro del directorio.
        val file: Array<File> = f.listFiles()
        Log.d("Archivo","Listando archivos " + path)
        for (i in file.indices) {
            Log.d("Files", "Archivo : " + file[i].name)
        }

        Toast.makeText(this@MainActivity, path + " - " + path2 , Toast.LENGTH_SHORT).show()
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