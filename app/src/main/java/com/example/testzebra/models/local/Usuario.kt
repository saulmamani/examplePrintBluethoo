package com.example.testzebra.models.local

data class Usuario (
    var id: Long,
    var codigo: Int,
    var pin: Int,
    var numero_lector: Int,
    var nombre_completo: String,
    var rol: String
)