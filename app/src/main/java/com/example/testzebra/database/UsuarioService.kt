package com.example.testzebra.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.testzebra.models.local.Usuario

class UsuarioService(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_NAME(
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                codigo INTEGER NOT NULL,
                ping INTEGER NOT NULL,
                numero_lector INTEGER NOT NULL,
                nombre_completo VARCHAR(200) NOT NULL
                rol VARCHAR(50) NOT NULL
            );
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun store(usuario: Usuario): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("codigo", usuario.codigo)
        values.put("pin", usuario.pin)
        values.put("numero_lector", usuario.numero_lector)
        values.put("nombre_completo", usuario.nombre_completo)
        values.put("rol", usuario.rol)
        val res = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$res") != -1)
    }

    fun login(pin: Int): Usuario {
        val usuarios = ArrayList<Usuario>()
        val db = this.writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE pin = $pin"
        val cursor = db.rawQuery(selectQuery, null)
        while (cursor.moveToNext()) {
            usuarios.add(
                Usuario(
                    cursor.getLong(cursor.getColumnIndex("id")),
                    cursor.getInt(cursor.getColumnIndex("codigo")),
                    cursor.getInt(cursor.getColumnIndex("pin")),
                    cursor.getInt(cursor.getColumnIndex("numero_lector")),
                    cursor.getString(cursor.getColumnIndex("nombre_completo")),
                    cursor.getString(cursor.getColumnIndex("rol"))
                )
            )
        }
        cursor.close()
        if (usuarios.count() > 0)
            return usuarios.first()
        return Usuario(0, 0, 0, 0, "", "")
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
        private val TABLE_NAME = "usuario"
    }
}