package com.example.testzebra.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.testzebra.models.local.Lectura
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LecturaService(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_NAME(
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                fecha TEXT NOT NULL,
                valor REAL NOT NULL,
                observacion_id INTEGER NOT NULL,
                observacion VARCHAR(200) NOT NULL,
                latitud VARCHAR(50) NULL,
                longitud VARCHAR(50) NULL,
                codigo_lector INTEGER NOT NULL,
                es_ultimo INTEGER NOT NULL,
                carga_id INTEGER NOT NULL,
            );
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    val lecturas: ArrayList<Lectura>
        get() {
            val lecturas = ArrayList<Lectura>()
            val db = writableDatabase
            val selectQuery = "SELECT  * FROM ${TABLE_NAME}"
            val cursor = db.rawQuery(selectQuery, null)
            while (cursor.moveToNext()) {
                lecturas.add(
                    Lectura(
                        cursor.getLong(cursor.getColumnIndex("id")),
                        LocalDateTime.parse(cursor.getString(cursor.getColumnIndex("fecha")), DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm")),
                        cursor.getDouble(cursor.getColumnIndex("valor")),
                        cursor.getInt(cursor.getColumnIndex("observacion_id")),
                        cursor.getString(cursor.getColumnIndex("observacion")),
                        cursor.getString(cursor.getColumnIndex("latitud")),
                        cursor.getString(cursor.getColumnIndex("longitud")),
                        cursor.getInt(cursor.getColumnIndex("codigo_lector")),
                        cursor.getInt(cursor.getColumnIndex("es_ultimo")),
                        cursor.getInt(cursor.getColumnIndex("carga_id"))
                    )
                );
            }
            cursor.close()
            return lecturas
        }

    fun store(lectura: Lectura): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("fecha", lectura.fecha.toString())
        values.put("valor", lectura.valor)
        values.put("observacion_id", lectura.observacion_id)
        values.put("observacion", lectura.observacion)
        values.put("latitud", lectura.latitud)
        values.put("longitud", lectura.longitud)
        values.put("codigo_lector", lectura.codigo_lector)
        values.put("es_ultimo", 0)
        values.put("carga_id", lectura.carga_id)
        val res = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$res") != -1)
    }

    fun deleteAll(): Boolean {
        val db = this.writableDatabase
        val res = db.rawQuery("delete from $TABLE_NAME", null)
        db.close()
        return Integer.parseInt("$res") != -1
    }

    companion object {
        private val DB_VERSION = 2
        private val DB_NAME = "lecturasDB_2.sela"
        private val TABLE_NAME = "lectura"
    }
}