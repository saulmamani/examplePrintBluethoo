package com.example.testzebra.models.local

import java.util.*

data class Lectura (
    val id: Int,
    val fecha: Date,
    val consumo: Double,
    val descripcion: String,
    val latitud: String,
    val longitud: String,
    val observacion_id: Int,
    val carga_id: Int,
    val codigo_usuario: Int,
    val es_leido: Int,

    val carga: Carga
)