package dev.williamscg.promcoserapp.model

data class DetalleParteDiarioModel(
    val idParteDiario: Int,
    val horaInicio: String,
    val horaFin: String,
    val trabajoEfectuado: String,
    val estado: Boolean
)
