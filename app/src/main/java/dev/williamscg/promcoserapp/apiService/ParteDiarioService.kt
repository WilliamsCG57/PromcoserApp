package dev.williamscg.promcoserapp.apiService

import dev.williamscg.promcoserapp.model.TarjetaParteDiarioModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ParteDiarioService {
    @GET("api/ParteDiario/GetAllActive/{userId}")
    suspend fun getParteDiario(@Path("userId") userId: Int): List<TarjetaParteDiarioModel>
}
