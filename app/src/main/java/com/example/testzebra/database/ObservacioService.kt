package com.example.testzebra.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.testzebra.models.local.Observacion

class ObservacioService(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_NAME(
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
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

    val observaciones: ArrayList<Observacion>
        get() {
            val observaciones = ArrayList<Observacion>()
            val db = writableDatabase
            val selectQuery = "SELECT  * FROM $TABLE_NAME"
            val cursor = db.rawQuery(selectQuery, null)
            while (cursor.moveToNext()) {
                observaciones.add(
                    Observacion(
                        cursor.getLong(cursor.getColumnIndex("id")),
                        cursor.getInt(cursor.getColumnIndex("codigo")),
                        cursor.getString(cursor.getColumnIndex("descripcion"))
                    )
                );
            }
            cursor.close()
            return observaciones
        }

    fun store(obs: Observacion): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("codigo", obs.codigo)
        values.put("descripcion", obs.descripcion)
        val res = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$res") != -1)
    }

    fun show(codigo: Int): Observacion {
        val observaciones = ArrayList<Observacion>()
        val db = this.writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE codigo = $codigo"
        val cursor = db.rawQuery(selectQuery, null)
        while (cursor.moveToNext()) {
            observaciones.add(
                Observacion(
                    cursor.getLong(cursor.getColumnIndex("id")),
                    cursor.getInt(cursor.getColumnIndex("codigo")),
                    cursor.getString(cursor.getColumnIndex("descripcion"))
                )
            )
        }
        cursor.close()
        if (observaciones.count() > 0)
            return observaciones.first()
        return Observacion(0, 0, "")
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
        private val TABLE_NAME = "observacion"
    }
}