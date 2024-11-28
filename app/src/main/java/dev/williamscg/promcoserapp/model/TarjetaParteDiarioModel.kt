package dev.williamscg.promcoserapp.model

data class TarjetaParteDiarioModel(
    val idParteDiario: Int,
    val placa: String,
    val personal: String,
    val descripcion: String,
    val razonSocial: String,
    val serie: String,
    val firmas: String,
    val fecha: String,
    val horometroInicio: Double,
    val horometroFinal: Double,
    val cantidadPetroleo: Double,
    val cantidadAceite: Double,
    val estado: Boolean
)
