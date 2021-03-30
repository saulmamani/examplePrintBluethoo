package com.example.testzebra.models.local

import java.time.LocalDateTime
import java.util.*

data class Lectura (
    val id: Long,
    val fecha: LocalDateTime,
    val valor: Double,
    val observacion_id: Int,
    val observacion: String,
    val latitud: String,
    val longitud: String,
    val codigo_lector: Int,
    val es_ultimo: Int,

    val carga_id: Int
)