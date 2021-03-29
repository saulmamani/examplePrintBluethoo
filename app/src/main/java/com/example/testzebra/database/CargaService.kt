package com.example.testzebra.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.testzebra.models.local.Carga

class CargaService(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_NAME(
                codigo INTEGER NOT NULL,
                descripcion VARCHAR(200) NOT NULL
            );
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    val cargas: ArrayList<Carga>
        get() {
            val cargas = ArrayList<Carga>()
            val db = writableDatabase
            val selectQuery = "SELECT  * FROM ${TABLE_NAME}"
            val cursor = db.rawQuery(selectQuery, null)
            while (cursor.moveToNext()) {
                cargas.add(
                    Carga(
                        cursor.getLong(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("api")),
                        cursor.getInt(cursor.getColumnIndex("numero_lector")),
                        cursor.getInt(cursor.getColumnIndex("mes")),
                        cursor.getInt(cursor.getColumnIndex("anio"))
                    )
                );
            }
            cursor.close()
            return cargas
        }

    fun store(carga: Carga): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("api", carga.api)
        values.put("numero_lector", carga.numero_lector)
        values.put("mes", carga.mes)
        values.put("anio", carga.anio)
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
        private val TABLE_NAME = "carga"
    }
}