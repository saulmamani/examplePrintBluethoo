package com.example.testzebra.models.local

data class Carga(
    val id: Long,
    val api: String,
    val numero_lector: Int,
    val mes: Int,
    val anio: Int,
    var impresiones: Int,
    var recibio_aviso: Int,
    val es_leido: Int
)