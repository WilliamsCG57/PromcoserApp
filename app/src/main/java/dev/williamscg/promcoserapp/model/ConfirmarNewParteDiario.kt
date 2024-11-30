package dev.williamscg.promcoserapp.model

data class ConfirmarNewParteDiario (
    val idParteDiario: Int,
    val horometroFinal: Double = 0.0,
    val cantidadPetroleo: Double = 0.0,
    val cantidadAceite: Double = 0.0
)
