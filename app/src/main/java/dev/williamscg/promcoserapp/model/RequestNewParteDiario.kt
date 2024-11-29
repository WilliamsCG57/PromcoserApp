package dev.williamscg.promcoserapp.model

data class RequestNewParteDiario(
    val idCliente: Int,
    val idPersonal: Int,
    val idLugarTrabajo: Int,
    val idMaquinaria: Int,
    val finalizado: Boolean = false,
    val fecha: String,
    val horometroInicio: Double = 0.0,
    val horometroFinal: Double = 0.0,
    val cantidadPetroleo: Double = 0.0,
    val cantidadAceite: Double = 0.0,
    val estado: Boolean = true
)
