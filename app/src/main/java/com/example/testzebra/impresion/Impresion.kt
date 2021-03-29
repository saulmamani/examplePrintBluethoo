package com.example.testzebra.impresion

import android.os.Looper
import com.zebra.sdk.comm.BluetoothConnection
import com.zebra.sdk.comm.Connection

class Impresion {
    companion object {
        fun imprimir(mac: String, data: HashMap<String, String>) {
            Thread(Runnable {
                try {
                    // Instantiate connection for given Bluetooth&reg; MAC Address.
                    val connection: Connection =
                        BluetoothConnection(mac)

                    // Initialize
                    Looper.prepare()

                    // Open the connection - physical connection is established here.
                    connection.open()

                    // This example prints "This is a ZPL test." near the top of the label
                    val zplData =
                        AvisoCobro.getData(data)

                    // Send the data to printer as a byte array.
                    connection.write(zplData.toByteArray())

                    // Make sure the data got to the printer before closing the connection
                    Thread.sleep(500)

                    // Close the connection to release resources.
                    connection.close()
                    Looper.myLooper()!!.quit()
                } catch (e: java.lang.Exception) {
                    // Handle communications error here.
                    e.printStackTrace()
                }
            }).start()
        }
    }
}