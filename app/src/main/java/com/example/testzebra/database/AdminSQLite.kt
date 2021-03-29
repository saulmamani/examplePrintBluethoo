package com.example.testzebra.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.testzebra.models.local.Lectura

class AdminSQLite(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_NAME(
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                cliente TEXT NOT NULL,
                consumo REAL,
                mes INTEGER NOT NULL,
                anio INTEGER NOT NULL,
                es_leido INTEGER DEFAULT 0 NOT NULL
            );
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

//    fun store(lectura: Lectura): Boolean {
//        val db = this.writableDatabase
//        val values = ContentValues()
//        values.put("cliente", lectura.cliente)
//        values.put("consumo", lectura.consumo)
//        values.put("mes", lectura.mes)
//        values.put("anio", lectura.anio)
//        values.put("es_leido", lectura.es_leido)
//        val res = db.insert(TABLE_NAME, null, values)
//        db.close()
//        return (Integer.parseInt("$res") != -1)
//    }
//
//    fun show(id: Int): Lectura {
//        val lectura = Lectura()
//        val db = this.writableDatabase
//        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE id = $id"
//        val cursor = db.rawQuery(selectQuery, null)
//        if (cursor != null) {
//            cursor.moveToFirst()
//            while (cursor.moveToNext()) {
//                lectura.cliente = cursor.getString(cursor.getColumnIndex("cliente"))
//                lectura.consumo = cursor.getDouble(cursor.getColumnIndex("consumo"))
//                lectura.mes = cursor.getInt(cursor.getColumnIndex("mes"))
//                lectura.anio = cursor.getInt(cursor.getColumnIndex("anio"))
//                lectura.es_leido = cursor.getInt(cursor.getColumnIndex("es_leido"))
//            }
//        }
//        cursor.close()
//        return lectura
//    }
//
//    fun delete(id: Int): Boolean {
//        val db = this.writableDatabase
//        val res = db.delete(TABLE_NAME, "id=?", arrayOf(id.toString())).toLong()
//        db.close()
//        return Integer.parseInt("$res") != -1
//    }
//
//    fun deleteAll(): Boolean {
//        val db = this.writableDatabase
//        val res = db.delete(TABLE_NAME, "id > ?", arrayOf("0")).toLong()
//        db.close()
//        return Integer.parseInt("$res") != -1
//    }
//
//    fun update(lectura: Lectura): Boolean {
//        val db = this.writableDatabase
//        val values = ContentValues()
//        values.put("cliente", lectura.cliente)
//        values.put("consumo", lectura.consumo)
//        values.put("mes", lectura.mes)
//        values.put("anio", lectura.anio)
//        values.put("es_leido", lectura.es_leido)
//        val res = db.update(TABLE_NAME, values, "id=?", arrayOf(lectura.id.toString())).toLong()
//        db.close()
//        return Integer.parseInt("$res") != -1
//    }
//
//    val lecturas: List<Lectura>
//        get() {
//            val lecturas = ArrayList<Lectura>()
//            val db = writableDatabase
//            val selectQuery = "SELECT  * FROM $TABLE_NAME"
//            val cursor = db.rawQuery(selectQuery, null)
//            if (cursor != null) {
//                cursor.moveToFirst()
//                while (cursor.moveToNext()) {
//                    val lectura = Lectura()
//                    lectura.cliente = cursor.getString(cursor.getColumnIndex("cliente"))
//                    lectura.consumo = cursor.getDouble(cursor.getColumnIndex("consumo"))
//                    lectura.mes = cursor.getInt(cursor.getColumnIndex("mes"))
//                    lectura.anio = cursor.getInt(cursor.getColumnIndex("anio"))
//                    lectura.es_leido = cursor.getInt(cursor.getColumnIndex("es_leido"))
//                    lecturas.add(lectura)
//                }
//            }
//            cursor.close()
//            return lecturas
//        }


    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "lecturasDB.sela"
        private val TABLE_NAME = "lectura"
    }
}